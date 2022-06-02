package View_Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import Models.Appointment;
import Models.Count;
import DB.DBQuery;

import java.net.URL;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class TypeMonthReportController extends MultiController 
{

    @FXML
    private TableView<Count> typeTable;

    @FXML
    private TableColumn<Count, String> typeTableColumn;

    @FXML
    private TableColumn<Count, Integer> typeAppointmentsColumn;

    @FXML
    private TableView<Count> monthTable;

    @FXML
    private TableColumn<Count, String> typeMonthColumn;

    @FXML
    private TableColumn<Count, Integer> typeMonthAppointmentsColumn;

    /**
     * Class constructor
     * @param data all data retrieved from the database by DBQuery
     */
    public TypeMonthReportController(DBQuery data) 
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
        generateTypeTable();
        generateMonthTable();
    }

    /**
     * Generates the Type table view. Calculates the number of appointments for each type and populates the table with those values
     */
    private void generateTypeTable()
    {
        ObservableList<Count> counts = FXCollections.observableArrayList();
        ObservableList<Count> view = FXCollections.observableArrayList();
        Count interview = new Count("Interview", 0);
        Count meeting = new Count("Meeting", 0);
        Count planning = new Count("Planning", 0);
        Count lunch = new Count("Lunch", 0);
        counts.addAll(interview, meeting, planning, lunch);

        for (Appointment a : data.get_Appointment_List())
            for (Count c : counts)
                if (c.getType().equals(a.getType()))
                    c.setCount(c.getCount() + 1); 
        for (Count c : counts)
            if (c.getCount() > 0)
                view.add(c);

        typeTable.setItems(view);
        typeTable.refresh();
    }

    /**
     * Generates the Month table view. Calculates number of appointments for each month and populates the table with those values
     */
    private void generateMonthTable() 
    {
        ObservableList<Count> counts = FXCollections.observableArrayList();
        ObservableList<Count> view = FXCollections.observableArrayList();
        Count jan = new Count(0, "January");
        Count feb = new Count(0, "February");
        Count mar = new Count(0, "March");
        Count apr = new Count(0, "April");
        Count may = new Count(0, "May");
        Count jun = new Count(0, "June");
        Count jul = new Count(0, "July");
        Count aug = new Count(0, "August");
        Count sep = new Count(0, "September");
        Count oct = new Count(0, "October");
        Count nov = new Count(0, "November");
        Count dec = new Count(0, "December");
        counts.addAll(jan,feb,mar,apr,may,jun,jul,aug,sep,oct,nov,dec);

        for (Appointment a : data.get_Appointment_List())
            for (Count c : counts)
                if (c.getMonth().equals(a.getStart().atZone(ZoneId.systemDefault()).toLocalDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)))
                    c.setCount(c.getCount() + 1);
        for (Count c : counts)
            if (c.getCount() > 0)
                view.add(c);

        monthTable.setItems(view);
        monthTable.refresh();
    }

}