package Util;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LevelGetter {
    private ArrayList<String> levels = new ArrayList<>();
    public LevelGetter() {
        File dir = new File("levels");
        for(String lvl : dir.list()) {
            if(lvl.endsWith(".lvl")) levels.add(lvl); // check extension
        }
        System.out.println(levels);
    }

    public ArrayList<String> getLevels() {
        return levels;
    }
}
