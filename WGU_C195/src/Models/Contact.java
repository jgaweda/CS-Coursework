package Models;

/**
 * Creates Contact objects that represent contacts in the db
 */
public class Contact 
{

    private int id;
    private String name;
    private String email;

    /**
     * Class constructor
     * @param id the contact ID
     * @param name the contact name
     * @param email the contact email address
     */
    public Contact(int id, String name, String email)
    {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Default class constructor
     */
    public Contact() 
    { }

    /**
     * Gets the contact ID
     * @return the contact ID
     */
    public int getID() 
    {
        return id;
    }

    /**
     * Sets the contact ID
     * @param id the contact ID
     */
    public void setID(int id) 
    {
        this.id = id;
    }

    /**
     * Gets the contact name
     * @return the contact name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the contact name
     * @param name the contact name
     */
    public void setName(String name) 
    {
        this.name = name;
    }

    /**
     * Gets the contact email address
     * @return the contact email address
     */
    public String getEmail() 
    {
        return email;
    }

    /**
     * Sets the contact email address
     * @param email the contact email address
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

}
