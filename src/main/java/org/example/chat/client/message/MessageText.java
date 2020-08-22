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
        byteArray = arrayContent;
    }

    @Override
    public Label getLabelWithContent() {

        return new Label(message);
    }

    @Override
    public String getTypeMessage() {
        return "text";
    }
}
