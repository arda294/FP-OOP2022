package Viewer;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    View viewer;
    Game gameview;
    @Override
    public void start(Stage primaryStage) {
        viewer = new View();
        gameview = new Game();
        Stage stage2;
        primaryStage = viewer.getStage();
        stage2 = viewer.getStage();
        primaryStage = gameview.getStage();
        primaryStage.show();
        stage2.show();
    }
    @Override
    public void stop() throws Exception {
        gameview.stop();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}