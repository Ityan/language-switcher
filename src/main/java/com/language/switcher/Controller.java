package com.language.switcher;

import com.sun.javafx.application.PlatformImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
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
    @FXML
    private ChoiceBox<Key> leftChoiceBox;
    @FXML
    private ChoiceBox<Key> rightChoiceBox;
    @FXML
    private Button saveBtn;
    @FXML
    private Button exitBtn;

    private Stage stage;

    public void initialize() {
        List<Key> keys = new ArrayList<>(Arrays.asList(Key.values()));

        if (MAC_OS) {
            keys.remove(Key.WINDOWS);
        } else {
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

            try {
                PropertiesConfiguration config = new PropertiesConfiguration(FILE_NAME);
                config.setProperty(FIRST_KEY, FIRST_VALUE.name());
                config.setProperty(SECOND_KEY, SECOND_VALUE.name());
                config.save();
            } catch (ConfigurationException e) {
                e.printStackTrace();
            }

            stage.hide();
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
