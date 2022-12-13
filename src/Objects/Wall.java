package Objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Wall extends Rectangle {

    public Wall() {
        super(100, 100);
        setFill(Color.BLUE);
    }

    public Wall(double sizex, double sizey) {
        super(sizex, sizey);
        setFill(Color.web("#FFAA5E"));
        setStrokeWidth(5);
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

    public double getCenterX() {
        return getLayoutX() + getWidth()/2;
    }

    public double getCenterY() {
        return getLayoutY() + getHeight()/2;
    }
}


