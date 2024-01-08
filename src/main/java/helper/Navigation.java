package helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Navigation {
    private static Stage stage;
    private static Scene scene;
    private static Parent root;
    ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());

    private Navigation(){}
    public static void switchToMainMenu(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Navigation.class.getResource("/MainMenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setX(200);
        stage.setY(200);
        scene = new Scene(root,1100,600);
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }
    public static void switchToAddCustomer(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Navigation.class.getResource("/AddCustomer.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,1100,400);
        stage.setScene(scene);
        stage.setTitle("Add Customer");
        stage.show();
    }
    public static void logOut(ActionEvent e) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader(Navigation.class.getResource("/Login.fxml"));
        fxmlLoader.setResources(rb);
        root = fxmlLoader.load();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,700,400);
        stage.setScene(scene);
        stage.setTitle(rb.getString("Log In"));
        stage.show();
    }
    public static void exit(ActionEvent e){
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
}
