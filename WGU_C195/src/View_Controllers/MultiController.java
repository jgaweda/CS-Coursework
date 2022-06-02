package View_Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import Models.*;
import DB.DBQuery;
import java.io.IOException;

import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Contains variables and methods used by all other Controllers
 */
public abstract class MultiController implements Initializable 
{

    @FXML
    protected TextField id;

    @FXML
    protected TextField title;

    @FXML
    protected TextField description;

    @FXML
    protected ComboBox<String> location;

    @FXML
    protected ComboBox<String> type;

    @FXML
    protected DatePicker date;

    @FXML
    protected ComboBox<LocalTime> startTime;

    @FXML
    protected ComboBox<LocalTime> endTime;

    @FXML
    protected ComboBox<Integer> customer;

    @FXML
    protected ComboBox<String> user;

    @FXML
    protected ComboBox<String> contact;

    @FXML
    protected TextField name;

    @FXML
    protected TextField address;

    @FXML
    protected TextField postalCode;

    @FXML
    protected TextField phone;

    @FXML
    protected ComboBox<String> division;

    @FXML
    protected ComboBox<String> country;

    @FXML
    protected Label errorLabel;

    @FXML
    protected ToggleGroup radio;

    @FXML
    protected RadioButton typeMonthRadio;

    @FXML
    protected RadioButton contactRadio;

    @FXML
    protected RadioButton locationRadio;
    
    @FXML
    protected Button loginButton;

    @FXML
    protected Button addButton;

    @FXML
    protected Button modifyButton;

    @FXML
    protected Button deleteButton;

    @FXML
    protected Button reportsButton;

    @FXML
    protected Button logoutButton;

    @FXML
    protected Button cancelButton;

    @FXML
    protected Button addSaveButton;

    @FXML
    protected Button modifySaveButton;

    protected DBQuery data;
    
    protected Appointment selectedAppointment;
    
    protected Customer selectedCustomer;
    
    /**
     * Loads the Appointments screen. Sends fxml file address and an instance of the MultiController class to the loadScreen() function. 
     * @param event mouse input when the user clicks the Log In button or Appointments button
     */
    @FXML
    protected void loadAppointments(MouseEvent event) 
    {
        try 
        {
            boolean used = false;
            if (event.getSource() == loginButton)
                used = true;
            String fxml = "/View_Controllers/Appointments.fxml";
            AppointmentsController controller = new AppointmentsController(data, used);
            loadScreen(event, fxml, controller);
        }
        catch (Exception exception) 
        {
            System.out.println(exception);
        }
    }

    /**
     * Loads the Customers screen. Sends fxml file address and an instance of the MultiController class to the loadScreen() function.
     * @param event mouse input when the user clicks the Customers button
     */
    @FXML
    protected void loadCustomers(MouseEvent event)
    {
        try 
        {
            String fxml = "/View_Controllers/Customers.fxml";
            CustomersController controller = new CustomersController(data);
            loadScreen(event, fxml, controller);
        }
        catch (Exception exception) 
        {
            System.out.println(exception);
        }
    }

    /**
     * Loads the TypeMonthReport screen. Sends fxml file address and an instance of the MultiController class to the loadScreen() function.
     * @param event mouse input when the user clicks the Customers button
     */
    @FXML
    protected void loadReports(MouseEvent event) 
    {
        try 
        {
            String fxml = "/View_Controllers/TypeMonthReport.fxml";
            TypeMonthReportController controller = new TypeMonthReportController(data);
            loadScreen(event, fxml, controller);
        }
        catch (IOException exception) 
        {
            System.out.println(exception);
        }
    }

    /**
     * Loads the ContactReport screen. Sends fxml file address and an instance of the MultiController class to the loadScreen() function.
     * @param event mouse input when the user clicks the Customers button
     */
    @FXML
    protected void loadContactReport(MouseEvent event)
    {
        try 
        {
            String fxml = "/View_Controllers/ContactReport.fxml";
            ContactReportController controller = new ContactReportController(data);
            loadScreen(event, fxml, controller);
        }
        catch (IOException exception) 
        {
            System.out.println(exception);
        }
    }

    /**
     * Loads the LocationReport screen. Sends fxml file address and an instance of the MultiController class to the loadScreen() function.
     * @param event mouse input when the user clicks the Customers button
     */
    @FXML
    protected void loadLocationReport(MouseEvent event) 
    {
        try 
        {
            String fxml = "/View_Controllers/LocationReport.fxml";
            LocationReportController controller = new LocationReportController(data);
            loadScreen(event, fxml, controller);
        }
        catch (IOException exception) 
        {
            System.out.println(exception);
        }
    }

