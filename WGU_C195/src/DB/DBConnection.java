package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Open/close db connection
 */
public class DBConnection 
{
    private static Connection dbcon;
    private static final String DB_NAME = "WJ06uqW";
    private static final String USER_NAME = "U06uqW";
    private static final String DB_PASS = "53688875766";
    private static final String DB_URL = "jdbc:mysql://wgudb.ucertify.com/" + DB_NAME;
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver"; 

    // Returns database connection
    public static Connection ConnectionObject() 
    {
        return dbcon;
    }
    
    // Connect to db
    public static void Connect() 
    {
        try
        {
            Class.forName(DB_DRIVER);
            dbcon = DriverManager.getConnection(DB_URL, USER_NAME, DB_PASS);
            System.out.println("Connected to DB");
        } 
        catch(SQLException exception) 
        {
            System.out.println("SQL Exception: " + exception.getMessage() + " and SQL State: " + exception.getSQLState()); 
            System.out.println("Error Code: " + exception.getErrorCode());
        }
        catch(ClassNotFoundException exception) 
        {
            System.out.println(exception.getMessage());
        } 
    }
    
    // Close db connection
    public static void Disconnect() 
    {
        try 
        {
            dbcon.close();
            System.out.println("Disconnected from DB");
        } 
        catch (SQLException exception) 
        {
            System.out.println("SQL Exception: " + exception.getMessage());
        }
    }
}
