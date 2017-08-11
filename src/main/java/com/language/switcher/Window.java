package com.language.switcher;

import com.sun.javafx.application.PlatformImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static com.language.switcher.Constants.*;

public class Window extends javafx.application.Application {

    private static final Logger LOG = LogManager.getLogger(Window.class);

    private boolean isFirstRun = false;
    private Tray tray;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        checkKeys();

        PlatformImpl.setImplicitExit(false);
        if (stage.getStyle() != StageStyle.UTILITY) {
            stage.initStyle(StageStyle.UTILITY);
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/window.fxml"));
        Parent root = loader.load();
        ((Controller) loader.getController()).setStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();

        if (isFirstRun) {
            stage.show();
        }

        new MouseMoveListener().start();

        if (SystemTray.isSupported()) {
            tray = new Tray(stage);
            tray.enable();
        }
    }

    private void checkKeys() throws IOException, ConfigurationException {
        File dir = new File(DIR_NAME);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                LOG.error("Can't create directory: " + dir.getAbsolutePath());
            }
        }
        File file = new File(FILE_NAME);
        PropertiesConfiguration config;
        if (!file.exists()) {
            if (file.createNewFile()) {
                isFirstRun = true;
                if (MAC_OS) {
                    FIRST_VALUE = Key.COMMAND;
                    SECOND_VALUE = Key.SPACE;
                } else if (WIN_OS) {
                    FIRST_VALUE = Key.WINDOWS;
                    SECOND_VALUE = Key.SPACE;
                } else {
                    FIRST_VALUE = Key.ALT;
                    SECOND_VALUE = Key.SHIFT;
                }

                config = new PropertiesConfiguration(FILE_NAME);

                config.setProperty(FIRST_KEY, FIRST_VALUE);
                config.setProperty(SECOND_KEY, SECOND_VALUE);

                config.save();
            } else {
                LOG.error("Can't create file" + FILE_NAME);
            }
        } else {
            config = new PropertiesConfiguration(FILE_NAME);
            FIRST_VALUE = Key.valueOf(config.getString(FIRST_KEY));
            SECOND_VALUE = Key.valueOf(config.getString(SECOND_KEY));
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        tray.disable();
    }
}