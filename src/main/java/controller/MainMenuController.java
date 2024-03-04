package controller;

import dao.AppointmentsQuery;
import dao.CustomersQuery;
import helper.Navigation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The Main Menu Controller
 * This is the application's main dashboard.
 * Displays tables for the list of Customers and the list of Appointmnets
 * Many Navigation options on this page to access Add/Update/Delete functionality
 */
public class MainMenuController implements Initializable{
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Label urgentAppointmentLabel;
    @FXML
    private TextField searchCustomerText;
    @FXML
    private TableView customersTable;
    @FXML
    private TableColumn customersIDColumn;
    @FXML
    private TableColumn customersNameColumn;
    @FXML
    private TableColumn customersAddressColumn;
    @FXML
    private TableColumn customersZipCodeColumn;
    @FXML
    private TableColumn customersDivisionIDColumn;
    @FXML
    private TableColumn customersPhoneColumn;
    @FXML
    private TableView appointmentsTable;
    @FXML
    private TableColumn appointmentsIDColumn;
    @FXML
    private TableColumn appointmentsTitleColumn;
    @FXML
    private TableColumn appointmentsDescriptionColumn;
    @FXML
    private TableColumn appointmentsLocationColumn;
    @FXML
    private TableColumn appointmentsTypeColumn;
    @FXML
    private TableColumn appointmentsStartColumn;
    @FXML
    private TableColumn appointmentsEndColumn;
    @FXML
    private TableColumn appointmentsContactColumn;
    @FXML
    private TableColumn appointmentsCustomerIDColumn;
    @FXML
    private TableColumn appointmentsUserIDColumn;
    public static Customer selectedCustomer;
    public static Appointment selectedAppointment;
    /**
     * Method that Filters the Appointments table to match the selected customer
     * on SelectCustomer button click
     * Incorporates lambda expression defined in the Query class to filter appointments
     * @throws SQLException
     */
    public void onSelectCustomer() throws SQLException {
        Customer selection = (Customer) customersTable.getSelectionModel().getSelectedItem();

        if (selection != null) {
            appointmentsTable.setItems
                    (AppointmentsQuery.customerFilter.extract(AppointmentsQuery.getAllAppointments(),
                            selection.getCustomer_ID()));
            errorLabel.setText("");
        }
        else errorLabel.setText("No Customer Selected!");
    }

