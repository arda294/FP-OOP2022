package Viewer;

import Objects.Ball;
import Objects.Wall;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Game {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
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

    private void createNewGame() {
        ball = new Ball();
        pane.getChildren().add(ball);
        walls.add(new Wall(100,100));
        walls.add(new Wall(300,100));
        pane.getChildren().add(walls.get(0));
        pane.getChildren().add(walls.get(1));

        createGameLoop();
    }

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ball.moveBall(walls);
            }
        };
        gameTimer.start();
    }


}
