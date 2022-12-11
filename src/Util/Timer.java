package Util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Timer {
    private Timeline tl;
    private KeyFrame kf;
    private int seconds = 0;
    public Timer() {
        kf = new KeyFrame(Duration.seconds(1), event -> {
           seconds++;
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
        seconds = 0;
    }

    @Override
    public String toString() {
        return seconds/60 + ":" + seconds%60;
    }

    public int getSeconds() {
        return seconds;
    }
}
