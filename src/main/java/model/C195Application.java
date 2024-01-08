package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The Main Application Class
 * @author Christopher Coluccio
 */
public class C195Application extends Application {
    ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
    /**
     * The Start method is called to initialize the application.
     * @param stage the initial stage to be shown (Login screen)
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(C195Application.class.getResource("/Login.fxml"));
        fxmlLoader.setResources(rb);
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.setScene(scene);
        stage.setTitle(rb.getString("Log In"));
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}