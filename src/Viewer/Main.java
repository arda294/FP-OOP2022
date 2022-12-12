package Viewer;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URLClassLoader;

public class Main extends Application {
    View viewer;
//    Game gameview;
    @Override
    public void start(Stage primaryStage) {
//        System.out.println(getClass().getResource("/Resources/ball.png"));
        viewer = new View();
//        gameview = new Game();
//        Stage stage2;
        primaryStage = viewer.getStage();
//        stage2 = viewer.getStage();
//        primaryStage = gameview.getStage();
        primaryStage.show();
//        stage2.show();
    }
    @Override
    public void stop() throws Exception {
        if(viewer.getGame() != null) viewer.getGame().stop();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

//    Casting
//    Overloading
//    Overriding
//    Inheritance
//    Encapsulation
//    Constructor
//    Collection
//    Exception Handling
//    Input Output

}