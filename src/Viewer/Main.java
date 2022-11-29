package Viewer;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        View viewer = new View();
        Game gameview = new Game();
//        primaryStage = viewer.getStage();
        primaryStage = gameview.getStage();
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}