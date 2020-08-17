package org.example.chat.client.graphics;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.chat.client.Client;

public class ControllerConnect {

    private double x, y;

    public ResourceBundle resources;

    public URL location;

    public Button buttonConnect;

    public TextField host;

    public TextField port;


    public void CloseApp(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }


    public void MinStage(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setIconified(true);
    }

    private void loadChat(Socket socket) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chat.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) buttonConnect.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        Client.run(loader.getController(), socket);
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
            loadChat(socket);
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
            alert.setContentText("Error during connection!");

            alert.showAndWait();
        } catch (Exception ex) {
            alert.setHeaderText(null);
            alert.setContentText("Something went wrong! Error of creation form.");
        }
    }

    public void dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }


    public void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }


    public void initialize() {

    }
}
