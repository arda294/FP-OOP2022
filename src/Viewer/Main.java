package Viewer;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    View viewer;

    @Override
    public void start(Stage primaryStage) {
        viewer = new MainView();
        primaryStage = viewer.getStage();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if(((MainView)viewer).getGame() != null) ((MainView)viewer).getGame().stop();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}