package controller;

import dao.AppointmentsQuery;
import dao.ContactsQuery;
import dao.CustomersQuery;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
/**
 * Controller Class for displaying aggregated Customer / Appointment totals
 * This class has Reporting for appointment totals by Type and Month.
 * Also has Reporting for customer totals by State/Region
 */
public class ReportingController implements Initializable {

    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private Label tableLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private TableView reportTable;
    @FXML
    private TableColumn groupByColumn;
    @FXML
    private TableColumn totalColumn;
    public static int selectedContactID;
    /**
     * Switches to the MainMenu
     * @param e ActionEvent for MainMenu button
     * @throws IOException throws IOException
     */
    public void onSwitchToMainMenu(ActionEvent e) throws IOException {
        Navigation.switchToMainMenu(e);
    }
    /**
     * Switches to the Contact Schedule Scene
     * @param e ActionEvent for View button
     * @throws IOException throws IOException
     */
    public void onSwitchToContactSchedule(ActionEvent e) throws IOException {
        if(contactCombo.getSelectionModel().getSelectedItem() != null) {
            selectedContactID = contactCombo.getSelectionModel().getSelectedItem().getContact_ID();
            Navigation.switchToContactSchedule(e);
        }
        else errorLabel.setText("Please select a Contact");
    }
    /**
     * Pulls a report for Appointment totals by Type and populates the Table
     */
    public void onByType(){
        try {
            reportTable.setItems(AppointmentsQuery.getAppointmentTotals("Type"));
            tableLabel.setText("Appointments");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        groupByColumn.setText("Type");
        groupByColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
    /**
     * Pulls a report for Appointment totals by Month and populates the Table
     */
    public void onByMonth(){
        try {
            reportTable.setItems(AppointmentsQuery.getAppointmentTotals("Month"));
            tableLabel.setText("Appointments");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        groupByColumn.setText("Month");
        groupByColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
    /**
     * Pulls a report for First Level Division totals by Month and populates the Table
     */
    public void onByFLD(){
        try {
            reportTable.setItems(CustomersQuery.getCustomersByFLD());
            tableLabel.setText("Customers");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        groupByColumn.setText("State/Region");
        groupByColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
    /**
     * Switches to the Contact Schedule Scene
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactCombo.setItems(ContactsQuery.getContacts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        onByType();
    }
}
