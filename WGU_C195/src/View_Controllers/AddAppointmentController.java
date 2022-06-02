package View_Controllers;

import Models.Appointment;
import DB.DBQuery;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the Add Appointment screen
 */
public class AddAppointmentController extends MultiController {

    /**
     * Class constructor
     * @param data all data retrieved from the database by DBQuery
     */
    public AddAppointmentController(DBQuery data) {
        this.data = data;
    }

    /**
     * Initializes the MultiController class
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     * @param rb The resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        popAppointmentOptions();
        fillData();
        errorLabel.setText("");
    }

    /**
     * Prefills the ID field with the Appointment ID of the selected appointment and the User field with the Username of the current user
     */
    private void fillData() {
        id.setText(Integer.toString(generateAppointmentID()));
        user.setValue(data.get_User().getUsername());
    }

    /**
     * Generates a unique Appointment ID. Finds the largest existing Appointment ID and adds 1
     * @return a unique Appointment ID
     */
    private int generateAppointmentID() {
        if (data.get_Appointment_List().isEmpty())
            return 1;
        else {
            int i = 1;
            for (Appointment a : data.get_Appointment_List())
                if (a.getAppointmentID() > i)
                    i = a.getAppointmentID();
            return i + 1;
        }
    }

}
