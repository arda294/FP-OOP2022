package Menu;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class PlayMenu extends SubScene {
    public boolean isHidden = true;
    private AnchorPane pane2;
    public PlayMenu() {
        super(new AnchorPane(), 500,400);
        pane2 = (AnchorPane)this.getRoot();
        pane2.setStyle("-fx-background-color: transparent ;");
        setFill(Color.web("#ffff99"));
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
}
