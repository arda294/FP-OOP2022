package Util;

import javafx.util.Pair;

public class Vec2d <T extends Number> {
    private T x;
    private T y;

    public Vec2d(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public void rotate(T ang) {
        this.x = (T)(Double)((double)x*Math.cos(Math.toRadians((double)ang)) - (double)y*Math.sin(Math.toRadians((double)ang)));
        this.y= (T)(Double)((double)x*Math.sin(Math.toRadians((double)ang)) + (double)y*Math.cos(Math.toRadians((double)ang)));
    }

    private static double rotateX(double x, double y, double angle) {
        return x*Math.cos(Math.toRadians(angle)) - y*Math.sin(Math.toRadians(angle));
    }

    private static double rotateY(double x, double y, double angle) {
        return x*Math.sin(Math.toRadians(angle)) + y*Math.cos(Math.toRadians(angle));
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }
}
