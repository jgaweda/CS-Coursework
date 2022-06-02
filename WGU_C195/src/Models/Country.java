package Models;

/**
 * Creates Country objects that represent the countries where the customers live
 */
public class Country 
{
    private int id;
    private String name;

    /**
     * Class constructor
     * @param id the country ID
     * @param name the country name
     */
    public Country(int id, String name) 
    {
        this.id = id;
        this.name = name;
    }

    /**
     * Default class constructor
     */
    public Country() 
    { }

    /**
     * Gets the country ID
     * @return the country ID
     */
    public int getID() 
    {
        return id;
    }

    /**
     * Sets country ID
     * @param id the country ID
     */
    public void setID(int id) 
    {
        this.id = id;
    }

    /**
     * Gets country name
     * @return country name
     */
    public String getName() 
    {
        return name;
    }

    /**
     * Sets  country name
     * @param name country name
     */
    public void setName(String name) 
    {
        this.name = name;
    }

}
