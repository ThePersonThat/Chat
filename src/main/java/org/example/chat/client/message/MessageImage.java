package org.example.chat.client.message;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class MessageImage extends Message {

    @Override
    public void setContent(byte[] arrayContent) {
        byteArray = arrayContent;
    }

    @Override
    public Label getLabelWithContent() {
        double height = 400;
        double width = 650;

        ByteArrayInputStream stream = new ByteArrayInputStream(byteArray);
        Image image = new Image(stream);
        Label label = new Label();

        if(image.getHeight() < height)
            height = image.getHeight();

        if(image.getWidth() < width)
            width = image.getWidth();

        ImageView view = new ImageView(image);
        view.setFitHeight(height);
        view.setFitWidth(width);
        label.setGraphic(view);

        return label;
    }

    @Override
    public String getTypeMessage() {
        return "image";
    }
}
