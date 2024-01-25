package controller;

import dao.AppointmentsQuery;
import dao.CustomersQuery;
import dao.UsersQuery;
import helper.Navigation;
import helper.TimeZoneHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
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
    @FXML
    public DatePicker datePicker;
    @FXML
    public ComboBox<Integer> startHourCombo;
    @FXML
    public ComboBox<String> startMinuteCombo;
    @FXML
    public ComboBox<String> durationCombo;

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
        int contact_ID;

        if(customerCombo.getSelectionModel().getSelectedItem() != null){
            customerID = customerCombo.getSelectionModel().getSelectedItem().getCustomer_ID();
        }
        else{
            errorLabel.setText("Please select a Customer!");
            return;
        }
        if(userCombo.getSelectionModel().getSelectedItem() != null){
            userID = userCombo.getSelectionModel().getSelectedItem().getUser_ID();
        }
        else{
            errorLabel.setText("Please select a User!");
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
        TimeZoneHelper.hours.clear();
        TimeZoneHelper.minutes.clear();
        TimeZoneHelper.durations.clear();
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
        //Working With Business Hours
        //UTC First Open Hour is 1:00 pm, we will use Military for simplification, so 13:00
        System.out.println("Offset: " + TimeZoneHelper.getOffsetHours());
        //Sets Local Hour for Start Time
        int offsetStartHour = 13+TimeZoneHelper.getOffsetHours();
        //The Business is open for 14 hrs total. This for loop will iterate 13 times, since one cannot start
        //an appointment on/after closing time. This gives us 13 hour markers to start on, localized to system
        for(int i=0; i<12;i++){
            int adjustedHour = offsetStartHour+i;
            if(adjustedHour<0)adjustedHour+=24;
            else if(adjustedHour>24)adjustedHour-=24;
            TimeZoneHelper.hours.add(adjustedHour);
        }
        TimeZoneHelper.minutes.addAll("00","15","30","45");
        TimeZoneHelper.durations.addAll("0:15","0:30","0:45","1:00","1:30","2:00","2:30","3:00","3:30","4:00");
        startHourCombo.setItems(TimeZoneHelper.hours.sorted());
        durationCombo.setItems(TimeZoneHelper.durations);
        startMinuteCombo.setItems(TimeZoneHelper.minutes);
    }
}