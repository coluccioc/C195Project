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

/**
 * Navigation class used to separate navigation concerns into one place
 */
public class Navigation {
    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    /**
     * Is not instantiated
     */
    private Navigation(){}

    /**
     * Loads MainMenu Scene
     * @param e ActionEvent
     * @throws IOException
     */
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
    /**
     * Loads AddCustomer Scene
     * @param e ActionEvent
     * @throws IOException
     */
    public static void switchToAddCustomer(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Navigation.class.getResource("/AddCustomer.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,500,400);
        stage.setScene(scene);
        stage.setTitle("Add Customer");
        stage.show();
    }
    /**
     * Loads AddAppointment Scene
     * @param e ActionEvent
     * @throws IOException
     */
    public static void switchToAddAppointment(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Navigation.class.getResource("/AddAppointment.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,900,400);
        stage.setScene(scene);
        stage.setTitle("Add Appointment");
        stage.show();
    }
    /**
     * Loads UpdateCustomer Scene
     * @param e ActionEvent
     * @throws IOException
     */
    public static void switchToUpdateCustomer(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Navigation.class.getResource("/UpdateCustomer.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,500,400);
        stage.setScene(scene);
        stage.setTitle("Update Customer");
        stage.show();
    }
    /**
     * Loads UpdateAppointment Scene
     * @param e
     * @throws IOException
     */
    public static void switchToUpdateAppointment(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Navigation.class.getResource("/UpdateAppointment.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,900,400);
        stage.setScene(scene);
        stage.setTitle("Update Appointment");
        stage.show();
    }
    /**
     * Loads Reporting Scene
     * @param e ActionEvent
     * @throws IOException
     */
    public static void switchToReporting(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Navigation.class.getResource("/Reporting.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,600,400);
        stage.setScene(scene);
        stage.setTitle("Reporting");
        stage.show();
    }
    /**
     * Loads ContactSchedule Scene
     * @param e ActionEvent
     * @throws IOException
     */
    public static void switchToContactSchedule(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Navigation.class.getResource("/ContactSchedule.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root,750,400);
        stage.setScene(scene);
        stage.setTitle("Contact Schedule");
        stage.show();
    }
    /**
     * Loads LogIn Scene
     * @param e ActionEvent
     * @throws IOException
     */
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

    /**
     * Closes Application
     * @param e
     */
    public static void exit(ActionEvent e){
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
}