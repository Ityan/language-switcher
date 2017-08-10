package com.language.switcher;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.awt.*;

import static com.language.switcher.Constants.*;

public class MouseMoveListener {

    private static final int DELAY = 200;

    public void start() throws AWTException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (screenSize.getHeight() - 2);
        int width = (int) (screenSize.getWidth() - 2);
        Rectangle rectangle = new Rectangle(1, 1, width, height);
        Robot robot = new Robot();

        final boolean[] isEvent = {false};

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(DELAY),
                        event -> {
                            Point coordinates = MouseInfo.getPointerInfo().getLocation();
                            if (!rectangle.contains(coordinates) && !isEvent[0]) {
                                isEvent[0] = true;

                                robot.keyPress(FIRST_VALUE.value());
                                robot.keyPress(SECOND_VALUE.value());
                                robot.keyRelease(SECOND_VALUE.value());
                                robot.keyRelease(FIRST_VALUE.value());
                            } else if (rectangle.contains(coordinates) && isEvent[0]) {
                                isEvent[0] = false;
                            }
                        }
                ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
