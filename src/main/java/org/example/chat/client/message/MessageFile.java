package org.example.chat.client.message;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import org.example.chat.client.graphics.app.App;

import java.io.*;


public class MessageFile extends AbstractMessageFile {

    @Override
    public void setContent(byte[] arrayContent) {
        byteArray = arrayContent;
    }

    @Override
    public Label getLabelWithContent() {
        Label label = new Label(filename + extension);

        FontAwesomeIcon icon = FontAwesomeIcon.ARROW_CIRCLE_ALT_DOWN;
        FontAwesomeIconView view = new FontAwesomeIconView(icon);

        view.setSize("35");
        view.setCursor(Cursor.HAND);

        view.setOnMouseClicked(e -> {
            /*try {
                File tempFile = File.createTempFile(filename, extension);
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(byteArray);

                App.getServices().showDocument(tempFile.toURI().toString());
            } catch (IOException exception) {
                exception.printStackTrace();
            }*/

            /*
                There must be open browser and download file
            */
        });

        label.setGraphic(view);
        return label;
    }

    @Override
    public String getTypeMessage() {
        return "file";
    }
}
