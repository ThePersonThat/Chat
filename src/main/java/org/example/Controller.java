package org.example;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller {

    public ResourceBundle resources;

    public URL location;

    public TextArea output;

    public TextField input;

    private double x, y;

    public void CloseApp(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    public void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    public void MinStage(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setIconified(true);
    }


    public void SendMessage(MouseEvent event) {
        send();
    }

    public void dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    public void checkKey(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            send();
        }
    }

    private void send() {
        output.setText("Alex: " + input.getText());
        input.clear();
    }

    public void initialize() {
        
    }
}
