package org.example.chat.client.message;

import javafx.scene.control.Label;

public class MessageText extends Message {

    private String message;

    public String getMessage() {
        return message;
    }

    @Override
    public void setContent(byte[] arrayContent) {
        message = new String(arrayContent);
    }

    @Override
    public Label getLabelWithContent() {
        Label label = new Label(message);

        return label;
    }
}
