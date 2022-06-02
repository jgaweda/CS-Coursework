package View_Controller;

import Model.Inhouse;
import Model.Outsourced;
import Model.Part;
import static Model.Inventory.addPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;        
import javafx.scene.control.RadioButton;


public class AddPartController implements Initializable 
{
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        partIdBox.setText("Auto Gen - Disabled");
        partIdBox.setDisable(true);
        inHousePartSelect.setSelected(true);
        outsourcedPartSelect.setSelected(false);
        isInHouse = true;
        partSourceBoxLabel.setText("Machine ID");
    } 

    @FXML
    private Label partSourceBoxLabel;

    @FXML
    private TextField partIdBox;

    @FXML
    private TextField partNameBox;

    @FXML
    private TextField partInventoryBox;

    @FXML
    private TextField partPriceBox;

    @FXML
    private TextField partMinBox;

    @FXML
    private TextField partMaxBox;

    @FXML
    private TextField partSourceBox;
    
    @FXML
    private RadioButton inHousePartSelect;

    @FXML
    private RadioButton outsourcedPartSelect;    
    
    private boolean isInHouse;             
  
    @FXML
    void inHousePartSelectHandler(ActionEvent event) 
    {
        inHousePartSelect.setSelected(true);
        outsourcedPartSelect.setSelected(false);
        isInHouse = true;
        partSourceBoxLabel.setText("Machine ID");
    }
    
    @FXML
    void outsourcedPartSelectHandler(ActionEvent event) 
    {
        outsourcedPartSelect.setSelected(true);
        inHousePartSelect.setSelected(false);
        isInHouse = false;
        partSourceBoxLabel.setText("Company Name");
    }
    
    @FXML
    void partCancelHandler(ActionEvent event) throws IOException 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("CANCEL PART MODIFICATION");
        alert.setHeaderText("Confirm cancel");
        alert.setContentText("Please confirm that you want to cancel adding or modifying " + partNameBox.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) 
        {
            Parent loader = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(loader);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }
    
    @FXML
    void addPartSaveHandler(ActionEvent event) throws IOException 
    {
        String partName = partNameBox.getText();
        String partInv = partInventoryBox.getText();
        String partPrice = partPriceBox.getText();
        String partMin = partMinBox.getText();
        String partMax = partMaxBox.getText();
        String partSource = partSourceBox.getText();
        
        int newPartID = 1;
        for(Part i: Model.Inventory.getPartInventory()) 
        {
            if (i.getPartId() >= newPartID) 
            {
                newPartID = i.getPartId() + 1;
            }
        }
        
        if ("".equals(partInv)) 
        {
            partInv = "0";
        }
        
           if (isInHouse) 
           {
                Inhouse newPart = new Inhouse();
                newPart.setPartId(newPartID);
                newPart.setName(partName);
                newPart.setPrice(Double.parseDouble(partPrice));
                newPart.setInStock(Integer.parseInt(partInv));
                newPart.setMin(Integer.parseInt(partMin));
                newPart.setMax(Integer.parseInt(partMax));
                newPart.setMachineId(Integer.parseInt(partSource));
                try 
                {
                     newPart.isValid();

                     if (newPart.isValid() == true) 
                     {
                        addPart(newPart);
                     }

                     Parent loader = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                     Scene scene = new Scene(loader);
                     Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                     window.setScene(scene);
                     window.show();

                 } 
                 catch (ValidationException e) 
                 {
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle("ERROR!");
                     alert.setHeaderText("Invalid Part");
                     alert.setContentText(e.getMessage());
                     alert.showAndWait();
                 }  
            } 
            else
            {
                Outsourced newPart = new Outsourced();
                newPart.setPartId(newPartID);
                newPart.setName(partName);
                newPart.setPrice(Double.parseDouble(partPrice));
                newPart.setInStock(Integer.parseInt(partInv));
                newPart.setMin(Integer.parseInt(partMin));
                newPart.setMax(Integer.parseInt(partMax));
                newPart.setCompanyName(partSource);

                try {
                    newPart.isValid();
                    if (newPart.isValid() == true) {
                        addPart(newPart);
                    }

                       Parent loader = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                 Scene scene = new Scene(loader);
                 Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                 window.setScene(scene);
                 window.show();


                }
                catch (ValidationException exception) 
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("ERROR!");
                    alert.setHeaderText("Invalid Part");
                    alert.setContentText(exception.getMessage());
                    alert.showAndWait();
                 }  
        
            }
        }           
}
