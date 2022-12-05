package Viewer;

import Menu.LevelLoader;
import Menu.PowerMeter;
import Objects.Ball;
import Objects.Goal;
import Objects.Wall;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;


public class Game {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
    private static final int physicsFPS = 1000;

    private static final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private static final GraphicsDevice[] gs = ge.getScreenDevices();
    private static final int refreshRate = gs[0].getDisplayMode().getRefreshRate();
    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    private boolean isLevelLoaded = false;
    private AnimationTimer gameTimer;
    private Stage stage;
    private Scene scene;
    private AnchorPane pane;
    private Ball ball;
    private ArrayList<Wall> walls;
    private Goal goal;
    private PowerMeter powerMeter;
    private View viewer;

    public Game(View viewer) {
        this.viewer = viewer;
        initializeGame();
//        createNewGame("1.lvl");
    }

    private class Updater implements Runnable {
        @Override
        public void run() {
            ball.moveBall(walls);
        }
    }

    private void initializeGame() {
        stage = new Stage();
        pane = new AnchorPane();
        scene = new Scene(pane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setResizable(false);
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void createNewGame(String lvl) {
        LevelLoader ld = new LevelLoader();
        walls = ld.loadLevel(lvl);
        for(Wall wall : walls) {
            pane.getChildren().add(wall);
        }
        goal = new Goal(ld.endX(), ld.endY());
        pane.getChildren().add(goal);
        ball = new Ball(refreshRate, physicsFPS, ld.startX(), ld.startY());
        pane.getChildren().add(ball);
        powerMeter = new PowerMeter();
        pane.getChildren().add(powerMeter);
        System.out.println("Game started");
        viewer.getStage().setScene(this.scene);
        createGameLoop();
    }
    private void createGameLoop() {
        // Ball mover and collision detection updater
        Updater updater = new Updater();
        service.scheduleAtFixedRate(updater, 0, 1000000/physicsFPS, TimeUnit.MICROSECONDS);

        // Animation updater at refresh rate
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ball.moveBall(walls); // Syncs updater thread with viewer pos update
                ball.update();
                powerMeter.setLength(ball.getPower());
                if(ball.isInGoal(goal)) {
                    Game.this.stop();
                    stop();
                }
            }
        };
        gameTimer.start();
    }

    public void stop() {
        System.out.println("Stopping game");
        service.shutdown();
    }
}
