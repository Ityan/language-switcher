package com.language.switcher;

import com.sun.javafx.application.PlatformImpl;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Tray {
    private static final Logger LOG = LogManager.getLogger(Tray.class);

    private static final String TRAY_ICON_PATH = "/tray.png";
    private SystemTray tray;
    private TrayIcon icon;
    private Stage stage;

    public Tray(Stage stage) {
        this.stage = stage;
    }

    public void enable() {
        SwingUtilities.invokeLater(() -> {
            try {
                this.tray = SystemTray.getSystemTray();
                this.icon = icon(stage);
                tray.add(icon);
            } catch (AWTException e) {
                LOG.error("Unable to init system tray", e);
            }
        });
    }

    private TrayIcon icon(Stage stage) {
        try {
            TrayIcon icon = new TrayIcon(ImageIO.read(getClass().getResource(TRAY_ICON_PATH)));
            icon.setImageAutoSize(true);
            icon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        PlatformImpl.runLater(() -> {
                            stage.show();
                            stage.toFront();
                        });
                    }
                }
            });
            return icon;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void disable() {
        SwingUtilities.invokeLater(()->tray.remove(icon));
    }
}
