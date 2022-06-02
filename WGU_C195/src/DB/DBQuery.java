package DB;

import Models.*;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Handles db queries
 */
public class DBQuery 
{
    private final ObservableList<Appointment> appointment_list = FXCollections.observableArrayList();
    private final ObservableList<User> user_list = FXCollections.observableArrayList();
    private final ObservableList<Contact> contact_list = FXCollections.observableArrayList();
    private final ObservableList<Customer> customer_list = FXCollections.observableArrayList();
    private final ObservableList<Country> country_list = FXCollections.observableArrayList();
    private final ObservableList<Division> division_list = FXCollections.observableArrayList();
    private User user;

    /**
     * Reads all data from the database
     */
    public void read_DB_Data() 
    {
        read_Appointment_List();
        read_Contact_List();
        read_Country_List();
        read_Customer_List();
        read_Division_List();
        read_User_List();
    }

    /**
     * Gets a User object representing the current user
     * @return a User object representing the current user
     */
    public User get_User() 
    {
        return user;
    }

    /**
     * Sets a User object representing the current user
     * @param user a User object representing the current user
     */
    public void set_User(User user) 
    {
        this.user = user;
    }
    
    /**
     * Gets list of appointments
     * @return the list of all appointments
     */
    public ObservableList<Appointment> get_Appointment_List() 
    {
        return appointment_list;
    }

    /**
     * Reads data from appointment_list, stores each record in an Appointment object and then in an ObservableList
     */
    private void read_Appointment_List() 
    {
        try 
        {
            Statement statement = DBConnection.ConnectionObject().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM appointments INNER JOIN contacts ON appointments.contact_id = contacts.contact_id INNER JOIN users ON appointments.user_id = users.user_id");
            while (rs.next())
            {
                int appointmentID = Integer.parseInt(rs.getString("appointment_id").trim());
                String title = rs.getString("title").trim();
                String description = rs.getString("description").trim();
                String location = rs.getString("location").trim();
                String type = rs.getString("type").trim();
                Instant start = rs.getTimestamp("start").toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant();
                Instant end = rs.getTimestamp("end").toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant();
                String username = rs.getString("user_name").trim();
                String contact = rs.getString("contact_name").trim();
                int customerID = Integer.parseInt(rs.getString("customer_id").trim());
                int userID = Integer.parseInt(rs.getString("user_id").trim());
                int contactID = Integer.parseInt(rs.getString("contact_id").trim());
                appointment_list.add(new Appointment(appointmentID, title, description, location, type, start, end, username, contact, customerID, userID, contactID));
            }
        }
        catch (SQLException | NumberFormatException exception)
        {
            System.out.println(exception);
        }
    }

    /**
     * Adds a record to appointment_list when a new Appointment object is created
     * @param appointment is the current appointment
     */
    public void add_Appointment(Appointment appointment) 
    {
        if (appointment != null)
            appointment_list.add(appointment);
        try 
        {
            String sql = "INSERT INTO appointments (appointment_id, title, description, location, type, start, end, create_date, created_by, last_update, last_updated_by, customer_id, user_id, contact_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.ConnectionObject().prepareStatement(sql);
            ps.setString(1, Integer.toString(appointment.getAppointmentID()));
            ps.setString(2, appointment.getTitle());
            ps.setString(3, appointment.getDescription());
            ps.setString(4, appointment.getLocation());
            ps.setString(5, appointment.getType());
            ps.setTimestamp(6, Timestamp.from(appointment.getStart())); 
            ps.setTimestamp(7, Timestamp.from(appointment.getEnd()));
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(9, get_User().getUsername());
            ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(11, get_User().getUsername());
            ps.setString(12, Integer.toString(appointment.getCustomerID()));
            ps.setString(13, Integer.toString(appointment.getUserID()));
            ps.setString(14, Integer.toString(appointment.getContactID()));
            ps.executeUpdate();
        }
        catch (SQLException exception)
        {
            System.out.println(exception);
        }
    }

