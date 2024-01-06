package controller;

import dao.DBConnection;
import dao.UsersQuery;
import helper.FileIO;
import helper.Navigation;
import helper.TimeZoneHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public static String username;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Label zoneLabel;
    public void onLogIn(ActionEvent e) throws SQLException, IOException {
        username = usernameTextField.getText();
        String password = UsersQuery.select(username);
        if(password == null){
            errorLabel.setText("Username not Found!");
            FileIO.addLoginAttempt(username,false);
        }
        else if(password.equals(passwordTextField.getText())){
            //Log in under username
            //Load Main Menu Scene
            FileIO.addLoginAttempt(username,true);
            Navigation.switchToMainMenu(e);
        }
        else{
            errorLabel.setText("Incorrect Password");
            FileIO.addLoginAttempt(username,false);
        }
        passwordTextField.setText("");
    }
    @FXML
    public void onExit(ActionEvent e) {
        Navigation.exit(e);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zoneLabel.setText("Region: " + TimeZoneHelper.systemZone.toString());
    }
}