package Models;

/**
 * Creates Customer objects that represent customers in the db
 */
public class Customer 
{
    private int customerID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String division;
    private String country;
    private int divisionID;

    
    /**
     * Class constructor
     * @param customerID the customer ID
     * @param name the customer's name
     * @param address the customer's address
     * @param postalCode the customer's postal code
     * @param phone the customer's phone number
     * @param division the first level division where the customer lives
     * @param country the country where the customer lives
     * @param divisionID the division ID
     */
    
    public Customer(int customerID, String name, String address, String postalCode, String phone, String division, String country, int divisionID) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.country = country;
        this.divisionID = divisionID;
    }

    
    /**
     * Default class constructor
     */
    public Customer() 
    {}

    
    /**
     * Gets the customer ID
     * @return the customer ID
     */
    public int getCustomerID() 
    {
        return customerID;
    }

    /**
     * Sets the customer ID
     * @param customerID the customer ID
     */
    public void setCustomerID(int customerID) 
    {
        this.customerID = customerID;
    }

    /**
     * Gets the customer's name
     * @return the customer's name
     */
    public String getName() 
    {
        return name;
    }

    /**
     * Sets the customer's name
     * @param name the customer's name
     */
    public void setName(String name) 
    {
        this.name = name;
    }

    /**
     * Gets the customer's address
     * @return the customer's address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Sets the customer's address
     * @param address the customer's address
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * Gets the customer's postal code
     * @return the customer's postal code
     */
    public String getPostalCode() 
    {
        return postalCode;
    }

    /**
     * Sets the customer's postal code
     * @param postalCode the customer's postal code
     */
    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    /**
     * Gets the customer's phone number
     * @return the customer's phone number
     */
    public String getPhone() 
    {
        return phone;
    }

    /**
     * Sets the customer's phone number
     * @param phone the customer's phone number
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * Gets the first level division where the customer lives
     * @return the first level division where the customer lives
     */
    public String getDivision() 
    {
        return division;
    }

    /**
     * Sets the first level division where the customer lives
     * @param division the first level division where the customer lives
     */
    public void setDivision(String division) 
    {
        this.division = division;
    }

    /**
     * Gets the country where the customer lives
     * @return the country where the customer lives
     */
    public String getCountry() 
    {
        return country;
    }

    /**
     * Sets the country where the customer lives
     * @param country the country where the customer lives
     */
    public void setCountry(String country) 
    {
        this.country = country;
    }

    /**
     * Gets the division ID
     * @return the division ID
     */
    public int getDivisionID() 
    {
        return divisionID;
    }

    /**
     * Sets the division ID
     * @param divisionID the division ID
     */
    public void setDivisionID(int divisionID) 
    {
        this.divisionID = divisionID;
    }

}
