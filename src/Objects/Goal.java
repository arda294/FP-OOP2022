package Objects;

import javafx.scene.shape.Circle;

public class Goal extends Circle {
    private double RADIUS = 12;
    public Goal(double posX, double posY) {
        super(12);
        setLayoutX(posX);
        setLayoutY(posY);
    }
}
