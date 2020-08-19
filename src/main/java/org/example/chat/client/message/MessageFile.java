package org.example.chat.client.message;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import org.example.chat.client.graphics.app.App;

import java.io.*;



public class MessageFile extends Message {

    private byte[] file;
    private String name;
    private String extension;

    @Override
    public void setContent(byte[] arrayContent) {
        file = arrayContent;

    }

    public void setNameFile(String fileName) {
        name = FileName.getNameWithoutExtension(fileName);
        extension = FileName.getFileExtension(fileName);
    }



    @Override
    public Label getLabelWithContent() {
        Label label = new Label(name + extension);
        FontAwesomeIcon icon = FontAwesomeIcon.ARROW_CIRCLE_ALT_DOWN;
        FontAwesomeIconView view = new FontAwesomeIconView(icon);
        view.setSize("35");
        view.setCursor(Cursor.HAND);


        view.setOnMouseClicked(e -> {
            try {
                File tempFile = File.createTempFile(name, extension);
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(file);

                App.getServices().showDocument(tempFile.toURI().toString());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        label.setGraphic(view);
        return label;
    }
}
