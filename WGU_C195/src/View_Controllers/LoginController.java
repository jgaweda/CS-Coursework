package View_Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import Models.User;
import DB.DBQuery;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controls the login window
 */
public class LoginController extends MultiController {

    @FXML
    private Label loginLabel;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    private ResourceBundle rb;

    /**
     * Class constructor
     * @param data the complete set of data retrieved from the db by DBQuery 
     */
    public LoginController(DBQuery data) 
    {
        this.data = data;
    }

    /**
     * Initializes the MultiController class
     * @param url location used to resolve relative paths for the root object or null if the location is not known
     * @param rb resources used to localize the root object or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        this.rb = rb;
        findLocation();
    }

    /**
     * Displays user's time zone in UTC and detect users language to be displayed throughout application.
     */
    private void findLocation() 
    {
        loginLabel.setText(rb.getString("login"));
        username.setPromptText(rb.getString("username"));
        password.setPromptText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        errorLabel.setText(rb.getString("timezone") + " UTC " + ZoneOffset.systemDefault().getRules().getOffset(Instant.now()));
    }

    /**
     * Check username and password. If credentials are correct the Appointments screen will load. 
     * If incorrect credentials are used, will display 
     * an error message in the language set by the user's OS
     * @param event mouse input when the user clicks the Log In button
     */
    @FXML
    private void checkCreds(MouseEvent event) 
    {
        for (User u : data.get_User_List()) 
        {
            if (u.getUsername().equals(username.getText().trim()) && u.getPassword().equals(password.getText().trim())) 
            {
                data.set_User(u);
                log("Successful");
                loadAppointments(event);
                return;
            }
        }
        errorLabel.setText(rb.getString("error"));
        log("Unsuccessful");
    }

    /**
     * Records log-in attempts along with the date/time, and whether the attempt was successful in a file named login_activity.txt. 
     * Appends new records to the existing log file and saves to the application root folder with a time stamp.
     * @param successful Indicates whether the login attempt was successful or not
     */
    private void log(String successful) 
    {
        try 
        {
            FileWriter writer = new FileWriter("login_activity.txt", true);
            writer.write("Log in attempt with username '" + username.getText().trim() + "'" + " on " + DateTimeFormatter.ofPattern("MM-dd-yyyy").format(LocalDateTime.now()) + " at " + DateTimeFormatter.ofPattern("HH:mm").format(LocalDateTime.now()) + " was " + successful + "\n");
            writer.close();
        }
        catch (IOException exception) 
        {
            exception.printStackTrace();
        }
    }

}