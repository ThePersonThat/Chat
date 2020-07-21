package org.example;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {


    public ResourceBundle resources;


    public URL location;


    public TextArea Output;


    public TextField Input;


    public Button Send;


    public void initialize() {
        Send.setOnAction(actionEvent -> {
            Output.setText("Alex: " + Input.getText());
        });

    }
}
