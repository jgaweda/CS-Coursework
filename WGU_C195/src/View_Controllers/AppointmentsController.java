package View_Controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import Models.Appointment;
import DB.DBQuery;

import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Controls the Appointments screen
 */
public class AppointmentsController extends MultiController 
{

    @FXML
    private TableView<Appointment> table;

    @FXML
    private RadioButton weekRadio;

    @FXML
    private RadioButton monthRadio;

    @FXML
    private RadioButton allRadio;

    @FXML
    private Button customersButton;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentTitleColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentLocationColumn;

    @FXML
    private TableColumn<Appointment, Integer> appointmentContactColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endColumn;

    @FXML
    private TableColumn<Appointment, Integer> customerIDColumn;

    private boolean free;

    /**
     * Class constructor
     * @param data all data retrieved from the database by DBQuery
     * @param free for appointments
     */
    public AppointmentsController(DBQuery data, boolean free)
    {
        this.data = data;
        this.free = free;
    }

    /**
     * Initializes the MultiController class
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        generateTable(data.get_Appointment_List());
        upcomingAppointment();
    }

    /**
     * Displays a message when there is an appointment associated with the current user that starts within 15 minutes of the user's login. Otherwise displays a message indicating there are no upcoming appointments
     */
    private void upcomingAppointment() 
    {
        if (free == true) {
            for (Appointment a : data.get_Appointment_List()) 
            {
                if (data.get_User().getUser_ID() == a.getUserID() && a.getStart().isAfter(Instant.now()) && a.getStart().isBefore(Instant.now().plus(Duration.ofMinutes(15)))) {
                    errorLabel.setText("Appointment " + Integer.toString(a.getAppointmentID()) + " on " + DateTimeFormatter.ofPattern("MM-dd-yyyy").format(a.getStart().atZone(ZoneId.of(TimeZone.getDefault().getID()))) + " at " + DateTimeFormatter.ofPattern("HH:mm").format(a.getStart().atZone(ZoneId.of(TimeZone.getDefault().getID()))) + " starts in less than 15 minutes");
                    return;
                }
            }
            errorLabel.setText("There are no upcoming appointments");
        }
    }

    /**
     * Generates the table view and populates it with data from the ObservableList 'view', refreshes and clears error notifications
     * @param view an ObservableList containing data displayed in the table
     */
    private void generateTable(ObservableList<Appointment> view) 
    {
        table.setItems(view);
        table.refresh();
        errorLabel.setText("");
    }

    /**
     * Displays all appointments in the table view
     * @param event mouse input when the user selects the All radio button
     */
    @FXML
    private void viewAll(MouseEvent event) 
    {
        generateTable(data.get_Appointment_List());
    }

    /**
     * Filters the appointments to displays only appointments in the current month in the table view
     * @param event mouse input when the user selects the Month radio button
     */
    @FXML
    private void viewMonth(MouseEvent event) 
    {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        for (Appointment a : data.get_Appointment_List())
            if (a.getStart().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue() == LocalDate.now().getMonthValue())
                filterAppointments.add(a);
        generateTable(filterAppointments);
    }

    /**
     * Filters appointments to display only the next 7 days in the table view
     * @param event mouse input when user clicks the Month radio button
     */
    @FXML
    private void viewWeek(MouseEvent event) 
    {
        ObservableList<Appointment> filterAppointments = FXCollections.observableArrayList();
        for (Appointment a : data.get_Appointment_List())
            if (!a.getStart().isBefore(Instant.now()) && a.getStart().isBefore(Instant.now().plus(Duration.ofDays(7))))
                filterAppointments.add(a);
        generateTable(filterAppointments);
    }

    /**
     * Launches the Add Appointment screen
     * @param event mouse input when the user clicks the Add button
     */
    @FXML
    private void add_Appointment(MouseEvent event) 
    {
        try 
        {
            String fxml = "/View_Controllers/AddAppointment.fxml";
            AddAppointmentController controller = new AddAppointmentController(data);
            loadScreen(event, fxml, controller);
        }
        catch (Exception exception) 
        {
            System.out.println(exception);
        }
    }

    /**
     * Launches the Modify Appointment screen and checks to make sure the schedule is not empty
     * @param event mouse input when the user clicks the Modify button
     */
    @FXML
    private void modifyAppointment(MouseEvent event)
    {
        try 
        {
            selectedAppointment = table.getSelectionModel().getSelectedItem();
            if (data.get_Appointment_List().isEmpty()) 
            {
                errorLabel.setText("There are no appointments to modify");
                return;
            }
            else if (selectedAppointment == null) 
            {
                errorLabel.setText("Please select an appointment");
                return;
            }

            String fxml = "/View_Controllers/ModifyAppointment.fxml";
            ModifyAppointmentController controller = new ModifyAppointmentController(data, selectedAppointment);
            loadScreen(event, fxml, controller);
        }
        catch (Exception exception) 
        {
            System.out.println(exception);
        }
    }

    /**
     * Deletes an appointment from the schedule. Checks to make sure the schedule is not empty and asks user to confirm deletion. Displays a message upon successful deletion
     * @param event mouse input when the user clicks the Delete button
     */
    @FXML
    private void delete_Appointment(MouseEvent event)
    {
        Appointment selectedAppointment = table.getSelectionModel().getSelectedItem();
        if (data.get_Appointment_List().isEmpty()) 
        {
            errorLabel.setText("There are no appointments to delete");
            return;
        }
        else if (selectedAppointment == null) 
        {
            errorLabel.setText("Please select an appointment");
            return;
        }
        boolean confirm = confirmationWindow(" Appointment " + Integer.toString(selectedAppointment.getAppointmentID()));
        if (!confirm)
            return;

        data.delete_Appointment(selectedAppointment);
        table.setItems(data.get_Appointment_List());
        table.refresh();
        errorLabel.setText("Appointment " + Integer.toString(selectedAppointment.getAppointmentID()) + " " + selectedAppointment.getType() + " has been cancelled");
    }

    /**
     * Exits the application
     * @param event mouse input when the user clicks the Log Out button
     */
    @FXML
    private void logout(MouseEvent event) 
    {
        Platform.exit();
    }

}
