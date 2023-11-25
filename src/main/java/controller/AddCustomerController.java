package controller;

import dao.CustomersQuery;
import helper.CountryHelper;
import helper.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The Add Part Scene Controller provides functionality for creating an instance of
 * an In House or Outsourced Part that will be added to the Inventory Part List
 * Also contains a "Cancel" to MainMenu option
 */
public class AddCustomerController implements Initializable {
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
    public ComboBox<String> firstLevelDivisionCombo;

    /**
     * Cancels Part submission, Returns to Main Menu
     * @param e ActionEvent for Back Button
     * @throws IOException
     */
    public void onCancel(ActionEvent e) throws IOException {
        Navigation.switchToMainMenu(e);
    }
    public void onCountrySelected(){
        if(countryCombo.getSelectionModel().isEmpty()){
            return;
        }
        else{
            firstLevelDivisionCombo.setItems(countryCombo.getSelectionModel().getSelectedItem().getFirstLevelDivisions());
        }
    }
    /**
     * Adds Part using the values from all text fields to a new In House or Outsourced object.
     * Performs input validation on all inputs upon submission. Cancels and populates ErrorLable if invalid
     * Returns to the main menu upon successful submission
     * @param e ActionEvent for the Save button
     * @throws IOException
     */
    public void onSave(ActionEvent e) throws IOException {
        Navigation.switchToMainMenu(e);
    }
    /*
    public void onAddPartSubmit(ActionEvent e) throws IOException {
        String error = "";
        int partIdValue = Inventory.getNextPartId();
        String partNameValue = partName.getText();
        if (partNameValue.isBlank()){
            errorLabel.setText("Part Name cannot be blank!");
            return;
        }
        Double partPriceValue = 0.00;
        try {
            partPriceValue = Double.parseDouble(partPrice.getText());
        }
        catch(NumberFormatException ex){
            errorLabel.setText("Part Price must be a Double (i.e. 19.99)");
            return;
        }
        try {
            error = "Stock";
            int partStockValue = Integer.parseInt(partStock.getText());
            error = "Min";
            int partMinValue = Integer.parseInt(partMin.getText());
            error = "Max";
            int partMaxValue = Integer.parseInt(partMax.getText());

            if (partMinValue > partMaxValue){
                errorLabel.setText("Min cannot be larger than Max!");
                return;
            }
            else if (partStockValue < partMinValue || partStockValue > partMaxValue){
                errorLabel.setText("Part Stock level must be between the Min and Max!");
                return;
            }
            //Deciding the subclass of Part to add based on the Radio Button selected
            if (inHouseRadio.isSelected()) {
                error = "Machine ID";
                int partSourceValue = Integer.parseInt(partSource.getText());
                Inventory.addPart(new InHouse(partIdValue, partNameValue, partPriceValue, partStockValue, partMinValue, partMaxValue, partSourceValue));
            }
            //Using else statement since Outsourced is the only remaining option if inHouse is not selected
            else {
                String partSourceValue = partSource.getText();
                if (partSourceValue.isBlank()){
                    errorLabel.setText("Company Name cannot be blank!");
                    return;
                }
                Inventory.addPart(new Outsourced(partIdValue, partNameValue, partPriceValue, partStockValue, partMinValue, partMaxValue, partSourceValue));
            }
        }
        catch(NumberFormatException ex){
            errorLabel.setText(error + " must be a number! (1234567890)");
            return;
        }
        Inventory.incrementPartId();
        //Switch Back to Main Menu upon submission
        root = FXMLLoader.load(getClass().getResource("/view/MainMenuScene.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }
    */
    /**
     * Actions to take before showing the Scene.
     * Sets the ID value to display. The rest of the info is User Input
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerIDText.textProperty().set(CustomersQuery.getNextCustomerID()+"");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        countryCombo.setItems(CountryHelper.listOfCountries);
    }
}
