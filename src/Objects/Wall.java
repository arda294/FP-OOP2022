package Objects;

import javafx.scene.shape.Rectangle;

public class Wall extends Rectangle {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    public Wall() {
        super(WIDTH, HEIGHT);
        setRotate(45);
    }

    public Wall(double sizex, double sizey) {
        super(sizex, sizey);
        setRotate(45);
    }

    public Wall(double sizex, double sizey, double posx, double posy) {
        this(sizex, sizey);
        setLayoutX(posx);
        setLayoutY(posy);
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public double getCenterX() {
        return getLayoutX() + getWidth()/2;
    }

    public double getCenterY() {
        return getLayoutY() + getHeight()/2;
    }
}


