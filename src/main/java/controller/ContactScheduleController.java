package controller;

import dao.AppointmentsQuery;
import dao.ContactsQuery;
import helper.Filterer;
import helper.Navigation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller Class for displaying contact schedules
 * This class allows for the selection of a Contact and viewing their list of appointments
 * Incorporates lambda expression contactFilter to filter All appointments based on a selected contact
 */
public class ContactScheduleController implements Initializable {
    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private Label errorLabel;
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
    private ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
    Filterer<Appointment> contactFilter = (inputList, contactID) ->
            inputList.filtered(appointment -> appointment.getContact_ID() == contactID);
    /**
     * Switches to the MainMenu
     * @param e ActionEvent for MainMenu button
     * @throws IOException throws IOException
     */
    public void onSwitchToMainMenu(ActionEvent e) throws IOException {
        Navigation.switchToMainMenu(e);
    }
    /**
     * Displays the schedule of appointments for the selected contact
     * @throws SQLException on SQL Exception occurance
     */
    public void onViewContactSchedule() throws SQLException {
        if(contactCombo.getSelectionModel().getSelectedItem() != null) {
            int selectedContactID = contactCombo.getSelectionModel().getSelectedItem().getContact_ID();
            contactAppointments = contactFilter.extract
                    (AppointmentsQuery.getAllAppointments(),selectedContactID);
            appointmentsTable.setItems(contactAppointments);
            //appointmentsTable.setItems(AppointmentsQuery.getContactAppointments(selectedContactID));
            errorLabel.setText("");
        }
        else errorLabel.setText("Please select a Contact");
    }
    /**
     * Initializes the controller, loading initial data
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            contactCombo.setItems(ContactsQuery.getContacts());
            contactAppointments = contactFilter.extract
                    (AppointmentsQuery.getAllAppointments(),ReportingController.selectedContactID);

//            contactAppointments = FXCollections.observableList(AppointmentsQuery.getAllAppointments()
//                    .stream()
//                    .filter(e -> e.getContact_ID()==ReportingController.selectedContactID)
//                    .collect(Collectors.toList()));
            appointmentsTable.setItems(contactAppointments);
            //appointmentsTable.setItems(AppointmentsQuery.getContactAppointments(ReportingController.selectedContactID));
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
