package controller;

import dao.AppointmentsQuery;
import dao.CustomersQuery;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    public void onSelectCustomer() throws SQLException {
        Customer selection = (Customer) customersTable.getSelectionModel().getSelectedItem();

        if (selection != null) {
            appointmentsTable.setItems(AppointmentsQuery.getCustomerAppointments(selection.getCustomer_ID()));
            errorLabel.setText("");
        }
        else errorLabel.setText("No Customer Selected!");
    }
    public void onSearchCustomer() throws SQLException {
        String searchText = searchCustomerText.getText();
        customersTable.setItems(CustomersQuery.getCustomers(searchText));
    }
    public void onAddCustomer(ActionEvent e) throws IOException {
        Navigation.switchToAddCustomer(e);
    }
    public void onDeleteCustomer(ActionEvent e){

    }
    public void onLogOut(ActionEvent e) throws IOException {
        Navigation.logOut(e);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        welcomeLabel.setText("Welcome, " + LoginController.username + "!");
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
            appointmentsTable.setItems(AppointmentsQuery.getAppointments());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        appointmentsIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        appointmentsTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentsDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentsLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentsTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentsStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
    }
}