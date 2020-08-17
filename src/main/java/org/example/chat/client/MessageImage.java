package org.example.chat.client;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class MessageImage extends Message {

    private byte[] image;

    @Override
    public void setContent(byte[] arrayContent) {
        image = arrayContent;
    }

    @Override
    public Label getLabelWithContent() {
        ByteArrayInputStream stream = new ByteArrayInputStream(image);
        Image image = new Image(stream);
        Label label = new Label();
        ImageView view = new ImageView(image);
        label.setGraphic(view);

        return label;
    }
}
