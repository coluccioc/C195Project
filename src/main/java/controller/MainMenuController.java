package controller;

import dao.CustomersQuery;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable{
    @FXML
    private Label welcomeLabel;
    @FXML
    private TableView customersTable;
    @FXML
    private TableColumn customersIDColumn;
    @FXML
    private TableColumn customersNameColumn;
    @FXML
    private TableColumn customersAddressColumn;
    @FXML
    private TableColumn customersDivisionIDColumn;
    @FXML
    private TableColumn customersPhoneColumn;

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
        customersDivisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("division_ID"));
        customersPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }
}