    /**
     * Loads a new screen.
     * @param event mouse input when the user clicks a button
     * @param fxml the address of the fxml document
     * @param controller a new instance of the MultiController class
     * @throws java.io.IOException .
     */
    protected void loadScreen(MouseEvent event, String fxml, MultiController controller) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        ResourceBundle rb = ResourceBundle.getBundle("Properties/language");
        loader.setController(controller);
        loader.setResources(rb);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
    *Lambda expression interface. Converts hours from int to LocalTime and use in a ComboBox field
    */
    @FunctionalInterface public interface TimePop 
    {
        void addAll(ComboBox<LocalTime> field, int t1, int t2, int t3, int t4, int t5, int t6, int t7, 
                int t8, int t9, int t10, int t11, int t12, int t13, int t14, int t15, int t16, int t17, int t18, int t19,
                int t20, int t21, int t22, int t23, int t24);
    }
    
    /**
     * Prefills the available options in the ComboBoxes on the Add Appointment and Modify Appointment screens
     * <p>Lambda Expression: Improves the code by avoiding repetition of the verbose syntax for converting an hour from an integer to a LocalTime</p>
     */
    protected void popAppointmentOptions() 
    {
        location.getItems().addAll("Phoenix", "White Plains", "Montreal", "London");
        type.getItems().addAll("Interview", "Meeting", "Planning", "Lunch");

        TimePop comboBox = (field, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23, t24) -> {
            ObservableList<Integer> hours = FXCollections.observableArrayList();
            hours.addAll(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23, t24);
            for (int t : hours)
                field.getItems().add(t,LocalTime.of(t,0));//.toInstant(ZoneOffset.ofHours(0)).atZone(ZoneId.of(TimeZone.getDefault().getID())).toLocalTime());
        };
        comboBox.addAll(startTime, 0, 1, 2, 3 ,4 ,5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23);
        comboBox.addAll(endTime, 0, 1, 2, 3 ,4 ,5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23);

        for (Customer c : data.get_Customer_List())
            customer.getItems().add(c.getCustomerID());
        for (User u : data.get_User_List())
            user.getItems().add(u.getUsername());
        for (Contact c : data.get_Contact_List())
            contact.getItems().add(c.getName());
    }

    /**
     * Prefills the available options in the ComboBoxes on the Add Customer and Modify Customer screens
     */
    protected void popCustomerOptions() 
    {
        for (Division d : data.get_Division_List())
            division.getItems().add(d.getName());
        for (Country c : data.get_Country_List())
            country.getItems().add(c.getName());
    }

    /**
     * Automatically sets the start time value to 1 hour before the selected end time
     * @param event an action event when the user makes a selection from the endTime ComboBox
     */
    @FXML
    protected void updateStartTime(ActionEvent event) 
    {
        startTime.setValue(endTime.getValue().minusHours(1));
    }

    /**
     * Automatically sets the end time value to 1 hour after the selected start time
     * @param event an action event when the user makes a selection from the startTime ComboBox
     */
    @FXML
    protected void updateEndTime(ActionEvent event) 
    {
        endTime.setValue(startTime.getValue().plusHours(1));
    }

    /**
     * Automatically updates the Division ComboBox with the value of the selected Country
     * @param event an action event when the user makes a selection from the Country ComboBox
     */
    @FXML
    protected void updateDivision(ActionEvent event) 
    {
        ObservableList<String> updateOptions = FXCollections.observableArrayList();
        for (Division d : data.get_Division_List())
            if (country.getValue().equals(d.getCountry()))
                updateOptions.add(d.getName());
        division.setItems(updateOptions);
    }

    /**
     * Automatically sets the Country value to the country associated with the selected First Level Division
     * @param event an action event when the user makes a selection from the Division ComboBox
     */
    @FXML
    protected void updateCountry(ActionEvent event) 
    {
        for (Division d : data.get_Division_List())
            if (d.getName().equals(division.getValue()))
                country.setValue(d.getCountry());
    }
    
    /**
    * Lambda expression interface. Gets values from DatePicker and ComboBox fields and then converts them to an Instant value. 
    * Used in saveAppointment()
    */
    @FunctionalInterface public interface TimeConv 
    {
        Instant toInstant(DatePicker date, ComboBox<LocalTime> time);
    }
    
