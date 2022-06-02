package View_Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import Models.Appointment;
import DB.DBQuery;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the Appointments by Location Report screen
 */
public class LocationReportController extends MultiController {

    @FXML
    private TableView<Appointment> phoenixTable;

    @FXML
    private TableColumn<Appointment, Integer> phoenixIDColumn;

    @FXML
    private TableColumn<Appointment, String> phoenixTitleColumn;

    @FXML
    private TableColumn<Appointment, String> phoenixDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> phoenixTypeColumn;

    @FXML
    private TableColumn<Appointment, String> phoenixStartColumn;

    @FXML
    private TableColumn<Appointment, String> phoenixEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> phoenixCustomerColumn;

    @FXML
    private TableView<Appointment> whitePlainsTable;

    @FXML
    private TableColumn<Appointment, Integer> whitePlainsIDColumn;

    @FXML
    private TableColumn<Appointment, String> whitePlainsTitleColumn;

    @FXML
    private TableColumn<Appointment, String> whitePlainsDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> whitePlainsTypeColumn;

    @FXML
    private TableColumn<Appointment, String> whitePlainsStartColumn;

    @FXML
    private TableColumn<Appointment, String> whitePlainsEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> whitePlainsCustomerColumn;

    @FXML
    private TableView<Appointment> montrealTable;

    @FXML
    private TableColumn<Appointment, Integer> montrealIDColumn;

    @FXML
    private TableColumn<Appointment, String> montrealTitleColumn;

    @FXML
    private TableColumn<Appointment, String> montrealDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> montrealTypeColumn;

    @FXML
    private TableColumn<Appointment, String> montrealStartColumn;

    @FXML
    private TableColumn<Appointment, String> montrealEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> montrealCustomerColumn;
    
    @FXML
    private TableView<Appointment> londonTable;

    @FXML
    private TableColumn<Appointment, Integer> londonIDColumn;

    @FXML
    private TableColumn<Appointment, String> londonTitleColumn;

    @FXML
    private TableColumn<Appointment, String> londonDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> londonTypeColumn;

    @FXML
    private TableColumn<Appointment, String> londonStartColumn;

    @FXML
    private TableColumn<Appointment, String> londonEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> londonCustomerColumn;

    /**
     * Class constructor
     * @param data all data retrieved from the database by DBQuery
     */
    public LocationReportController(DBQuery data) {
        this.data = data;
    }

    /**
     * Initializes the MultiController class
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        generatePhoenixTable();
        generateWhitePlainsTable();
        generateMontrealTable();
        generateLondonTable();
    }

    /**
     * Generates the Phoenix table view. Filters the schedule of appointments to include only those with a Location set to Phoenix
     */
    private void generatePhoenixTable() 
    {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        for (Appointment a : data.get_Appointment_List())
            if (a.getLocation().equals("Phoenix"))
                filterAppointments.add(a);
        phoenixTable.setItems(filterAppointments);
        phoenixTable.refresh();
    }

    /**
     * Generates the WhitePlains table view. Filters the schedule of appointments to include only those with a Location set to WhitePlains
     */
    private void generateWhitePlainsTable() 
    {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        for (Appointment a : data.get_Appointment_List())
            if (a.getLocation().equals("White Plains"))
                filterAppointments.add(a);
        whitePlainsTable.setItems(filterAppointments);
        whitePlainsTable.refresh();
    }

    /**
     * Generates the Montreal table view. Filters the schedule of appointments to include only those with a Location set to Montreal
     */
    private void generateMontrealTable() 
    {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        for (Appointment a : data.get_Appointment_List())
            if (a.getLocation().equals("Montreal"))
                filterAppointments.add(a);
        montrealTable.setItems(filterAppointments);
        montrealTable.refresh();
    }

    /**
     * Generates the London table view. Filters the schedule of appointments to include only those with a Location set to London
     */
    private void generateLondonTable() 
    {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        for (Appointment a : data.get_Appointment_List())
            if (a.getLocation().equals("London"))
                filterAppointments.add(a);
        londonTable.setItems(filterAppointments);
        londonTable.refresh();
    }
    
}