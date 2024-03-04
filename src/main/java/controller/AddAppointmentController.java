package controller;

import dao.AppointmentsQuery;
import dao.ContactsQuery;
import dao.CustomersQuery;
import dao.UsersQuery;
import helper.Navigation;
import helper.TimeZoneHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * The Add Appointment Scene Controller provides functionality for creating a New Appointment Record
 * that will be stored in the database upon submission
 * Also contains a "Cancel" to MainMenu option
 * Checks for invalid input in the input fields and for overlapping appointments before submission
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
    public ComboBox<Contact> contactCombo;
    @FXML
    public DatePicker datePicker;
    @FXML
    public ComboBox<Integer> startHourCombo;
    @FXML
    public ComboBox<String> startMinuteCombo;
    @FXML
    public ComboBox<String> durationCombo;
    int offsetStartHour;
    /**
     * Cancels Appointment submission, Returns to Main Menu
     * @param e ActionEvent for Back Button
     * @throws IOException
     */
    public void onCancel(ActionEvent e) throws IOException {
        Navigation.switchToMainMenu(e);
    }
    /**
     * Adds Appointment using the values from all text fields
     * Performs input validation on all inputs upon submission. Cancels and populates errorLabel if invalid
     * Returns to the main menu upon successful submission
     * Adjusted upon revision to exclude Timezone conversion as this happens upon submission
     * @param e ActionEvent for the Save button
     * @throws IOException
     */
    public void onSave(ActionEvent e) throws IOException, SQLException {
        LocalTime openTime = LocalTime.of(offsetStartHour,0);
        int id = AppointmentsQuery.getNextAppointmentID();
        String title = titleText.getText();
        if(title.isBlank()) {errorLabel.setText("Title cannot be blank!"); return;}
        if(title.length()>50) {errorLabel.setText("Title cannot more than 50 characters!"); return;}
        String description = descriptionText.getText();
        if(description.isBlank()){ errorLabel.setText("Description cannot be blank!"); return;}
        if(description.length()>50) {errorLabel.setText("Description cannot more than 50 characters!"); return;}
        String location = locationText.getText();
        if(location.isBlank()){ errorLabel.setText("Location cannot be blank!"); return;}
        if(location.length()>50) {errorLabel.setText("Location cannot more than 50 characters!"); return;}
        String type = typeText.getText();
        if(type.isBlank()){ errorLabel.setText("Type cannot be blank!");return;}
        if(type.length()>50) {errorLabel.setText("Type cannot more than 50 characters!"); return;}
        LocalDate selectedDate = datePicker.getValue();
        int customerID;
        int userID;
        int contactID;

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
        if(contactCombo.getSelectionModel().getSelectedItem() != null){
            contactID = contactCombo.getSelectionModel().getSelectedItem().getContact_ID();
        }
        else{
            errorLabel.setText("Please select a Contact!");
            return;
        }
        if(selectedDate == null || selectedDate.isBefore(LocalDate.now())){
            errorLabel.setText("Please select a valid Date");
            return;
        }
        int selectedHour;
        int selectedMinute;
        if(startHourCombo.getValue() != null && startMinuteCombo.getValue() != null){
            selectedHour = startHourCombo.getValue();
            selectedMinute = Integer.parseInt(startMinuteCombo.getValue());
        }
        else {
            errorLabel.setText("Start Time cannot be blank!");
            return;
        }
        String duration;
        LocalTime startTime = LocalTime.of(selectedHour,selectedMinute);
        LocalDateTime startDateTime = LocalDateTime.of(selectedDate,startTime);
        Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
        //Timestamp startTimestamp = TimeZoneHelper.convertToUTCTimestamp(startDateTime);
        if(startHourCombo.getValue() != null)duration = durationCombo.getValue();
        else {
            errorLabel.setText("Duration cannot be blank!");
            return;
        }
        if(durationCombo.getValue() != null)duration = durationCombo.getValue();
        else {
            errorLabel.setText("Duration cannot be blank!");
            return;
        }
        //Complex TIME SELECTED VALIDATION
        int durationHours = Integer.parseInt(duration.charAt(0)+"");
        int durationMinutes = Integer.parseInt(duration.substring(duration.length()-2));
        //create LocalTime to compare to openTime
        LocalTime endTime = startTime.plusHours(durationHours).plusMinutes(durationMinutes);
        //Think about the case where close time (open+14) wraps around, but end time is after.
        //CASE WHERE CLOSE DOES NOT WRAP
        if(endTime.isAfter(openTime)&&openTime.plusHours(14).isAfter(LocalTime.of(13,0))) {
            if (endTime.isAfter(openTime.plusHours(14))){
                errorLabel.setText("Appointment extends past the office's close!");
                return;
            }
            if (startTime.isBefore(openTime) || startTime.isAfter(openTime.plusHours(14))){
                errorLabel.setText("Appointment starts before the office opens!");
                return;
            }
        }
        else if(endTime.isBefore(openTime)){
            //Catches case where Close time wraps around to next day and invalid
            if(endTime.isAfter(openTime.minusHours(10))&&openTime.isAfter(LocalTime.of(9,0))) {
                errorLabel.setText("Appointment extends past the office's close!");
                return;
            }
            //Catches Case where Close Time does not wrap around , endTime is before openTime
            else if(openTime.isBefore(LocalTime.of(10,0))){
                errorLabel.setText("Appointment extends past the office's close!");
                return;
            }
        }
        else if(openTime.isAfter(LocalTime.of(10,0))){
            if(startTime.isAfter(openTime.plusHours(14))&&startTime.isBefore(openTime)){
                errorLabel.setText("Appointment starts before the office opens!");
                return;
            }
        }
        LocalDateTime endDateTime = startDateTime.plusHours(durationHours).plusMinutes(durationMinutes);
        //Timestamp endTimestamp = TimeZoneHelper.convertToUTCTimestamp(endDateTime);
        Timestamp endTimestamp = Timestamp.valueOf(endDateTime);

                //CHECK FOR OVERLAPPING APPTS FOR GIVEN CUSTOMER
        //SEE IF START TIME IS BETWEEN ANY EXISTING START/END TIMES
        ObservableList<Appointment> customerAppointments =
                AppointmentsQuery.customerFilter.extract(AppointmentsQuery.getAllAppointments(),customerID);
        for(int i = 0; i < customerAppointments.size();i++){
            //SEE IF START TIME IS BETWEEN ANY EXISTING START/END TIMES
            //IF START OR END IS BETWEEN ANOTHER APPOINTMENT'S START OR END, WE OVERLAPPED
            Appointment indexAppointment = customerAppointments.get(i);
            LocalDateTime indexStart = indexAppointment.getStart().toLocalDateTime();
            LocalDateTime indexEnd = indexAppointment.getEnd().toLocalDateTime();
            if((startDateTime.compareTo(indexStart)>=0&&startDateTime.compareTo(indexEnd)<=0)
            || (endDateTime.compareTo(indexStart)>=0&&endDateTime.compareTo(indexEnd)<=0)){
                errorLabel.setText("Appointment cannot overlap an existing Appointment for this Customer!");
                return;
            }
        }
        AppointmentsQuery.insert(id,
                title,
                description,
                location,
                type,
                startTimestamp,
                endTimestamp,
                customerID,
                userID,
                contactID
                );
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
            contactCombo.setItems(ContactsQuery.getContacts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //Working With Business Hours
        //UTC First Open Hour is 1:00 pm, we will use Military for simplification, so 13:00
        //System.out.println("Offset: " + TimeZoneHelper.getOffsetHours());
        //Sets Local Hour for Start Time
        offsetStartHour = 13+TimeZoneHelper.getOffsetHours();
        //The Business is open for 14 hrs total. This for loop will iterate 13 times, since one cannot start
        //an appointment on/after closing time. This gives us 13 hour markers to start on, localized to system
        for(int i=0; i<14;i++){
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