package model;

import helper.CountryHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Main Application Class
 * @author Christopher Coluccio
 */
public class C195Application extends Application {
    /**
     * The Start method is called to initialize the application.
     * @param stage the initial stage to be shown (Login screen)
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(C195Application.class.getResource("/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

        CountryHelper.listOfCountries.add(CountryHelper.usa);
        CountryHelper.listOfCountries.add(CountryHelper.canada);
    }

    public static void main(String[] args) {
        launch();
    }
}