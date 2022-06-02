package Models;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * Creates Appointment objects that represent appointments in the db
 */
public class Appointment 
{

    private int appointmentID;
    private int customerID;
    private int userID;
    private int contactID;
    private String title;
    private String description;
    private String location;
    private String type;
    private String username;
    private String contact;
    private Instant start;
    private Instant end;
    private LocalTime startTime;
    private LocalTime endTime;
    private String startFormatted;
    private String endFormatted;

    /**
     * Class constructor. Stores appointment start/end times in the time zone set by the user OS. Displayed in UTC format.
     * @param appointmentID the appointment ID
     * @param title the appointment title
     * @param description the appointment description
     * @param location the appointment location
     * @param type the appointment type
     * @param start the appointment start date and time in UTC
     * @param end the appointment end date and time in UTC
     * @param username the username of the user that created the appointment
     * @param contact the company contact associated with the appointment
     * @param customerID the customer ID for the appointment
     * @param userID the ID of the current user.
     * @param contactID the contact ID of the company contact associated with the appointment
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, Instant start, Instant end, String username, String contact, int customerID, int userID, int contactID) 
    {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.username = username;
        this.contact = contact;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        startTime = start.atZone(ZoneId.systemDefault()).toLocalTime();
        endTime = end.atZone(ZoneId.systemDefault()).toLocalTime();
        startFormatted = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm").format(start.atZone(ZoneId.systemDefault()));
        endFormatted = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm").format(end.atZone(ZoneId.systemDefault()));
    }

    /**
     * Gets the appointment ID
     * @return the appointment ID
     */
    public int getAppointmentID() 
    {
        return appointmentID;
    }

    /**
     * Sets the appointment ID
     * @param appointmentID the appointment ID
     */
    public void setAppointmentID(int appointmentID) 
    {
        this.appointmentID = appointmentID;
    }

    /**
     * Gets the appointment title
     * @return the appointment title
     */
    public String getTitle() 
    {
        return title;
    }

    /**
     * Sets the appointment title
     * @param title the appointment title
     */
    public void setTitle(String title) 
    {
        this.title = title;
    }

    /**
     * Gets the appointment description
     * @return the appointment description
     */
    public String getDescription() 
    {
        return description;
    }

    /**
     * Sets the appointment description
     * @param description the appointment description
     */
    public void setDescription(String description) 
    {
        this.description = description;
    }

    /**
     * Gets the appointment location
     * @return the appointment location
     */
    public String getLocation() 
    {
        return location;
    }

    /**
     * Sets the appointment location
     * @param location the appointment location
     */
    public void setLocation(String location) 
    {
        this.location = location;
    }

    /**
     * Gets the appointment type
     * @return the appointment type
     */
    public String getType()
    {
        return type;
    }

    /**
     * Sets the appointment type
     * @param type the appointment type
     */
    public void setType(String type) 
    {
        this.type = type;
    }

    /**
     * Gets the appointment start date and time in UTC
     * @return the appointment start date and time in UTC
     */
    public Instant getStart()
    {
        return start.atOffset(ZoneOffset.UTC).toInstant();
    }

    /**
     * Sets the appointment start date and time in UTC
     * @param start the appointment start date and time in UTC
     */
    public void setStart(Instant start) 
    {
        this.start = start.atOffset(ZoneOffset.UTC).toInstant();
    }

    /**
     * Gets the appointment end date and time in UTC
     * @return the appointment end date and time in UTC
     */
    public Instant getEnd() 
    {
        return end.atOffset(ZoneOffset.UTC).toInstant();
    }

    /**
     * Sets the appointment end date and time in UTC
     * @param end the appointment end date and time in UTC
     */
    public void setEnd(Instant end) 
    {
        this.end = end.atOffset(ZoneOffset.UTC).toInstant();
    }

    /**
     * Gets the username of the user that created the appointment
     * @return the username of the user that created the appointment
     */
    public String getUsername() 
    {
        return username;
    }

    /**
     * Sets the username of the user that created the appointment
     * @param username the username of the user that created the appointment
     */
    public void setUsername(String username) 
    {
        this.username = username;
    }

    /**
     * Gets the company contact associated with the appointment
     * @return the company contact associated with the appointment
     */
    public String getContact() 
    {
        return contact;
    }

    /**
     * Sets the company contact of the appointment
     * @param contact the company contact of the appointment
     */
    public void setContact(String contact)
    {
        this.contact = contact;
    }

    /**
     * Gets the customer ID of the customer of the appointment
     * @return the customer ID of the customer of the appointment
     */
    public int getCustomerID() 
    {
        return customerID;
    }

    /**
     * Sets the customer ID of the customer associated with the appointment
     * @param customerID the customer ID of the customer associated with the appointment
     */
    public void setCustomerID(int customerID) 
    {
        this.customerID = customerID;
    }

    /**
     * Gets the user ID of the user that created the appointment
     * @return the user ID of the user that created the appointment
     */
    public int getUserID() 
    {
        return userID;
    }

    /**
     * Sets the user ID of the user that created the appointment
     * @param user the user ID of the user that created the appointment
     */
    public void setUserID(int user) 
    {
        this.userID = userID;
    }

    /**
     * Gets the contact ID of the company contact associated with the appointment
     * @return the contact ID of the company contact associated with the appointment
     */
    public int getContactID()
    {
        return contactID;
    }

    /**
     * Sets the contact ID of the company contact associated with the appointment
     * @param contactID the contact ID of the company contact associated with the appointment
     */
    public void setContactID(int contactID)
    {
        this.contactID = contactID;
    }

    /**
     * Gets the start time in the time zone set by the user's operating system
     * @return the start time in the time zone set by the user's operating system
     */
    public LocalTime getStartTime() 
    {
        return startTime;
    }

    /**
     * Sets the start time in the time zone set by the user's operating system
     * @param startTime the start time in the time zone set by the user's operating system
     */
    public void setStartTime(Instant startTime) 
    {
        this.startTime = LocalTime.from(start.atZone(ZoneId.systemDefault()));
    }

    /**
     * Gets the end time in the time zone set by the user's operating system
     * @return the end time in the time zone set by the user's operating system
     */
    public LocalTime getEndTime() 
    {
        return endTime;
    }

    /**
     * Sets the end time in the time zone set by the user's operating system
     * @param endTime the end time in the time zone set by the user's operating system
     */
    public void setEndTime(LocalTime endTime) 
    {
        this.endTime = endTime;
    }

    /**
     * Gets the formatted start date and time in the time zone set by the user's operating system
     * @return the formatted start date and time in the time zone set by the user's operating system
     */
    public String getStartFormatted() 
    {
        startFormatted = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm").format(start.atZone(ZoneId.systemDefault()));
        return startFormatted;
    }

    /**
     * Sets the formatted start date and time in the time zone set by the user's operating system
     * @param startFormatted the formatted start date and time in the time zone set by the user's operating system
     */
    public void setStartFormatted(String startFormatted) 
    {
        this.startFormatted = startFormatted;
    }

    /**
     * Gets the formatted end date and time in the time zone set by the user's operating system
     * @return the formatted end date and time in the time zone set by the user's operating system
     */
    public String getEndFormatted() 
    {
        endFormatted = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm").format(end.atZone(ZoneId.systemDefault()));
        return endFormatted;
    }

    /**
     * Sets the formatted end date and time in the time zone set by the user's operating system
     * @param endFormatted the formatted end date and time in the time zone set by the user's operating system
     */
    public void setEndFormatted(String endFormatted) 
    {
        this.endFormatted = endFormatted;
    }

}
