package Models;

/**
 * Creates Count objects used to count the number of appointments associated with each type and month
 */
public class Count 
{

    private String type;
    private int count;
    private String month;

    /**
     * Class constructor for counting appointments
     * @param type the appointment type
     * @param count the number of appointments associated with the Type
     */
    public Count(String type, int count) 
    {
        this.type = type;
        this.count = count;
        month = "";
    }

    /**
     * Class constructor used when counting appointments associated with each month
     * @param count the number of appointments that begin in the month
     * @param month the month
     */
    public Count(int count, String month)
    {
        type = "";
        this.count = count;
        this.month = month;
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
     * Gets the number of appointments associated with the type or month
     * @return the number of appointments associated with the type or month
     */
    public int getCount()
    {
        return count;
    }

    /**
     * Sets the number of appointments associated with the type or month
     * @param count the number of appointments associated with the type or month
     */
    public void setCount(int count) 
    {
        this.count = count;
    }

    /**
     * Gets the month
     * @return the month
     */
    public String getMonth() 
    {
        return month;
    }

    /**
     * Sets the month
     * @param month the month
     */
    public void setMonth(String month)
    {
        this.month = month;
    }

}
