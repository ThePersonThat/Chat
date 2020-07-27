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
import org.example.chat.client.ClientProgramStatus;


public class Controller {
    private volatile boolean login = false;
    private volatile boolean message = false;

    public ResourceBundle resources;

    public URL location;

    public TextArea output;

    public TextField input;

    private double x, y;

    public void CloseApp(MouseEvent event) {
        ClientProgramStatus.program.setRunning(false);

        try {
            ClientProgramStatus.program.getWriteTread().join();
            ClientProgramStatus.program.getReadThread().interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        while (!message && ClientProgramStatus.program.isRunning()) {
            Thread.onSpinWait();
        }

        String msg = input.getText();
        input.clear();
        message = false;
        return msg;
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
