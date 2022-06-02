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
 * Controls the Contact Schedules Report screen
 */
public class ContactReportController extends MultiController 
{

    @FXML
    private TableView<Appointment> tableA;

    @FXML
    private TableColumn<Appointment, Integer> tableAIDColumn;

    @FXML
    private TableColumn<Appointment, String> tableATitleColumn;

    @FXML
    private TableColumn<Appointment, String> tableADescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> tableATypeColumn;

    @FXML
    private TableColumn<Appointment, String> tableAStartColumn;

    @FXML
    private TableColumn<Appointment, String> tableAEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> tableACustomerColumn;

    @FXML
    private TableView<Appointment> tableB;

    @FXML
    private TableColumn<Appointment, Integer> tableBIDColumn;

    @FXML
    private TableColumn<Appointment, String> tableBTitleColumn;

    @FXML
    private TableColumn<Appointment, String> tableBDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> tableBTypeColumn;

    @FXML
    private TableColumn<Appointment, String> tableBStartColumn;

    @FXML
    private TableColumn<Appointment, String> tableBEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> tableBCustomerColumn;

    @FXML
    private TableView<Appointment> tableC;

    @FXML
    private TableColumn<Appointment, Integer> tableCIDColumn;

    @FXML
    private TableColumn<Appointment, String> tableCTitleColumn;

    @FXML
    private TableColumn<Appointment, String> tableCDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> tableCTypeColumn;

    @FXML
    private TableColumn<Appointment, String> tableCStartColumn;

    @FXML
    private TableColumn<Appointment, String> tableCEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> tableCCustomerColumn;

    /**
     * Class constructor
     * @param data all data retrieved from the database by DBQuery
     */
    public ContactReportController(DBQuery data) 
    {
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
        tableA();
        tableB();
        tableC();
    }

    /**
     * Generates table A
     */
    private void tableA() 
    {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        data.get_Appointment_List().stream().filter((a) -> (a.getContact().equals("Amari Andrews"))).forEachOrdered((a) -> {
            filterAppointments.add(a);
        });
        tableA.setItems(filterAppointments);
        tableA.refresh();
    }

    /**
     * Generates table B
     */
    private void tableB() 
    {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        data.get_Appointment_List().stream().filter((b) -> (b.getContact().equals("Bill Belichick"))).forEachOrdered((b) -> {
            filterAppointments.add(b);
        });
        tableB.setItems(filterAppointments);
        tableB.refresh();
    }

    /**
     * Generates table C
     */
    private void tableC() 
    {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        data.get_Appointment_List().stream().filter((c) -> (c.getContact().equals("Chris Carson"))).forEachOrdered((c) -> {
            filterAppointments.add(c);
        });
        tableC.setItems(filterAppointments);
        tableC.refresh();
    }

}