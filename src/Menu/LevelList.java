package Menu;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;
import java.util.HashSet;

public class LevelList extends SubScene {

    private ScrollPane pane;
    private TilePane tilepane = new TilePane();
    private ArrayList<LevelIcon> buttons = new ArrayList<>();
    private static final double padding = 50;
    private int selectedIdx = 0;

    public LevelList(ArrayList<String> levels) {
        super(new ScrollPane(), 500-padding, 400-padding);
        initializeLevelList();
        addButtons(levels);
    }

    private void initializeLevelList() {
        pane = (ScrollPane)this.getRoot();
        pane.setContent(tilepane);
        pane.setLayoutX(padding);
        pane.setLayoutY(padding);
        tilepane.setPadding(new Insets(10,10,10,10));
        tilepane.setPrefColumns(4);
        tilepane.setHgap(20);
        tilepane.setVgap(20);
    }

    private void addButtons(ArrayList<String> levels) {
        for(String lvl : levels) {
            buttons.add(new LevelIcon(lvl));
        }
        for(LevelIcon btn : buttons) {
            tilepane.getChildren().add(btn);
            btn.setOnAction((ActionEvent event) -> {
                selectedIdx = buttons.indexOf(btn);
                System.out.println( "Selected : " + selectedIdx);
            });
        }
    }

    public String getLevel() {
        return buttons.get(selectedIdx).getLevel();
    }
}
