package org.example;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller {

    private volatile boolean login = false;
    private volatile boolean message = false;
    private volatile boolean isRunning = true;

    public ResourceBundle resources;

    public URL location;

    public TextArea output;

    public TextField input;

    private double x, y;

    public void CloseApp(MouseEvent event) {
        shutDown();
    }

    public void exit() {
        shutDown();
    }

    private void shutDown() {
        isRunning = false;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}

        Platform.exit();
        System.exit(0);
    }

    public boolean isRunning() {
        return isRunning;
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


    public String inputLogin() {
        output.setText("Enter your name and send it: ");

        while (!login) {
            Thread.onSpinWait();
        }

        String name = input.getText();
        input.clear();
        output.clear();
        return name;
    }

    public String waitMessage() {
        while (!message && isRunning) {
            Thread.onSpinWait();
        }

        if(isRunning) {
            String msg = input.getText();
            input.clear();
            message = false;
            return msg;
        } else return "Disconnect";
    }

    public void setMessage(String msg) {
        output.appendText(msg + "\n");
    }


    public void initialize() {

    }

    private void send() {
        if(input.getText().isEmpty())
            return;

        if(!login) {
            login = true;
        } else {
            message = true;
        }
    }

}
