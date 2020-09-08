package org.example.chat.client.graphics.app;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static HostServices services;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loaderConnect = new FXMLLoader(getClass().getResource("/connect.fxml"));

        Parent root = loaderConnect.load();

        setServices(getHostServices());
        primaryStage.setOnCloseRequest(e -> closeApp());
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();

    }

    public static void setServices(HostServices hostServices) {
        services = hostServices;
    }

    public static HostServices getServices() {
        return services;
    }

    public static void closeApp() {
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}