    /**
     * Checks user input for the Add Appointment or Modify Appointment screen fields.  Will display error if any of the fields are empty.
     * If selected date and time are already past, already exist, or are outside of business hours (8am-10pm EST), then calls DBQuery to save the input data to the db
     * <p>Lambda Expression (Line 344): Reduces code repetition and allows the syntax required for converting date/time fields to Instant values to be written once</p>
     * @param event mouse input when the user clicks the Save button
     */
    @FXML
    protected void saveAppointment(MouseEvent event) 
    {
        if (title.getText().trim().isEmpty()) 
        {
            errorLabel.setText("Please enter a Title");
            return;
        }
        if (description.getText().trim().isEmpty()) 
        {
            errorLabel.setText("Please enter Description");
            return;
        }
        if (location.getSelectionModel().isEmpty()) 
        {
            errorLabel.setText("Please select Location");
            return;
        }
        if (type.getSelectionModel().isEmpty()) 
        {
            errorLabel.setText("Please select Type");
            return;
        }
        if (date.getValue() == null)
        {
            errorLabel.setText("Please enter Date");
            return;
        }
        if (startTime.getValue() == null) 
        {
            errorLabel.setText("Please enter Start Time");
            return;
        }
        if (endTime.getValue() == null) 
        {
            errorLabel.setText("Please enter End Time");
            return;
        }

        TimeConv dateTime = (date, time) -> ZonedDateTime.of(date.getValue(), time.getValue(), ZoneId.of(TimeZone.getDefault().getID())).toInstant();
        String tz = "America/New_York";
        Instant start = dateTime.toInstant(date, startTime);
        Instant end = dateTime.toInstant(date, endTime);

        if (start.isBefore(Instant.now())) 
        {
            errorLabel.setText("Please select future Date and Time");
            return;
        }
        if (start.atZone(ZoneId.of(tz)).toLocalTime().isBefore(LocalTime.of(8,0)) || end.atZone(ZoneId.of(tz)).toLocalTime().isAfter(LocalTime.of(21,0))) 
        {
            errorLabel.setText("Select a time during business hours");
            return;
        }

        if (customer.getSelectionModel().isEmpty()) 
        {
            errorLabel.setText("Please select a Customer ID");
            return;
        }
        for (Appointment a : data.get_Appointment_List()) 
        {
            if (customer.getValue() == a.getCustomerID() && start.equals(a.getStart())) 
            {
                errorLabel.setText("Overlapping appointment");
                return;
            }
        }
        if (contact.getSelectionModel().isEmpty()) 
        {
            errorLabel.setText("Please select a Contact");
            return;
        }
        if (user.getSelectionModel().isEmpty()) 
        {
            errorLabel.setText("Please select a User");
            return;
        }

        int userID = 0, contactID = 0;
        for (User u : data.get_User_List())
            if (u.getUsername().equals(user.getValue()))
                userID = u.getUser_ID();
        for (Contact c : data.get_Contact_List())
            if (c.getName().equals(contact.getValue()))
                contactID = c.getID();

        Appointment appointment = new Appointment(Integer.parseInt(id.getText()), title.getText().trim(), description.getText().trim(), location.getValue(), type.getValue(), start, end, user.getValue(), contact.getValue(), customer.getValue(), userID, contactID);

        if (event.getSource() == addSaveButton)
            data.add_Appointment(appointment);
        if (event.getSource() == modifySaveButton)
            data.update_Appointment(appointment);

        loadAppointments(event);
    }

    /**
     * Checks user input for Add Customer or Modify Customer screen fields.  Displays error if any of the fields are empty and calls DBQuery to save the input to the db.
     * @param event mouse input when the user clicks the Save button
     */
    @FXML
    protected void saveCustomer(MouseEvent event)
    {
        if (name.getText().trim().isEmpty())
        {
            errorLabel.setText("Please enter a Name");
            return;
        }
        if (address.getText().trim().isEmpty()) 
        {
            errorLabel.setText("Please enter an Address");
            return;
        }
        if (postalCode.getText().trim().isEmpty()) 
        {
            errorLabel.setText("Please enter a Postal Code");
            return;
        }
        if (phone.getText().trim().isEmpty()) 
        {
            errorLabel.setText("Please enter a Phone Number");
            return;
        }
        if (division.getSelectionModel().isEmpty()) 
        {
            errorLabel.setText("Please select a Division");
            return;
        }
        if (country.getSelectionModel().isEmpty()) 
        {
            errorLabel.setText("Please select a Country");
            return;
        }

        int divisionID = 0;
        for (Division d : data.get_Division_List())
            if (d.getName().equals(division.getValue()))
                divisionID = d.getDivId();

        Customer customer = new Customer(Integer.parseInt(id.getText().trim()), name.getText().trim(), address.getText().trim(), postalCode.getText().trim(), phone.getText().trim(), division.getValue(), country.getValue(), divisionID);

        if (event.getSource() == addSaveButton)
            data.add_Customer(customer);
        if (event.getSource() == modifySaveButton)
            data.update_Customer(customer);

        loadCustomers(event);
    }

    /**
     * Displays confirmation window before deleting item
     * @param id of the custom/appointment being deleted
     * @return boolean value of true if the user wants to proceed and false if not
     */
    protected boolean confirmationWindow(String id) 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setContentText("Are you sure you want to delete" + id + "?");
        alert.setGraphic(null);
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) 
        {
            return true;
        }
        else 
        {
            return false;
        }
    }

    /**
     * Clears any error messages
     * @param event mouse input when the user clicks away from the item that caused the message
     */
    @FXML
    protected void clearError(MouseEvent event) 
    {
        errorLabel.setText("");
    }
}