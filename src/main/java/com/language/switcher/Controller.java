package com.language.switcher;

import com.sun.javafx.application.PlatformImpl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.language.switcher.Constants.*;
import static com.language.switcher.Constants.FILE_NAME;
import static com.language.switcher.Constants.FIRST_VALUE;
import static com.language.switcher.Constants.SECOND_VALUE;

public class Controller {

    private static final int DELAY = 500;

    @FXML
    public Label defaultLang;
    @FXML
    public Label langLabel;
    @FXML
    public Button changeBtn;
    @FXML
    public Button saveLangBtn;
    @FXML
    public Label saveLangLabel;
    @FXML
    public VBox langBox;
    @FXML
    public Button cancelLangBtn;
    @FXML
    private ChoiceBox<Key> leftChoiceBox;
    @FXML
    private ChoiceBox<Key> rightChoiceBox;
    @FXML
    private Button saveBtn;
    @FXML
    private Button exitBtn;

    private Stage stage;
    private Executor executor = new Executor();

    public void initialize() {
        List<Key> keys = new ArrayList<>(Arrays.asList(Key.values()));
        Timeline timeline = initTimeLine();
        langBox.setVisible(false);

        if (MAC_OS) {
            keys.remove(Key.WINDOWS);
            defaultLang.setText(DEFAULT_LANG);
        } else {
            langLabel.setVisible(false);
            defaultLang.setVisible(false);
            changeBtn.setVisible(false);
            keys.remove(Key.COMMAND);
        }

        leftChoiceBox.getItems().addAll(keys);
        rightChoiceBox.getItems().addAll(keys);

        leftChoiceBox.getSelectionModel().select(FIRST_VALUE);
        rightChoiceBox.getSelectionModel().select(SECOND_VALUE);

        exitBtn.setOnAction(event -> PlatformImpl.exit());
        saveBtn.setOnAction(event -> {
            FIRST_VALUE = leftChoiceBox.getSelectionModel().getSelectedItem();
            SECOND_VALUE = rightChoiceBox.getSelectionModel().getSelectedItem();
            DEFAULT_LANG = defaultLang.getText();

            try {
                PropertiesConfiguration config = new PropertiesConfiguration(FILE_NAME);
                config.setProperty(FIRST_KEY, FIRST_VALUE.name());
                config.setProperty(SECOND_KEY, SECOND_VALUE.name());
                config.setProperty(LANGUAGE_KEY, DEFAULT_LANG);
                config.save();
            } catch (ConfigurationException e) {
                e.printStackTrace();
            }

            stage.hide();
        });
        changeBtn.setOnAction(event -> {
            timeline.play();
            langBox.setVisible(true);
            stage.setWidth(550);
        });
        saveLangBtn.setOnAction(event -> {
            timeline.pause();
            defaultLang.setText(saveLangLabel.getText());
            langBox.setVisible(false);
            stage.setWidth(350);
        });
        cancelLangBtn.setOnAction(event -> {
            timeline.pause();
            langBox.setVisible(false);
            stage.setWidth(350);
        });
    }

    private Timeline initTimeLine() {
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(DELAY),
                        event -> {
                            String lang = executor.currentLanguage();
                            if (!lang.equals(saveLangLabel.getText())) {
                                saveLangLabel.setText(lang);
                            }
                        }
                ));
        timeline.setCycleCount(Timeline.INDEFINITE);

        return timeline;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
