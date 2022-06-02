package View_Controllers;

import Models.Appointment;
import DB.DBQuery;

import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Controls the Modify Appointment screen
 */
public class ModifyAppointmentController extends MultiController {

    /**
     * Class constructor
     * @param data all data retrieved from the database by DBQuery
     * @param selectedAppointment the appointment being modified 
     */
    public ModifyAppointmentController(DBQuery data, Appointment selectedAppointment) 
    {
        this.data = data;
        this.selectedAppointment = selectedAppointment;
    }

    /**
     * Initializes the MultiController class
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        popAppointmentOptions();
        fillData();
        errorLabel.setText("");
    }

    /**
     * Prefills the form with the selected appointment. Displays in the time zone set by the user PC
     */
    private void fillData() 
    {
        id.setText(Integer.toString(selectedAppointment.getAppointmentID()));
        title.setText(selectedAppointment.getTitle());
        description.setText(selectedAppointment.getDescription());
        location.setValue(selectedAppointment.getLocation());
        type.setValue(selectedAppointment.getType());
        date.setValue(selectedAppointment.getStart().atZone(ZoneId.of(TimeZone.getDefault().getID())).toLocalDate());
        startTime.setValue(selectedAppointment.getStart().atZone(ZoneId.of(TimeZone.getDefault().getID())).toLocalTime());
        endTime.setValue(selectedAppointment.getEnd().atZone(ZoneId.of(TimeZone.getDefault().getID())).toLocalTime());
        customer.setValue(selectedAppointment.getCustomerID());
        user.setValue(selectedAppointment.getUsername());
        contact.setValue(selectedAppointment.getContact());
    }

}
