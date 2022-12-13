package Util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Timer {
    private Timeline tl;
    private KeyFrame kf;
    private int time = 0;
    public Timer() {
        kf = new KeyFrame(Duration.millis(10), event -> {
           time++;
        });
        tl = new Timeline(kf);
        tl.setCycleCount(Timeline.INDEFINITE);
    }

    public void start() {
        tl.play();
    }

    public void stop() {
        tl.stop();
    }


    public void reset() {
        tl.stop();
        time = 0;
    }

    @Override
    public String toString() {
        return Double.toString(time/100.0);
    }

    public int getTime() {
        return time;
    }
}
