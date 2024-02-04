package controller;

import dao.AppointmentsQuery;
import dao.CustomersQuery;
import helper.Navigation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

    public void onSelectCustomer() throws SQLException {
        Customer selection = (Customer) customersTable.getSelectionModel().getSelectedItem();

        if (selection != null) {
            appointmentsTable.setItems(AppointmentsQuery.getCustomerAppointments(selection.getCustomer_ID()));
            errorLabel.setText("");
        }
        else errorLabel.setText("No Customer Selected!");
    }
    public void onAllAppointmentsSelected() throws SQLException {
        appointmentsTable.setItems(AppointmentsQuery.getAllAppointments());
        errorLabel.setText("");
    }
    public void onMonthAppointmentsSelected() throws SQLException {
        appointmentsTable.setItems(AppointmentsQuery.getMonthAppointments());
        errorLabel.setText("");
    }
    public void onWeekAppointmentsSelected() throws SQLException {
        appointmentsTable.setItems(AppointmentsQuery.getWeekAppointments());
        errorLabel.setText("");
    }
    public void onSearchCustomer() throws SQLException {
        String searchText = searchCustomerText.getText();
        customersTable.setItems(CustomersQuery.getCustomers(searchText));
    }
    public void onAddCustomer(ActionEvent e) throws IOException {
        Navigation.switchToAddCustomer(e);
    }
    public void onAddAppointment(ActionEvent e) throws IOException {
        Navigation.switchToAddAppointment(e);
    }
    public void onUpdateAppointment(ActionEvent e) throws IOException {
        selectedAppointment = (Appointment)appointmentsTable.getSelectionModel().getSelectedItem();
        if(selectedAppointment == null){
            errorLabel.setText("No Appointment Selected!");
        }
        else {
            Navigation.switchToUpdateAppointment(e);
        }

    }
    public void onUpdateCustomer(ActionEvent e) throws IOException {
        selectedCustomer = (Customer)customersTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null){
            errorLabel.setText("No Customer Selected!");
        }
        else {
            Navigation.switchToUpdateCustomer(e);
        }
    }
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
        if(AppointmentsQuery.getCustomerAppointments(selectedID).isEmpty()){
            String name = selected.getCustomer_Name();
            CustomersQuery.delete(selectedID);
            customersTable.setItems(CustomersQuery.getCustomers());
            appointmentsTable.setItems(AppointmentsQuery.getAllAppointments());
            errorLabel.setText("Customer: " + name + " has been deleted!");
        }
        else{
            errorLabel.setText("Cannot Delete a Customer with Appointments!");
        }
    }
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
        AppointmentsQuery.delete(selectedID);
        appointmentsTable.setItems(AppointmentsQuery.getAllAppointments());
        errorLabel.setText("Appointment: " + title + " has been deleted!");
    }
    public void onLogOut(ActionEvent e) throws IOException {
        Navigation.logOut(e);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        welcomeLabel.setText("Welcome, " + LoginController.username + "!");
        //CHECK FOR URGENT APPOINTMENTS
        try {
            ObservableList<Appointment> urgentAppointments = AppointmentsQuery.getUrgentAppointments();
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customersIDColumn.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        customersNameColumn.setCellValueFactory(new PropertyValueFactory<>("customer_Name"));
        customersAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customersZipCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postal_Code"));
        customersDivisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("division_ID"));
        customersPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        try {
            appointmentsTable.setItems(AppointmentsQuery.getAllAppointments());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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