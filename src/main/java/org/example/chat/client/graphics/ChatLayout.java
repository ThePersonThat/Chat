package org.example.chat.client.graphics;

import com.gluonhq.charm.glisten.control.Avatar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import org.example.chat.client.message.Message;

public class ChatLayout {

    private static void setStyleLabelMessage (Label labelMessage, Label labelTime, boolean isYouSender) {
        if(isYouSender) {
            labelMessage.getStyleClass().add("label-message");
            labelTime.setTextAlignment(TextAlignment.RIGHT);
        } else {
            labelMessage.getStyleClass().add("label-message-other");
        }

        labelTime.getStyleClass().add("label-time");
        labelMessage.setWrapText(true);
        labelMessage.setTextAlignment(TextAlignment.JUSTIFY);
    }


    private static VBox setVBoxLayout (Label labelMessage, Label labelTime, boolean isYouSender) {
        VBox vBoxMessage = new VBox();
        vBoxMessage.getChildren().addAll(labelMessage, labelTime);
        vBoxMessage.setPadding(new Insets(15, 0, 0, 0));
        vBoxMessage.setSpacing(3);

        return vBoxMessage;
    }

    private static HBox setHBoxLayout (Avatar avatar, VBox VBoxLayout, boolean isYouSender) {
        HBox hBoxMessage = new HBox();
        hBoxMessage.setFillHeight(false);
        hBoxMessage.setSpacing(4);


        if(isYouSender) {
            hBoxMessage.getChildren().addAll(avatar, VBoxLayout);
            hBoxMessage.setAlignment(Pos.CENTER_LEFT);
        } else {
            hBoxMessage.getChildren().addAll(VBoxLayout, avatar);
            hBoxMessage.setAlignment(Pos.CENTER_RIGHT);
        }

        return hBoxMessage;
    }

    private static Avatar setAvatar() {
        Avatar avatar = new Avatar();
        avatar.setRadius(30);

        return avatar;
    }


    public static void setLayoutMessage(Message message, VBox chatBox, boolean isYouSender) {
        Label labelMessage = message.getLabelWithContent();
        Label labelTime = new Label(message.getTime());

        if(isYouSender) {
            setStyleLabelMessage(labelMessage, labelTime, true);
            Avatar avatar = setAvatar();
            VBox vBoxMessage = setVBoxLayout(labelMessage, labelTime, true);
            HBox hBoxMessage = setHBoxLayout(avatar, vBoxMessage, true);
            chatBox.getChildren().add(hBoxMessage);
        } else {
            setStyleLabelMessage(labelMessage, labelTime, false);
            Avatar avatar = setAvatar();
            VBox vBoxMessage = setVBoxLayout(labelMessage, labelTime, false);
            HBox hBoxMessage = setHBoxLayout(avatar, vBoxMessage, false);

            chatBox.getChildren().add(hBoxMessage);
        }
    }
}
