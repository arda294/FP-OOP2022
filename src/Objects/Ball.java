package Objects;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Ball extends Circle {
    private static final int RADIUS = 20;
    public double dx = 0;
    public double dy = 0;
    public double drag = 0.035;
    private double speedLimit = 20;
    private double multiplier = 0.05;
    public boolean isMoving = false;

    public Ball() {
        super(RADIUS);
        setLayoutX(400);
        setLayoutY(300);

        setOnMouseReleased((MouseEvent event) -> {
            if(!isMoving) {
                dx = -event.getX() * multiplier;
                dy = -event.getY() * multiplier;
                limitSpeed();
                System.out.println(dx + " " +dy);
                System.out.println("Shot with power : " + Math.sqrt(dx*dx + dy*dy)/speedLimit);
                isMoving = true;
            }
        });
    }

    private void limitSpeed() {
        double speed = Math.sqrt(dx*dx + dy*dy);
        if(speed > speedLimit) {
            dx = (dx/speed)*speedLimit;
            dy = (dy/speed)*speedLimit;
        }
    }

    private double clamp(double value, double ll, double ul) {
        if(value > ul) value = ul;
        if(value < ll) value = ll;
        return value;
    }

    public void moveBall(ArrayList<Wall> walls) {
        double speed = Math.sqrt(dx*dx + dy*dy);
//        System.out.println(speed);
        if(speed > 0) {
            dx -= (dx/speed) * drag;
            dy -= (dy/speed) * drag;
            if(speed < 0.1) {
                dx = 0;
                dy = 0;
                isMoving = false;
            }
        }
        checkCollision(walls);
        if(getLayoutX()+RADIUS >= getScene().getWidth() || getLayoutX()-RADIUS <= 0) dx *= -1;
        if(getLayoutY()+RADIUS >= getScene().getHeight() || getLayoutY()-RADIUS <= 0)  dy *= -1;
//        System.out.println(ball.getLayoutY())
        setLayoutX(getLayoutX() + dx);
        setLayoutY(getLayoutY() + dy);
    }

    private void checkCollision(ArrayList<Wall> walls) {
        double distance;
        double x1, x2, y1, y2;
        double i, j;
        x1 = getLayoutX();
        y1 = getLayoutY();
        for(Wall wall : walls) {
            x2 = clamp(x1, wall.getLayoutX(), wall.getLayoutX() + wall.getWidth());
            y2 = clamp(y1, wall.getLayoutY(), wall.getLayoutY() + wall.getHeight());
            i = x2-x1;
            j = y2-y1;
            distance = Math.sqrt(i*i + j*j);
            if(distance < RADIUS) {
//                if((x2 == wall.getLayoutX() && y2 == wall.getLayoutY()) || (
//                        x2 == wall.getLayoutX() + wall.getWidth() && y2 == wall.getLayoutY() + wall.getHeight()
//                        )) {
//                    System.out.println("Not now");
//                }
                if (x1-RADIUS < wall.getLayoutX() || x1+RADIUS > wall.getLayoutX() + wall.getWidth()) {
                    dx *= -1;
                } else {
                    dy *= -1;
                }
                System.out.println("Collision");
            }
        }
    }
}
