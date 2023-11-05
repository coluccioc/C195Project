package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void onLogIn(ActionEvent e) {

    }

    public void onExit(ActionEvent e) {
        Stage stage = stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
}