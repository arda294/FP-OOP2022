package Util;

import Objects.Wall;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class LevelLoader {
    private File levelTxt;
    private Scanner sc;
    private double[] start = {0,0};
    private double[] end = {0,0};

    public ArrayList<Wall> loadLevel(String fileName) {
        try {
            levelTxt = new File("levels/"+fileName);
            sc = new Scanner(levelTxt);
        } catch (FileNotFoundException e) {
            System.out.println("Error in loading level");
            e.printStackTrace();
            return null;
        }
        ArrayList<Wall> walls = new ArrayList<>();
        double length, width, posx, posy, angle;
        start[0] = sc.nextDouble();
        start[1] = sc.nextDouble();
        end[0] = sc.nextDouble();
        end[1] = sc.nextDouble();

        while(sc.hasNext()) {
            length = sc.nextDouble();
            width = sc.nextDouble();
            posx = sc.nextDouble();
            posy = sc.nextDouble();
            angle = sc.nextDouble();
            walls.add(new Wall(length, width, posx, posy, angle));
        }
        return walls;
    }

    public double startX() {
        return start[0];
    }

    public double startY() {
        return start[1];
    }

    public double endX() {
        return end[0];
    }

    public double endY() {
        return end[1];
    }
}
