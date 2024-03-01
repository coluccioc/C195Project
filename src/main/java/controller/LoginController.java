package controller;

import dao.UsersQuery;
import helper.FileIO;
import helper.Navigation;
import helper.TimeZoneHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * LoginController Class is used to manage input on the Application's login screen
 * This class validates username/password input and shows errors if submissions are incorrect
 * Also is built with a ResourceBundle to be able to translate the text on screen to French
 * based on the user's computer's languate settings
 */
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
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Button logInButton;
    @FXML
    private Button exitButton;
    ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());

    /**
     * LogIn Method checks to see if the username is valid and the password is correct
     * Provides informative errors if not
     * @param e ActionEvent used for navigation
     * @throws SQLException
     * @throws IOException
     */
    public void onLogIn(ActionEvent e) throws SQLException, IOException {
        username = usernameTextField.getText();
        String password = UsersQuery.select(username);
        if(password == null){
            errorLabel.setText(rb.getString("Username not Found!"));
            FileIO.addLoginAttempt(username,false);
        }
        else if(password.equals(passwordTextField.getText())){
            //Log in under username
            //Load Main Menu Scene
            FileIO.addLoginAttempt(username,true);
            Navigation.switchToMainMenu(e);
        }
        else{
            errorLabel.setText(rb.getString("Incorrect Password"));
            FileIO.addLoginAttempt(username,false);
        }
        passwordTextField.setText("");
    }

    /**
     * Closes Application
     * @param e
     */
    @FXML
    public void onExit(ActionEvent e) {
        Navigation.exit(e);
    }

    /**
     * Initialize method to set the text based on user's language before scene loads
     * @param url
     * @param rb Resource Bundle defined by default locale
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        zoneLabel.setText(rb.getString("Region") + ": " + TimeZoneHelper.systemZone.toString());
        usernameLabel.setText(rb.getString("Username"));
        passwordLabel.setText(rb.getString("Password"));
        logInButton.setText(rb.getString("Log In"));
        exitButton.setText(rb.getString("Exit"));
        System.out.println(System.getProperty("javafx.runtime.version"));
    }
}