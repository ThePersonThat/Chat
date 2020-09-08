package org.example.chat.client.graphics.controllers;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.chat.client.graphics.app.App;

public class ControllerConnect extends AbstractController {
    public ResourceBundle resources;

    public URL location;

    public Button buttonConnect;

    public TextField host;

    public TextField port;

    private void loadLoginForm(Socket socket) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = loader.load();
        ControllerLoginUp controller = loader.getController();
        controller.setSocket(socket);
        Scene scene = new Scene(root);
        Stage stage = (Stage) buttonConnect.getScene().getWindow();
        stage.setOnCloseRequest(e -> App.closeApp());
        stage.setScene(scene);
        stage.show();
    }

    public void connect(MouseEvent event) {
        String ht = host.getText();
        String pt = port.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error!");

        if(ht.isEmpty() && pt.isEmpty()) {
            alert.setHeaderText(null);
            alert.setContentText("Not all the fields are completed");

            alert.showAndWait();

            return;
        }

        try {
            int port = Integer.parseInt(pt);
            Socket socket = new Socket(ht, port);
            loadLoginForm(socket);
        } catch (NumberFormatException ex) {
            alert.setHeaderText(null);
            alert.setContentText("Port only can have numbers");

            alert.showAndWait();
        } catch (UnknownHostException ex) {
            alert.setHeaderText(null);
            alert.setContentText("Such a server not found!");

            alert.showAndWait();
        } catch (IOException ex) {
            alert.setHeaderText(null);
            alert.setContentText("Error during connection: " + ex.getMessage());
            alert.showAndWait();
        } catch (Exception ex) {
            alert.setHeaderText(null);
            alert.setContentText("Something went wrong! Error of creation form.");
        }
    }



    public void initialize() {

    }
}
