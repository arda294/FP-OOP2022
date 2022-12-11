package Objects;

import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;

public class Ball extends Circle {
    private static final int RADIUS = 10;
    private double posX;
    private double posY;
    public double dx = 0;
    public double dy = 0;

    public double drag = 0.001;
    private double speedLimit = 2;
    private double power = 0;
    private double pullDistance = 100;
    private double px;
    private double py;
    public boolean isMoving = false;
    private int puts = 0;


    public Ball(double physicsFPS) {
        super(RADIUS);
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
        setStrokeWidth(4);
        posX = 400;
        posY = 300;
        speedLimit *= (1000/physicsFPS);
        drag *= (1000/physicsFPS);
        System.out.println(speedLimit);

        setOnMouseDragged((MouseEvent event) -> {
            if(!isMoving) {
                px = -event.getX();
                py = -event.getY();
                limitPull();
            }
        });

        setOnMouseReleased((MouseEvent event) -> {
            if(!isMoving) {
                dx = px*speedLimit;
                dy = py*speedLimit;
                System.out.println(dx + " " +dy);
                System.out.println("Shot with power : " + power);
                px = 0;
                py = 0;
                power = 0;
                isMoving = true;
                puts++;
            }
        });
    }

    public Ball(double physicsFPS, double startX, double startY) {
        this(physicsFPS);
        this.posX = startX;
        this.posY = startY;
    }



    private void limitPull() {
        double pull = Math.sqrt(px*px + py*py);
        if(pull > pullDistance) {
            power = 1;
            px = (px/pull);
            py = (py/pull);
            return;
        }
        power = pull/pullDistance;
        px = (px/pull)*(power);
        py = (py/pull)*(power);

    }

    private double clamp(double value, double ll, double ul) {
        if(value > ul) value = ul;
        if(value < ll) value = ll;
        return value;
    }

    public void moveBall(ArrayList<Wall> walls) {
        double speed = Math.sqrt(dx*dx + dy*dy);
        if(speed > 0) {
            dx -= (dx/speed) * drag;
            dy -= (dy/speed) * drag;
            if(speed < 0.1) {
                dx = 0;
                dy = 0;
                isMoving = false;
            }
        }
        if(posX+RADIUS >= getScene().getWidth() || posX-RADIUS <= 0) dx *= -1;
        if(posY+RADIUS >= getScene().getHeight() || posY-RADIUS <= 0)  dy *= -1;
        checkCollision(walls);
        posX += dx;
        posY += dy;
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
            posX = x1 + (ni*(RADIUS-distance));
            posY = y1 + (nj*(RADIUS-distance));
//            System.out.println((distance - RADIUS) + " abnormal collision");
        }
    }

    private void checkCollision(ArrayList<Wall> walls) {
        double distance;
        double x1, x2, y1, y2;
        double i, j;

        for(Wall wall : walls) {
            x1 = posX;
            y1 = posY;
            if (wall.getRotate() != 0) {
                double tempX = x1;
                x1 = rotateX(x1-wall.getCenterX(), y1-wall.getCenterY(), -wall.getRotate()) + wall.getCenterX();
                y1 = rotateY(tempX-wall.getCenterX(), y1-wall.getCenterY(), -wall.getRotate()) + wall.getCenterY();
                x2 = clamp(x1, wall.getCenterX() - wall.getWidth()/2, wall.getCenterX() + wall.getWidth()/2);
                y2 = clamp(y1, wall.getCenterY() - wall.getHeight()/2, wall.getCenterY() + wall.getHeight()/2);
            } else {
                x2 = clamp(x1, wall.getCenterX() - wall.getWidth()/2, wall.getCenterX() + wall.getWidth()/2);
                y2 = clamp(y1, wall.getCenterY() - wall.getHeight()/2, wall.getCenterY() + wall.getHeight()/2);
            }
            i = x1-x2;
            j = y1-y2;
            distance = Math.sqrt(i*i + j*j);
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
                break;
            }
        }
    }

    public void update() {
        setLayoutX(posX);
        setLayoutY(posY);
    }

    public boolean isInGoal(Goal goal) {
        double x = posX - goal.getLayoutX();
        double y = posY - goal.getLayoutY();
        double distance = Math.sqrt(x*x + y*y);
        if(distance < goal.getRadius()) {
            System.out.println("Goal!");
            setVisible(false);
            return true;
        }
        return false;
    }

    public double getPower() {
        return power;
    }

    public int getPuts() {
        return puts;
    }
}
