package Objects;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;

public class Wall extends Rectangle {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    public Wall() {
        super(WIDTH, HEIGHT);
        setFill(Color.BLUE);

    }

    public Wall(double sizex, double sizey) {
        super(sizex, sizey);
//        setFill(Color.web("#855626"));
        setFill(Color.web("#FFAA5E"));
        setStrokeWidth(5);
//        setStroke(Color.web("#452d15"));
        setStroke(Color.web("#C9864A"));
        setStrokeType(StrokeType.INSIDE);
        setArcHeight(10);
        setArcWidth(10);
    }

    public Wall(double sizex, double sizey, double posx, double posy) {
        this(sizex, sizey);
        setLayoutX(posx);
        setLayoutY(posy);
    }

    public Wall(double sizex, double sizey, double posx, double posy, double angle) {
        this(sizex, sizey);
        setLayoutX(posx);
        setLayoutY(posy);
        setRotate(angle);
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


