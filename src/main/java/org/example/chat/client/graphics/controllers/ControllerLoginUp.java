package org.example.chat.client.graphics.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.chat.client.Client;
import org.example.chat.client.graphics.app.App;

import java.net.Socket;

public class ControllerLoginUp extends AbstractController {

    private Socket socket;
    public FontAwesomeIconView closeApp;
    public TextField host;
    public Button buttonConnect;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void connect(MouseEvent event) {
        try {
            loadChat(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {

    }

    private void loadChat(Socket socket) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chat.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) buttonConnect.getScene().getWindow();
        stage.setOnCloseRequest(e -> App.closeApp());
        stage.setScene(scene);
        stage.show();

        Client.run(loader.getController(), socket);
    }
}
