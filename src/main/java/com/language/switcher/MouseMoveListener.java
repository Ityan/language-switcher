package com.language.switcher;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.apache.commons.lang.StringUtils;

import java.awt.*;

import static com.language.switcher.Constants.*;

public class MouseMoveListener {

    private static final int DELAY = 200;

    private Robot robot;

    public void start() throws AWTException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (screenSize.getHeight() - 2);
        int width = (int) (screenSize.getWidth() - 2);
        Rectangle rectangle = new Rectangle(1, 1, width, height);
        robot = new Robot();
        robot.setAutoDelay(10);
        final boolean[] isEvent = {false};
        Executor executor = new Executor();

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(DELAY),
                        event -> {
                            Point coordinates = MouseInfo.getPointerInfo().getLocation();
                            if (!rectangle.contains(coordinates) && !isEvent[0]) {
                                isEvent[0] = true;

                                if (MAC_OS) {
                                    String currentLang = executor.currentLanguage();
                                    if (StringUtils.containsIgnoreCase(currentLang, DEFAULT_LANG)
                                            && !PREV_LANG.equalsIgnoreCase(DEFAULT_LANG)) {
                                        while (!StringUtils.containsIgnoreCase(executor.currentLanguage(), PREV_LANG)) {
                                            switchLang();
                                        }
                                    } else if (!StringUtils.containsIgnoreCase(currentLang, DEFAULT_LANG)) {
                                        PREV_LANG = currentLang;
                                        while (!StringUtils.containsIgnoreCase(executor.currentLanguage(), DEFAULT_LANG)) {
                                            switchLang();
                                        }
                                    }
                                } else {
                                    switchLang();
                                }
                            } else if (rectangle.contains(coordinates) && isEvent[0]) {
                                isEvent[0] = false;
                            }
                        }
                ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void switchLang() {
        robot.keyPress(FIRST_VALUE.value());
        robot.keyPress(SECOND_VALUE.value());
        robot.keyRelease(SECOND_VALUE.value());
        robot.keyRelease(FIRST_VALUE.value());
    }
}
