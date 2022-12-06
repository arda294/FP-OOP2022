package Menu;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class LevelGetter {
    private ArrayList<String> levels = new ArrayList<>();
    public LevelGetter() throws NullPointerException {
        File dir = new File(System.getProperty("user.dir") + "/levels/");
        System.out.println("Levels dir: "+ System.getProperty("user.dir") + "\\levels");
        for(String lvl : dir.list()) {
            if(lvl.endsWith(".lvl")) levels.add(lvl); // check extension
        }
        System.out.println(levels);
    }

    public ArrayList<String> getLevels() {
        return levels;
    }
}
