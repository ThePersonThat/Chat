package org.example.chat.client;

import javafx.scene.control.Label;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

abstract public class Message implements Serializable {

    protected String time;

    protected Message() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        this.time = format.format(LocalDateTime.now());
    }

    public String getTime() {
        return time;
    }

    abstract public void setContent(byte[] arrayContent);
    abstract public Label getLabelWithContent();
}
