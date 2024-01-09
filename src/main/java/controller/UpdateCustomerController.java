package controller;

import dao.CountriesQuery;
import dao.CustomersQuery;
import dao.FirstLevelDivisionsQuery;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Country;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The Add Part Scene Controller provides functionality for creating an instance of
 * an In House or Outsourced Part that will be added to the Inventory Part List
 * Also contains a "Cancel" to MainMenu option
 */
public class UpdateCustomerController implements Initializable {
    @FXML
    public Label errorLabel;
    @FXML
    public TextField customerIDText;
    @FXML
    public TextField nameText;
    @FXML
    public TextField addressText;
    @FXML
    public TextField postalCodeText;
    @FXML
    public TextField phoneText;
    @FXML
    public ComboBox<Country> countryCombo;
    @FXML
    public ComboBox<FirstLevelDivision> firstLevelDivisionCombo;

    /**
     * Cancels Part submission, Returns to Main Menu
     * @param e ActionEvent for Back Button
     * @throws IOException
     */
    public void onCancel(ActionEvent e) throws IOException {
        Navigation.switchToMainMenu(e);
    }
    public void onCountrySelected() throws SQLException {
        if(countryCombo.getSelectionModel().isEmpty()){
        }
        else{
            countryCombo.getSelectionModel().clearSelection();
            int countryID = countryCombo.getSelectionModel().getSelectedItem().getID();
            firstLevelDivisionCombo.setItems(FirstLevelDivisionsQuery.select(countryID));
        }
    }
    /**
     * Adds Part using the values from all text fields to a new In House or Outsourced object.
     * Performs input validation on all inputs upon submission. Cancels and populates ErrorLabel if invalid
     * Returns to the main menu upon successful submission
     * @param e ActionEvent for the Save button
     * @throws IOException
     */
    public void onSave(ActionEvent e) throws IOException, SQLException {
        int id = MainMenuController.selectedCustomer.getCustomer_ID();
        String name = nameText.getText();
        if(name.isBlank()) {errorLabel.setText("Name cannot be blank!"); return;}
        String address = addressText.getText();
        if(address.isBlank()){ errorLabel.setText("Address cannot be blank!"); return;}
        String postal = postalCodeText.getText();
        if(postal.isBlank()){ errorLabel.setText("Postal Code cannot be blank!"); return;}
        String phone = phoneText.getText();
        if(phone.isBlank()) errorLabel.setText("Phone cannot be blank!");
        int divisionID;
        if(firstLevelDivisionCombo.getSelectionModel().getSelectedItem() != null){
            divisionID = firstLevelDivisionCombo.getSelectionModel().getSelectedItem().getID();
        }
        else{
            errorLabel.setText("Please select a Country and First Level Division!");
            return;
        }
        CustomersQuery.update(id,
                name,
                address,
                postal,
                phone,
                divisionID);
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
        int divisionID = MainMenuController.selectedCustomer.getDivision_ID();
        int countryID;
        FirstLevelDivision FLD;
        Country selectedCountry;
        try {
            countryID = FirstLevelDivisionsQuery.selectCountryID(divisionID);
            FLD = FirstLevelDivisionsQuery.selectGivenFLD(divisionID);
            selectedCountry = CountriesQuery.select(countryID);
            firstLevelDivisionCombo.setItems(FirstLevelDivisionsQuery.select(countryID));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerIDText.setText(""+MainMenuController.selectedCustomer.getCustomer_ID());
        nameText.setText(MainMenuController.selectedCustomer.getCustomer_Name());
        addressText.setText(MainMenuController.selectedCustomer.getAddress());
        postalCodeText.setText(MainMenuController.selectedCustomer.getPostal_Code());
        phoneText.setText(MainMenuController.selectedCustomer.getPhone());
        countryCombo.setValue(selectedCountry);
        firstLevelDivisionCombo.setValue(FLD);
        try {
            countryCombo.setItems(CountriesQuery.select());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}