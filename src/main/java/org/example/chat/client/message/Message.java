package org.example.chat.client.message;

import javafx.scene.control.Label;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

abstract public class Message implements Serializable {

    protected String time;
    protected byte[] byteArray;

    protected Message() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        this.time = format.format(LocalDateTime.now());
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public byte[] getContentInBytes() {
        return byteArray;
    }

    abstract public void setContent(byte[] arrayContent);
    abstract public Label getLabelWithContent();
    abstract public String getTypeMessage();
}
