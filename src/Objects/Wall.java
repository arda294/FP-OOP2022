package Objects;

import javafx.scene.shape.Rectangle;

public class Wall extends Rectangle {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    public Wall(double posx, double posy) {
        super(WIDTH, HEIGHT);
        setLayoutX(posx);
        setLayoutY(posy);
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getWIDTH() {
        return WIDTH;
    }
}


