package controller;

import dao.DBConnection;
import dao.UsersQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static String username;

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label errorLabel;
    @FXML
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
            root = FXMLLoader.load(getClass().getResource("/model/MainMenu.fxml"));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Main Menu");
            stage.show();
        }
        else{
            errorLabel.setText("Incorrect Password");
        }
        passwordTextField.setText("");
    }
    @FXML
    public void onExit(ActionEvent e) {
        Stage stage = stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
}