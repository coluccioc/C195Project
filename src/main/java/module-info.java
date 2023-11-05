module model.c195project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens model to javafx.fxml;
    exports model;
    exports controller;
    opens controller to javafx.fxml;
}