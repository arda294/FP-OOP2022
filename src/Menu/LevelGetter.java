package Menu;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;

public class LevelGetter {
    private HashSet<String> levels = new HashSet<>();
    public LevelGetter() {
        File dir = new File(System.getProperty("user.dir") + "/levels/");
        System.out.println("Levels dir: "+ System.getProperty("user.dir") + "\\levels");
        Collections.addAll(levels, dir.list());

        System.out.println(levels);
    }

    public HashSet<String> getLevels() {
        return levels;
    }
}
