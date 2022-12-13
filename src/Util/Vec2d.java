package Util;

public class Vec2d extends Pair<Double> {
    public Vec2d() {
        super();
        setX(0);
        setY(0);
    }

    public Vec2d(double x, double y) {
        this();
        setX(x);
        setY(y);
    }

    public void rotate(double angle) {
        double temp = getX();
        setX(getX()*Math.cos(Math.toRadians(angle)) - getY()*Math.sin(Math.toRadians(angle)));
        setY(temp*Math.sin(Math.toRadians(angle)) + getY() * Math.cos(Math.toRadians(angle)));
    }

    public void rotate(Vec2d axis, double angle) {
        substract(axis);
        rotate(angle);
        add(axis);
    }

    public void multiply(double val) {
        setX(getX()*val);
        setY(getX()*val);
    }

    public void add(Vec2d v) {
        setX(getX()+v.getX());
        setY(getY()+v.getY());
    }

    public void add(double x, double y) {
        setX(getX()+x);
        setY(getY()+y);
    }

    public void substract(Vec2d v) {
        setX(getX()-v.getX());
        setY(getY()-v.getY());
    }

    public void copy(Vec2d v) {
        setX(v.getX());
        setY(v.getY());
    }

    public double length() {
        return Math.sqrt(getX()*getX() + getY()*getY());
    }

    public Vec2d normalized() {
        Vec2d v = new Vec2d();
        v.setX(getX()/length());
        v.setY(getY()/length());
        return v;
    }

    public static double dot(Vec2d v1, Vec2d v2) {
        return (v1.getX()*v2.getX()) + (v1.getY()*v2.getY());
    }

    public void setX(double x) {
        setKey(x);
    }

    public void setY(double y) {
        setValue(y);
    }

    public void setXY(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getX() {
        return getKey();
    }

    public double getY() {
        return getValue();
    }

    @Override
    public String toString() {
        return "("+getX()+","+getY()+")";
    }
}
