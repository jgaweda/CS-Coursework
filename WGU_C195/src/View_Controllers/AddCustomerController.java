package View_Controllers;

import Models.Customer;
import DB.DBQuery;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the Add Customer screen
 */
public class AddCustomerController extends MultiController
{

    /**
     * Class constructor
     * @param data all data retrieved from the database by DBQuery
     */
    public AddCustomerController(DBQuery data) 
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
        popCustomerOptions();
        fillData();
        errorLabel.setText("");
    }

    /**
     * Prefills the ID field with the Customer ID of the selected customer
     */
    private void fillData() 
    {
        id.setText(Integer.toString(generateCustomerID()));
    }

    /**
     * Generates a unique Customer ID. Finds the largest existing Customer ID and adds 1
     * @return a unique Customer ID
     */
    private int generateCustomerID() 
    {
        if (data.get_Customer_List().isEmpty())
            return 1;
        else 
        {
            int i = 1;
            for (Customer a : data.get_Customer_List())
                if (a.getCustomerID() > i)
                    i = a.getCustomerID();
            return i + 1;
        }
    }

}
