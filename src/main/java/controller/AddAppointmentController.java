package controller;

import dao.AppointmentsQuery;
import dao.CustomersQuery;
import dao.UsersQuery;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The Add Part Scene Controller provides functionality for creating an instance of
 * an In House or Outsourced Part that will be added to the Inventory Part List
 * Also contains a "Cancel" to MainMenu option
 */
public class AddAppointmentController implements Initializable {
    @FXML
    public Label errorLabel;
    @FXML
    public TextField appointmentIDText;
    @FXML
    public TextField titleText;
    @FXML
    public TextField descriptionText;
    @FXML
    public TextField locationText;
    @FXML
    public TextField typeText;
    @FXML
    public ComboBox<Customer> customerCombo;
    @FXML
    public ComboBox<User> userCombo;

    /**
     * Cancels Part submission, Returns to Main Menu
     * @param e ActionEvent for Back Button
     * @throws IOException
     */
    public void onCancel(ActionEvent e) throws IOException {
        Navigation.switchToMainMenu(e);
    }
    /**
     * Adds Part using the values from all text fields to a new In House or Outsourced object.
     * Performs input validation on all inputs upon submission. Cancels and populates ErrorLable if invalid
     * Returns to the main menu upon successful submission
     * @param e ActionEvent for the Save button
     * @throws IOException
     */
    public void onSave(ActionEvent e) throws IOException, SQLException {
        int id = AppointmentsQuery.getNextAppointmentID();
        String title = titleText.getText();
        if(title.isBlank()) {errorLabel.setText("Title cannot be blank!"); return;}
        String description = descriptionText.getText();
        if(description.isBlank()){ errorLabel.setText("Description cannot be blank!"); return;}
        String location = locationText.getText();
        if(location.isBlank()){ errorLabel.setText("Location cannot be blank!"); return;}
        String type = typeText.getText();
        if(type.isBlank()) errorLabel.setText("Type cannot be blank!");
        int customerID;
        int userID;
        if(customerCombo.getSelectionModel().getSelectedItem() != null){
            customerID = customerCombo.getSelectionModel().getSelectedItem().getCustomer_ID();
        }
        else{
            errorLabel.setText("Please select a Customer");
            return;
        }
        if(userCombo.getSelectionModel().getSelectedItem() != null){
            userID = userCombo.getSelectionModel().getSelectedItem().getUser_ID();
        }
        else{
            errorLabel.setText("Please select a Country and First Level Division!");
            return;
        }
        CustomersQuery.insert(id,
                title,
                description,
                location,
                type,
                customerID);
        Navigation.switchToMainMenu(e);
    }
    /**
     * Actions to take before showing the Scene.
     * Sets the ID value to display. The rest of the info is User Input
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentIDText.textProperty().set(AppointmentsQuery.getNextAppointmentID()+"");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            customerCombo.setItems(CustomersQuery.getCustomers());
            userCombo.setItems(UsersQuery.getUsers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}