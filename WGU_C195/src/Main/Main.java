package Main;

import DB.*;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * WGU C195
 * @author Josh Gaweda
 */
public class Main extends Application 
{
    /**
     * Opens/closes the db connection
     * @param args launch arguments
     */
    public static void main(String[] args) 
    {
        DBConnection.Connect();
        launch(args);
        DBConnection.Disconnect();
    }
    
    /**
     * Loads db and displays the login window
     * @param mainStage identifies Main stage
     * @throws java.lang.Exception on start
     */
    @Override
    public void start(Stage mainStage) throws Exception 
    {
        DBQuery data = new DBQuery();
        data.read_DB_Data();
        ResourceBundle rb = ResourceBundle.getBundle("Properties/language", Locale.getDefault());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controllers/Login.fxml"));
        View_Controllers.LoginController controller = new View_Controllers.LoginController(data);
        loader.setController(controller);
        loader.setResources(rb);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }
}
