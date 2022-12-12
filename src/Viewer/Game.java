package Viewer;

import Util.LevelLoader;
import Menu.PowerMeter;
import Objects.Ball;
import Objects.Goal;
import Objects.Wall;
import Util.Timer;
import Util.UserDataUtil;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;


public class Game {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
    private static final int physicsFPS = 1000;

    private static final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private static final GraphicsDevice[] gs = ge.getScreenDevices();
    private static final int refreshRate = gs[0].getDisplayMode().getRefreshRate();
    private static ScheduledExecutorService service;
    private AnimationTimer gameTimer;
    private Stage stage;
    private Scene scene;
    private AnchorPane pane;
    private Ball ball;
    private ArrayList<Wall> walls;
    private Goal goal;
    private PowerMeter powerMeter;
    private Label timerLabel;
    private Label bestTime;
    private Label putLabel;
    private Label bestPut;
    private View viewer;
    private Timer timer = new Timer();
    private boolean isPlaying = false;

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
        // Click anywhere to continue after winning
        scene.setOnMouseClicked((MouseEvent e) -> {
            if(!isPlaying) viewer.viewMainMenu();
        });
        pane.getStylesheets().add(getClass().getResource("/styles/game.css").toString());
        pane.setId("gamepane");
        stage.setScene(scene);
        stage.setResizable(true);

    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void createNewGame(String lvl) {
        clearMap();
        timer.reset();
        LevelLoader ld = new LevelLoader();
        walls = ld.loadLevel(lvl);
        for(Wall wall : walls) {
            pane.getChildren().add(wall);
        }
        createTimerLabel();
        createPutCounter();
        goal = new Goal(ld.endX(), ld.endY());
        pane.getChildren().add(goal);
        ball = new Ball(physicsFPS, ld.startX(), ld.startY());
        pane.getChildren().add(ball);
        powerMeter = new PowerMeter();
        pane.getChildren().add(powerMeter);
        System.out.println("Game started");
        isPlaying = true;
        viewer.viewGame();
        createGameLoop(lvl);
    }
    private void createGameLoop(String lvl) {
        // Ball mover and collision detection updater
        Updater updater = new Updater();
        service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(updater, 0, 1000000/physicsFPS, TimeUnit.MICROSECONDS);

        UserDataUtil udt = new UserDataUtil(lvl);

        bestTime.setText("Best : " + udt.getBestTime()/60 + ":" + udt.getBestTime()%60);
        bestPut.setText("Best : " + udt.getBestPuts());


        // Animation updater at refresh rate
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ball.moveBall(walls); // (Tries to) Sync updater thread with viewer pos update
                ball.update();
                powerMeter.setLength(ball.getPower());
                timerLabel.setText("Time : " + timer.toString());
                putLabel.setText("Puts : " + ball.getPuts());
                if(ball.isInGoal(goal)) {
                    timer.stop();
                    try {
                        udt.update(timer.getSeconds(), ball.getPuts());
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                    isPlaying = false;
                    Game.this.stop();
                    stop(); // Stop animation timer
                }
            }
        };
        gameTimer.start();
        timer.start();
    }

    private void createTimerLabel() {
        timerLabel = new Label();
        pane.getChildren().add(timerLabel);
        timerLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        bestTime = new Label();
        pane.getChildren().add(bestTime);
        bestTime.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        bestTime.setLayoutY(20);

    }

    private void createPutCounter() {
        putLabel = new Label();
        pane.getChildren().add(putLabel);
        putLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        putLabel.setLayoutY(40);
        bestPut = new Label();
        pane.getChildren().add(bestPut);
        bestPut.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        bestPut.setLayoutY(60);
    }

    public void stop() {
        System.out.println("Stopping game");
        if(service != null) service.shutdown(); // Stop updater
    }
    public void clearMap() {
        pane.getChildren().clear(); // Clear map
    }
}
