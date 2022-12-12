package Menu;

import Util.LevelGetter;
import Viewer.View;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;

public class PlayMenu extends SubScene {
    public boolean isHidden = true;
    private AnchorPane pane2;
    private LevelGetter lvlselect = new LevelGetter();
    private ArrayList<Button> buttons = new ArrayList<>();
    private LevelList levelList;
    private String selectedLvl;
    private View viewer;
    public PlayMenu(View viewer) {
        super(new AnchorPane(), 500,400);
        this.viewer = viewer;
        pane2 = (AnchorPane) this.getRoot();
        pane2.setStyle("-fx-background-color: transparent ;");
        levelList = new LevelList(lvlselect.getLevels());
        pane2.getChildren().add(levelList);
        createStartButton();
        setFill(Color.web("#ffff99"));
        getStyleClass().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/styles/buttons.css");
        setLayoutX(150);
        setLayoutY(850);
    }

    public void toggleMenu() {
        if(isHidden) {
//            this.setLayoutY(150);
            isHidden = false;
            System.out.println("Entered level menu");
            showMenu();
        } else {
//            this.setLayoutY(1000);
            isHidden = true;
            System.out.println("Exited level menu");
            hideMenu();
        }
    }

    private void showMenu() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);
        transition.setToY(-700);
        transition.play();
    }

    private void hideMenu() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);
        transition.setToY(0);
        transition.play();
    }

    private void createStartButton() {
        Button start = new Button("Start");
        start.setId("menu-button");
        start.setOnAction((ActionEvent event) -> {
            viewer.getGame().createNewGame(levelList.getLevel());
        });
        start.setLayoutX(250-40);
        start.setLayoutY(400-45);
        pane2.getChildren().add(start);
    }
}
