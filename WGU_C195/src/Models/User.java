package Models;

/**
 * Creates User objects that represent the users in the db
 */
public class User 
{

    private int id;
    private String username;
    private String password;

    /**
     * Class constructor
     * @param id the user ID
     * @param username the username
     * @param password the password
     */
    public User(int id, String username, String password)
    {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Default class constructor
     */
    public User() 
    {}

    /**
     * Gets the user ID
     * @return the user ID
     */
    public int getUser_ID()
    {
        return id;
    }

    /**
     * Sets user ID
     * @param id the user ID
     */
    public void setUser_ID(int id) 
    {
        this.id = id;
    }

    /**
     * Gets username
     * @return username
     */
    public String getUsername() 
    {
        return username;
    }

    /**
     * Sets users name
     * @param username username
     */
    public void setUsername(String username) 
    {
        this.username = username;
    }

    /**
     * Gets password
     * @return password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets the users password
     * @param password the user password
     */
    public void setPassword(String password) 
    {
        this.password = password;
    }
}
