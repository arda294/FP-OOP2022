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
        setLayoutX(300);
        setLayoutY(400);

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

        if(getLayoutX()+RADIUS >= getScene().getWidth() || getLayoutX()-RADIUS <= 0) dx *= -1;
        if(getLayoutY()+RADIUS >= getScene().getHeight() || getLayoutY()-RADIUS <= 0)  dy *= -1;

//        System.out.println(ball.getLayoutY())
        checkCollision(walls);
        setLayoutX(getLayoutX() + dx);
        setLayoutY(getLayoutY() + dy);
//        System.out.println(getLayoutX() + " " + getLayoutY());

    }

    private static double rotateX(double x, double y, double angle) {
        return x*Math.cos(Math.toRadians(angle)) - y*Math.sin(Math.toRadians(angle));
    }

    private static double rotateY(double x, double y, double angle) {
        return x*Math.sin(Math.toRadians(angle)) + y*Math.cos(Math.toRadians(angle));
    }
    private void anticlip(double x1, double y1, double distance, double ni, double nj, Wall wall) {
        if(distance < RADIUS) {
            if(wall.getRotate() != 0) {
                double tempX = x1;
                x1 = rotateX(x1-wall.getCenterX(), y1-wall.getCenterY(), wall.getRotate()) + wall.getCenterX();
                y1 = rotateY(tempX-wall.getCenterX(), y1-wall.getCenterY(), wall.getRotate()) + wall.getCenterY();
            }
            setLayoutX(x1 + (ni*(RADIUS-distance)));
            setLayoutY(y1 + (nj*(RADIUS-distance)));
            System.out.println(distance + " abnormal collision");
        }
    }

    private void checkCollision(ArrayList<Wall> walls) {
        double distance;
        double x1, x2, y1, y2;
        double i, j;

        for(Wall wall : walls) {
            x1 = getLayoutX();
            y1 = getLayoutY();
            if (wall.getRotate() != 0) {
                double tempX = x1;
                x1 = rotateX(x1-wall.getCenterX(), y1-wall.getCenterY(), -wall.getRotate()) + wall.getCenterX();
                y1 = rotateY(tempX-wall.getCenterX(), y1-wall.getCenterY(), -wall.getRotate()) + wall.getCenterY();
//                System.out.println(x1 + " " + y1);
                x2 = clamp(x1, wall.getCenterX() - wall.getWidth()/2, wall.getCenterX() + wall.getWidth()/2);
                y2 = clamp(y1, wall.getCenterY() - wall.getHeight()/2, wall.getCenterY() + wall.getHeight()/2);
            } else {
                x2 = clamp(x1, wall.getCenterX() - wall.getWidth()/2, wall.getCenterX() + wall.getWidth()/2);
                y2 = clamp(y1, wall.getCenterY() - wall.getHeight()/2, wall.getCenterY() + wall.getHeight()/2);
            }
            i = x1-x2;
            j = y1-y2;
            distance = Math.sqrt(i*i + j*j);
//            System.out.println(distance - RADIUS);
            if(distance < RADIUS) {
                System.out.println("Collision");
                double ni = i / distance;
                double nj = j / distance;
                if(distance == 0) {
                    ni = 0;
                    nj = 0;
                }

                if(wall.getRotate() != 0) {
                    double tempX = dx;
                    dx = rotateX(dx, dy, -wall.getRotate());
                    dy = rotateY(tempX, dy, -wall.getRotate());
                }

                if(x1 > wall.getLayoutX() + wall.getWidth() && y1 > wall.getLayoutY() + wall.getHeight() ||
                        x1 < wall.getLayoutX() && y1 > wall.getLayoutY() + wall.getHeight() ||
                        x1 > wall.getLayoutX() + wall.getWidth() && y1 < wall.getLayoutY() ||
                        x1 < wall.getLayoutX() && y1 < wall.getLayoutY()) {
                    anticlip(x1, y1, distance, ni, nj, wall);
                    double dotp = (dx * ni) + (dy * nj);
                    dx -= (2 * dotp * ni);
                    dy -= (2 * dotp * nj);
                } else if (x1 > wall.getLayoutX() && x1 < wall.getLayoutX() + wall.getWidth()) {
                    dy *= -1;
                    anticlip(x1, y1, distance, ni, nj, wall);
                } else if (y1 > wall.getLayoutY() && y1 < wall.getLayoutY() + wall.getHeight()){
                    dx *= -1;
                    anticlip(x1, y1, distance, ni, nj, wall);
                } else {
                    dx = 0;
                    dy = 0;
                }
                if(wall.getRotate() != 0) {
                    double tempX = dx;
                    dx = rotateX(dx, dy, wall.getRotate());
                    dy = rotateY(tempX, dy, wall.getRotate());
                }
                System.out.println("Collision");
                break;
            }
        }
    }
}
