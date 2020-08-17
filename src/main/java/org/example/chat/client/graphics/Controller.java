package org.example.chat.client.graphics;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import com.gluonhq.charm.glisten.control.Avatar;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.example.chat.client.Message;
import org.example.chat.client.MessageImage;
import org.example.chat.client.MessageText;

// TODO: 1) scroll bar on hover;
// TODO: 2) Correct displaying time, sendMessageSelf


enum TypeMessage {
    IMAGE, TEXT
}

public class Controller {

    private volatile Message message;

    private volatile boolean isSendMessage = false;

    public ResourceBundle resources;

    public URL location;

    public TextField input;

    public VBox chatBox;

    public ScrollPane scrollPane;

    private double x, y;


    public void setMessageSelf(Message message) {
        ChatLayout.setLayoutMessage(message, chatBox, true);
    }

    public void setMessageOther(Message message) {
        ChatLayout.setLayoutMessage(message, chatBox, false);
    }


    public void initialize() {
        scrollPane.vvalueProperty().bind(chatBox.heightProperty());
    }


    public void CloseApp(MouseEvent event) {
        /*ClientProgramStatus.program.setRunning(false);

        try {
            ClientProgramStatus.program.getWriteTread().join();
            ClientProgramStatus.program.getReadThread().interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.exit();
        System.exit(0);*/
    }


    public void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    public void MinStage(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setIconified(true);
    }

    public void dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    public void checkKey(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            send(TypeMessage.TEXT);
        }
    }

    public Message waitMessage() {
        while (!isSendMessage) {
            Thread.onSpinWait();
        }


        if(message instanceof MessageText) {
            String msg = input.getText();
            input.clear();
            message.setContent(msg.getBytes());
        } else if(message instanceof MessageImage) {

            try {
                byte[] array = Files.readAllBytes(Paths.get("C:\\Users\\Alex\\IdeaProjects\\com.TestChat\\src\\main\\resources\\cat.jpg"));
                message.setContent(array);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        Platform.runLater(() -> setMessageSelf(message));
        isSendMessage = false;
        return message;
    }

    public void SendMessage(MouseEvent event) {
        send(TypeMessage.TEXT);
    }

    public void sendImage(MouseEvent event) {
        send(TypeMessage.IMAGE);
    }

    private void send(TypeMessage type) {
        if(type == TypeMessage.TEXT) {
            message = new MessageText();
        } else if(type == TypeMessage.IMAGE) {
            message = new MessageImage();
        }

        isSendMessage = true;
    }

}
