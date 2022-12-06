package Viewer;

import Menu.PlayMenu;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class View {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
    private Stage stage;
    private Scene scene;
    private AnchorPane pane;
    private ArrayList<Button> buttons = new ArrayList<>();
    private PlayMenu playMenu;
    private Game game;
    public View() {
        initializeView();
        createButtons();
        System.out.println("Viewer created");
    }

    private void initializeView() {
        pane = new AnchorPane();
        scene = new Scene(pane, WIDTH, HEIGHT);
        stage = new Stage();
        pane.setStyle("-fx-background-color: transparent ;");
        stage.setResizable(false);
        scene.setFill(Color.web("#81c483"));
        stage.setScene(scene);
        stage.setTitle("Mini Golf");
        stage.getIcons().add(new Image("resources/ball.png"));
        playMenu = new PlayMenu(this);
        pane.getChildren().add(playMenu);
        game = new Game(this);
        scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/styles/buttons.css");
        createBanner();
    }
    public Stage getStage() {
        return stage;
    }

    private void createButtons() {
        createPlayButton();
    }
    private void createPlayButton() {
        Button startButton = new Button("Play");
        startButton.setId("menu-button");
        startButton.setLayoutX(400-40);
        startButton.setLayoutY(150+5);

        startButton.setOnAction((ActionEvent event) -> {
            if(playMenu.isHidden) {
                playMenu.toggleMenu();
                startButton.setText("Back");
            } else {
                playMenu.toggleMenu();
                startButton.setText("Play");
            }
        });
        pane.getChildren().add(startButton);
        buttons.add(startButton);
    }

    private void createBanner() {
        ImageView banner = new ImageView("resources/banner.png");
        banner.setFitWidth(500);
        banner.setPreserveRatio(true);
        banner.setLayoutX(150);
        banner.setLayoutY(50);
        pane.getChildren().add(banner);
    }

    public Game getGame() {
        return game;
    }
}