    /**
     * Radio Button selection that will show All appointmnets
     * @throws SQLException
     */
    public void onAllAppointmentsSelected() throws SQLException {
        appointmentsTable.setItems(AppointmentsQuery.getAllAppointments());
        errorLabel.setText("");
    }
    /**
     * Radio Button selection that shows all appointmnets in the current month
     * @throws SQLException
     */
    public void onMonthAppointmentsSelected() throws SQLException {
        appointmentsTable.setItems(AppointmentsQuery.getMonthAppointments());
        errorLabel.setText("");
    }
    /**
     * Radio button selection that shows all appoinments in the current week (Sunday -> Saturday)
     * @throws SQLException
     */
    public void onWeekAppointmentsSelected() throws SQLException {
        appointmentsTable.setItems(AppointmentsQuery.getWeekAppointments());
        errorLabel.setText("");
    }
    /**
     * Search functionality for the customer table to fund based on ID or Substring
     * @throws SQLException
     */
    public void onSearchCustomer() throws SQLException {
        String searchText = searchCustomerText.getText();
        customersTable.setItems(CustomersQuery.getCustomers(searchText));
    }
    /**
     * Switches to the AddCustomer scene
     * @param e
     * @throws IOException
     */
    public void onAddCustomer(ActionEvent e) throws IOException {
        Navigation.switchToAddCustomer(e);
    }
    /**
     * Switches to Add Appointment scene
     * @param e
     * @throws IOException
     */
    public void onAddAppointment(ActionEvent e) throws IOException {
        Navigation.switchToAddAppointment(e);
    }
    /**
     * Switches to UpdateAppointment Scene
     * also sets static variable selectedAppointment that can be accessed from new scene
     * @param e
     * @throws IOException
     */
    public void onUpdateAppointment(ActionEvent e) throws IOException {
        selectedAppointment = (Appointment)appointmentsTable.getSelectionModel().getSelectedItem();
        if(selectedAppointment == null){
            errorLabel.setText("No Appointment Selected!");
        }
        else {
            Navigation.switchToUpdateAppointment(e);
        }

    }
    /**
     * Switches to UpdateCustomer scene
     * Also sets static variable selectedCusomer that can be accessed from new scene
     * @param e
     * @throws IOException
     */
    public void onUpdateCustomer(ActionEvent e) throws IOException {
        selectedCustomer = (Customer)customersTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null){
            errorLabel.setText("No Customer Selected!");
        }
        else {
            Navigation.switchToUpdateCustomer(e);
        }
    }
    /**
     * if cusomter is selected and has no appointments, Pop up alert double checks
     * If user accepts, customer is deleted. Informative errors for all cases
     * @throws SQLException
     */
    public void onDeleteCustomer() throws SQLException {
        int selectedID;
        Customer selected;
        if(customersTable.getSelectionModel().getSelectedItem() != null){
            selected = (Customer) customersTable.getSelectionModel().getSelectedItem();
            selectedID = selected.getCustomer_ID();
        }
        else {
            errorLabel.setText("No Customer Selected!");
            return;
        }
        if(AppointmentsQuery.customerFilter.extract(AppointmentsQuery.getAllAppointments(),selectedID).isEmpty()){
            String name = selected.getCustomer_Name();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to Delete: " + name + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                CustomersQuery.delete(selectedID);
                customersTable.setItems(CustomersQuery.getCustomers());
                appointmentsTable.setItems(AppointmentsQuery.getAllAppointments());
                errorLabel.setText("Customer: " + name + " has been deleted!");
            }
            else errorLabel.setText("");
        }
        else{
            errorLabel.setText("Cannot Delete a Customer with Appointments!");
        }
    }
    /**
     * if appointment is selected, Pop up alert double checks
     * If user accepts, appointment is deleted. Informative errors for all cases
     * @throws SQLException
     */
    public void onDeleteAppointment() throws SQLException {
        int selectedID;
        Appointment selected;
        if(appointmentsTable.getSelectionModel().getSelectedItem() != null){
            selected = (Appointment) appointmentsTable.getSelectionModel().getSelectedItem();
            selectedID = selected.getAppointment_ID();
        }
        else {
            errorLabel.setText("No Appointment Selected!");
            return;
        }
        String title = selected.getTitle();
        String type = selected.getType();
        int ID = selected.getAppointment_ID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to Delete: " + title + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AppointmentsQuery.delete(selectedID);
            appointmentsTable.setItems(AppointmentsQuery.getAllAppointments());
            errorLabel.setText("Appointment: " + title + " of type: "+ type + ", ID: "+ID+" has been deleted!");
        }
        else errorLabel.setText("");
    }

    /**
     * Switches to Reporting Scene
     * @param e
     * @throws IOException
     */
    public void onReporting(ActionEvent e) throws IOException {
        Navigation.switchToReporting(e);
    }

    /**
     * Takes user back to login screen
     * @param e
     * @throws IOException
     */
    public void onLogOut(ActionEvent e) throws IOException {
        Navigation.logOut(e);
    }

    /**
     * Initialize method to populate tables, check for urgent appointment
     * Lambda expression defined in DAO class is used to filter appointments based on start time
     * in order to check for urgent appointmnets
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        welcomeLabel.setText("Welcome, " + LoginController.username + "!");
        //CHECK FOR URGENT APPOINTMENTS
        try {
            ObservableList<Appointment> urgentAppointments =
                    AppointmentsQuery.urgentFilter.extract(AppointmentsQuery.getAllAppointments(),15);
            if(urgentAppointments.isEmpty()){
                urgentAppointmentLabel.setText("No Urgent Appointments!");
                System.out.println("No urgent appt detected");
            }
            else{
                Appointment mostUrgent = urgentAppointments.get(0);
                urgentAppointmentLabel.setText("Appointment ID: "+mostUrgent.getAppointment_ID()+" Starting on: "+
                        mostUrgent.getStart().toLocalDateTime().toLocalDate() + " at: "+
                        mostUrgent.getStart().toLocalDateTime().toLocalTime());
                System.out.println("Urgent appt detected");
            }
        }catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        //Update Customer List upon Main Menu Load
        try {
            customersTable.setItems(CustomersQuery.getCustomers());
            appointmentsTable.setItems(AppointmentsQuery.getAllAppointments());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customersIDColumn.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        customersNameColumn.setCellValueFactory(new PropertyValueFactory<>("customer_Name"));
        customersAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customersZipCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postal_Code"));
        customersDivisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("division_ID"));
        customersPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        appointmentsIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        appointmentsTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentsDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentsLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentsTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentsStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentsEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentsContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        appointmentsCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        appointmentsUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
    }
}