    /**
     * Updates the appointment_list table whenever an Appointment object is updated
     * @param appointment is the current appointment
     */
    public void update_Appointment(Appointment appointment) 
    {
        for (int i = 0; i < appointment_list.size(); i++) 
        {
            if (appointment_list.get(i).getAppointmentID() == appointment.getAppointmentID()) 
            {
                appointment_list.set(i, appointment);
                break;
            }
        }
        try 
        {
            String sql = "UPDATE appointments SET title = ?, description = ?, location = ?, type = ?, start = ?, end = ?, last_update = ?, last_updated_by = ?, customer_id = ?, user_id = ?, contact_id = ? WHERE appointment_id = ?";
            PreparedStatement ps = DBConnection.ConnectionObject().prepareStatement(sql);
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.ofInstant(appointment.getStart(), ZoneOffset.ofHours(0))));
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.ofInstant(appointment.getEnd(), ZoneOffset.ofHours(0))));
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8, get_User().getUsername());
            ps.setString(9, Integer.toString(appointment.getCustomerID()));
            ps.setString(10, Integer.toString(appointment.getUserID()));
            ps.setString(11, Integer.toString(appointment.getContactID()));
            ps.setString(12, Integer.toString(appointment.getAppointmentID()));
            ps.executeUpdate();
        }
        catch (SQLException exception) 
        {
            System.out.println(exception);
        }
    }

    /**
     * Deletes a record in the appointment_list table in the database whenever an Appointment object is deleted
     * @param appointment is the current appointment
     */
    public void delete_Appointment(Appointment appointment) 
    {
        appointment_list.remove(appointment);
        try 
        {
            String sql = "DELETE FROM appointments WHERE appointment_id = ? ";
            PreparedStatement ps = DBConnection.ConnectionObject().prepareStatement(sql);
            ps.setString(1, Integer.toString(appointment.getAppointmentID()));
            ps.executeUpdate();
        }
        catch (SQLException exception)
        {
            System.out.println(exception);
        }
    }

    /**
     * Gets list of all users
     * @return list of all users
     */
    public ObservableList<User> get_User_List() 
    {
        return user_list;
    }

    /**
     * Reads from the user_list table, stores each record in an User object, and stores the User objects in an ObservableList
     */
    public void read_User_List() 
    {
        try 
        {
            Statement statement = DBConnection.ConnectionObject().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users");
            while (rs.next())
            {
                int id = Integer.parseInt(rs.getString("user_id").trim());
                String name = rs.getString("user_name").trim();
                String password = rs.getString("password").trim();
                user_list.add(new User(id, name, password));
            }
        }
        catch (SQLException | NumberFormatException exception) 
        {
            System.out.println(exception);
        }
    }

    /**
     * Gets list of all contacts
     * @return list of all contacts
     */
    public ObservableList<Contact> get_Contact_List() 
    {
        return contact_list;
    }

    /**
     * Reads data from the contact_list table, stores each record in an Contact object, and stores the Contact objects in an ObservableList
     */
    public void read_Contact_List() 
    {
        try 
        {
            Statement statement = DBConnection.ConnectionObject().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM contacts");
            while (rs.next()) 
            {
                int id = Integer.parseInt(rs.getString("contact_id").trim());
                String name = rs.getString("contact_name").trim();
                String email = rs.getString("email").trim();
                contact_list.add(new Contact(id, name, email));
            }
        }
        catch (SQLException | NumberFormatException exception)
        {
            System.out.println(exception);
        }
    }

    /**
     * Gets list of all customer_list
     * @return list of all customer_list
     */
    public ObservableList<Customer> get_Customer_List() 
    {
        return customer_list;
    }

    /**
     * Reads data from the customer_list table in the database, stores each record in an Customer object, and stores the Customer objects in an ObservableList
     */
    public void read_Customer_List() 
    {
        try 
        {
            Statement statement = DBConnection.ConnectionObject().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM customers INNER JOIN first_level_divisions ON customers.division_id = first_level_divisions.division_id INNER JOIN countries ON first_level_divisions.country_id = countries.country_id");
            while (rs.next()) 
            {
                int customerID = Integer.parseInt(rs.getString("customer_id").trim());
                String name = rs.getString("customer_name").trim();
                String address = rs.getString("address").trim();
                String postalCode = rs.getString("postal_code").trim();
                String phone = rs.getString("phone").trim();
                String division = rs.getString("division").trim();
                String country = rs.getString("country").trim();
                int divisionID = Integer.parseInt(rs.getString("division_id").trim());
                customer_list.add(new Customer(customerID, name, address, postalCode, phone, division, country, divisionID));
            }
        }
        catch (SQLException | NumberFormatException exception) 
        {
            System.out.println(exception);
        }
    }

    /**
     * Adds an entry to the customer_list table when a new Customer object is created
     * @param customer is the current customer
     */
    public void add_Customer(Customer customer) 
    {
        if (customer != null)
            customer_list.add(customer);
        try 
        {
            String sql = "INSERT INTO customers (customer_id, customer_name, address, postal_code, phone, create_date, created_by, last_update, last_updated_by, division_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.ConnectionObject().prepareStatement(sql);
            ps.setString(1, Integer.toString(customer.getCustomerID()));
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPostalCode());
            ps.setString(5, customer.getPhone());
            ps.setString(6, LocalDateTime.now().toString()); // ADD DATE
            ps.setString(7, get_User().getUsername());
            ps.setString(8, LocalDateTime.now().toString()); // ADD DATE
            ps.setString(9, get_User().getUsername());
            ps.setString(10, Integer.toString(customer.getDivisionID()));
            ps.executeUpdate();
        }
        catch (SQLException exception) 
        {
            System.out.println(exception);
        }
    }

    /**
     * Updates the customer_list table whenever a Customer object is updated
     * @param customer is the current customer 
     */
    public void update_Customer(Customer customer) 
    {
        for (int i = 0; i < customer_list.size(); i++) 
        {
            if (customer_list.get(i).getCustomerID() == customer.getCustomerID()) 
            {
                customer_list.set(i, customer);
                break;
            }
        }
        try 
        {
            String sql = "UPDATE customers SET customer_name = ?, address = ?, postal_code = ?, phone = ?, last_update = ?, last_updated_by = ?, division_id = ? WHERE customer_id = ?";
            PreparedStatement ps = DBConnection.ConnectionObject().prepareStatement(sql);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhone());
            ps.setString(5, LocalDateTime.now().toString()); 
            ps.setString(6, get_User().getUsername());
            ps.setString(7, Integer.toString(customer.getDivisionID()));
            ps.setString(8, Integer.toString(customer.getCustomerID()));
            ps.executeUpdate();
        }
        catch (SQLException exception) 
        {
            System.out.println(exception);
        }
    }

    /**
     * Deletes entry in the customer_list table when a Customer object is deleted
     * @param customer is the current customer
     */
    public void delete_Customer(Customer customer) 
    {
        customer_list.remove(customer);
        try 
        {
            String sql = "DELETE FROM customers WHERE customer_id = ? ";
            PreparedStatement ps = DBConnection.ConnectionObject().prepareStatement(sql);
            ps.setString(1, Integer.toString(customer.getCustomerID()));
            ps.executeUpdate();
        }
        catch (SQLException exception) 
        {
            System.out.println(exception);
        }
    }

    /**
     * Gets list of all first level division
     * @return list of all first level division
     */
    public ObservableList<Division> get_Division_List() 
    {
        return division_list;
    }

    /**
     * Reads the division_list table, stores each record in an Division object, and stores the Division objects in an ObservableList
     */
    public void read_Division_List() 
    {
        try 
        {
            Statement statement = DBConnection.ConnectionObject().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM first_level_divisions INNER JOIN countries ON first_level_divisions.country_id = countries.country_id");
            while (rs.next()) 
            {
                int id = Integer.parseInt(rs.getString("division_id").trim());
                String name = rs.getString("division").trim();
                String country = rs.getString("country").trim();
                division_list.add(new Division(id, name, country));
            }
        }
        catch (SQLException | NumberFormatException exception) 
        {
            System.out.println(exception);
        }
    }

    /**
     * Gets list of all countries
     * @return list of all counties
     */
    public ObservableList<Country> get_Country_List() 
    {
        return country_list;
    }

    /**
     * Reads the country_list table and stores each record in an Country object.  Country objects are stored in an ObservableList
     */
    public void read_Country_List() 
    {
        try 
        {
            Statement statement = DBConnection.ConnectionObject().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM countries");
            while (rs.next()) 
            {
                int id = Integer.parseInt(rs.getString("country_id").trim());
                String name = rs.getString("country").trim();
                country_list.add(new Country(id, name));
            }
        }
        catch (SQLException | NumberFormatException exception) 
        {
            System.out.println(exception);
        }
    }
}