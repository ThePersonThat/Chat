module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    opens org.example.chat.client.graphics.controllers to javafx.fxml;


    requires de.jensd.fx.fontawesomefx.fontawesome;
    requires com.gluonhq.charm.glisten;
    requires java.sql;

    exports org.example.chat.client.graphics.app;
}



