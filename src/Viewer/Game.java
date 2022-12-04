package Viewer;

import Objects.Ball;
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

    private AnimationTimer gameTimer;
    private Stage stage;
    private Scene scene;
    private AnchorPane pane;
    private Ball ball;
    private ArrayList<Wall> walls = new ArrayList<>();

    public Game() {
        initializeGame();
        createNewGame();
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
        scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    private void createNewGame() {
        ball = new Ball(refreshRate, physicsFPS);
        pane.getChildren().add(ball);
        walls.add(new Wall(20, 120,100,100));
        walls.add(new Wall(10, 40,300,100));
        walls.add(new Wall(80, 90,500,100));
        walls.add(new Wall(50, 10,200,300));
        for(Wall wall : walls) {
            pane.getChildren().add(wall);
        }

        createGameLoop();
    }
    private void createGameLoop() {
        // Ball mover and collision detection updater at 288 fps
        Updater updater = new Updater();
        service.scheduleAtFixedRate(updater, 0, 1000000/physicsFPS, TimeUnit.MICROSECONDS);

        // Animation updater at refresh rate (144 hz)
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ball.moveBall(walls); // Syncs updater thread with viewer pos update
                ball.update();
            }
        };
        gameTimer.start();
    }

    public void stop() {
        System.out.println("Stopping game");
        service.shutdown();
    }
}
