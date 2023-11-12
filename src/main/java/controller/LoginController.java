package controller;

import dao.DBConnection;
import dao.UsersQuery;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    public static String username;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label errorLabel;
    public void onLogIn(ActionEvent e) throws SQLException, IOException {
        DBConnection.openConnection();
        String password = UsersQuery.select(usernameTextField.getText());
        DBConnection.closeConnection();
        if(password == null){
            errorLabel.setText("Username not Found!");
        }
        else if(password.equals(passwordTextField.getText())){
            //Log in under username
            //Load Main Menu Scene
            username = usernameTextField.getText();
            Navigation.switchToMainMenu(e);
        }
        else{
            errorLabel.setText("Incorrect Password");
        }
        passwordTextField.setText("");
    }
    @FXML
    public void onExit(ActionEvent e) {
        Navigation.exit(e);
    }
}