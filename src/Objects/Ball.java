package Objects;

import Util.Vec2d;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;

public class Ball extends Circle {
    private static final int RADIUS = 10;
    private Vec2d pos = new Vec2d();
    private Vec2d ds = new Vec2d();
    private Vec2d pv = new Vec2d();
    private double drag = 0.001;
    private double speedLimit = 2;
    private double power = 0;
    private final double pullDistance = 100;
    private boolean isMoving = false;
    private int puts = 0;


    public Ball(double physicsFPS) {
        super(RADIUS);
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
        setStrokeWidth(4);
        speedLimit *= (1000/physicsFPS);
        drag *= (1000/physicsFPS);

        setOnMouseDragged((MouseEvent event) -> {
            if(!isMoving) pull(event);
        });

        setOnMouseReleased((MouseEvent event) -> {
            if(!isMoving) shoot();
        });
    }

    public Ball(double physicsFPS, double startX, double startY) {
        this(physicsFPS);
        pos.setX(startX);
        pos.setY(startY);
    }

    private void pull(MouseEvent event) {
        pv.setX(-event.getX());
        pv.setY(-event.getY());
        power = pv.length()/pullDistance;
        power = Math.min(power,1);
        pv = pv.normalized();
        pv.setX(pv.getX()*power);
        pv.setY(pv.getY()*power);
    }

    private void shoot() {
        ds.setX(pv.getX()*speedLimit);
        ds.setY(pv.getY()*speedLimit);
        System.out.println(ds.getX() + " " +ds.getY());
        System.out.println("Shot with power : " + power);
        pv.setX(0);
        pv.setY(0);
        power = 0;
        isMoving = true;
        puts++;
    }


    private double clamp(double value, double ll, double ul) {
        if(value > ul) value = ul;
        if(value < ll) value = ll;
        return value;
    }

    // Anti ball clip
    private void anticlip(Vec2d p, Vec2d n, double distance, Wall wall) {
        if(distance < RADIUS) {
            if(wall.getRotate() != 0) p.rotate(new Vec2d(wall.getCenterX(), wall.getCenterY()), wall.getRotate());
            pos.setX(p.getX() + (n.getX()*(RADIUS-distance)));
            pos.setY(p.getY() + (n.getY()*(RADIUS-distance)));
        }
    }

    // Check wall collisions
    private void checkWall(Wall wall, Vec2d p1, Vec2d v, double distance) {
        System.out.println("Collision");
        Vec2d n = new Vec2d();
        n.copy(v);
        n = n.normalized();
        if(distance == 0) n.copy(new Vec2d(0,0));
        if(wall.getRotate() != 0) ds.rotate(-wall.getRotate());
        if(p1.getX() > wall.getLayoutX() + wall.getWidth() && p1.getY() > wall.getLayoutY() + wall.getHeight() ||
                p1.getX() < wall.getLayoutX() && p1.getY() > wall.getLayoutY() + wall.getHeight() ||
                p1.getX() > wall.getLayoutX() + wall.getWidth() && p1.getY() < wall.getLayoutY() ||
                p1.getX() < wall.getLayoutX() && p1.getY() < wall.getLayoutY()) {
            anticlip(p1, n, distance, wall);
            double dotp = Vec2d.dot(ds,n);
            ds.setX(ds.getX() - (2 * dotp * n.getX()));
            ds.setY(ds.getY() - (2 * dotp * n.getY()));
        } else if (p1.getX() > wall.getLayoutX() && p1.getX() < wall.getLayoutX() + wall.getWidth()) {
            anticlip(p1, n, distance, wall);
            ds.setY(ds.getY()*-1);
        } else if (p1.getY() > wall.getLayoutY() && p1.getY() < wall.getLayoutY() + wall.getHeight()){
            anticlip(p1, n, distance, wall);
            ds.setX(ds.getX()*-1);
        } else {
            ds.setXY(0,0);
        }
        if(wall.getRotate() != 0) ds.rotate(wall.getRotate());
    }

    private void checkCollision(ArrayList<Wall> walls) {
        double distance;
        Vec2d p1 = new Vec2d();
        Vec2d p2 = new Vec2d();
        Vec2d v = new Vec2d();
        for(Wall wall : walls) {
            p1.copy(pos);
            if (wall.getRotate() != 0) p1.rotate(new Vec2d(wall.getCenterX(), wall.getCenterY()), -wall.getRotate());
            p2.setX(clamp(p1.getX(), wall.getCenterX() - wall.getWidth()/2, wall.getCenterX() + wall.getWidth()/2));
            p2.setY(clamp(p1.getY(), wall.getCenterY() - wall.getHeight()/2, wall.getCenterY() + wall.getHeight()/2));
            v.copy(p1);
            v.substract(p2);
            distance = v.length();
            if(distance > RADIUS) continue;
            checkWall(wall, p1, v, distance);
            break;
        }
    }

    // Move ball coords
    public void moveBall(ArrayList<Wall> walls) {
        double speed = ds.length();
        if(speed > 0) {
            ds.setX(ds.getX() - ((ds.getX()/speed) * drag));
            ds.setY(ds.getY() - ((ds.getY()/speed) * drag));
            if(speed < 0.1) {
                ds.setX(0);
                ds.setY(0);
                isMoving = false;
            }
        }
        if(pos.getX()+RADIUS >= getScene().getWidth() || pos.getX()-RADIUS <= 0) ds.setX(ds.getX()*-1);
        if(pos.getY()+RADIUS >= getScene().getHeight() || pos.getY()-RADIUS <= 0)  ds.setY(ds.getY()*-1);
        checkCollision(walls);
        pos.setX(pos.getX() + ds.getX());
        pos.setY(pos.getY() + ds.getY());
    }

    // Update ball pos
    public void update() {
        setLayoutX(pos.getX());
        setLayoutY(pos.getY());
    }

    public boolean isInGoal(Goal goal) {
        Vec2d v = new Vec2d(pos.getX() - goal.getLayoutX(), pos.getY() - goal.getLayoutY());
        if(v.length() < goal.getRadius()) {
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
