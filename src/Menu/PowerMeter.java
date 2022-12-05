package Menu;

import Objects.Ball;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PowerMeter extends Rectangle {
    private final double MAX_LENGTH = 100;
    private final double posX = 800-40;
    private final double posY = 600-20;
    public PowerMeter() {
        super(20,0, Color.RED);
        setLayoutX(posX);
        setLayoutY(posY);
    }

    public void setLength(double power) {
        setHeight(MAX_LENGTH * power);
        setLayoutY(posY-(MAX_LENGTH*power));
    }
}
