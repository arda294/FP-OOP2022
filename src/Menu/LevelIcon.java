package Menu;

import javafx.scene.control.Button;

public class LevelIcon extends Button {
    private String levelName;
    private String level;
    public LevelIcon(String levelpath) {
        super(levelpath.substring(0,levelpath.lastIndexOf(".")));
        this.level = levelpath;
        this.levelName = levelpath.substring(0,levelpath.lastIndexOf("."));
        setId("lvl-button");
    }

    public String getLevelName() {
        return levelName;
    }

    public String getLevel() {
        return level;
    }
}
