package View_Controller;

import Model.Inhouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import static View_Controller.MainScreenController.getModifyPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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

public class ModifyPartController implements Initializable 
{
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {

        partIdBox.setDisable(true);
        partIdBox.setText(Integer.toString(modifyPart.getPartId()));
        partNameBox.setText(modifyPart.getName());
        partInventoryBox.setText(Integer.toString(modifyPart.getInStock()));
        partPriceBox.setText(Double.toString(modifyPart.getPrice()));
        partMinBox.setText(Integer.toString(modifyPart.getMin()));
        partMaxBox.setText(Integer.toString(modifyPart.getMax()));

        if (modifyPart instanceof Inhouse) 
        {
            partSourceBox.setText(Integer.toString(((Inhouse) modifyPart).getMachineId()));

            partSourceBoxLabel.setText("Machine ID");
            inHousePartSelect.setSelected(true);

        }
        else 
        {
            partSourceBox.setText(((Outsourced) modifyPart).getCompanyName());
            partSourceBoxLabel.setText("Company Name");
            outsourcedPartSelect.setSelected(true);
        }
    }    

    @FXML
    private RadioButton inHousePartSelect;

    @FXML
    private RadioButton outsourcedPartSelect;

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

    private boolean isInHouse;   
    
    private final Part modifyPart;
    
    public ModifyPartController() 
    {
      this.modifyPart = getModifyPart();
    }
    
    @FXML
    void inHousePartSelectHandler(ActionEvent event) 
    {
        isInHouse = true;
        partSourceBoxLabel.setText("Machine ID");
    }
    
    @FXML
    void outsourcedPartSelectHandler(ActionEvent event) {
        isInHouse = false;
        partSourceBoxLabel.setText("Company Name");
   }

    @FXML
    void partCancelHandler(ActionEvent event) throws IOException 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel Part Modification");
        alert.setHeaderText("Confirm cancellation");
        alert.setContentText("Please confirm that you want to cancel modifying part " + partNameBox.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            Parent loader = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(loader);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
        
    }
  
    @FXML
    void PartModSaveHandler(ActionEvent event) throws IOException 
    {
        String partID    = partIdBox.getText();
        String partName  = partNameBox.getText();
        String partInv   = partInventoryBox.getText();
        String partPrice = partPriceBox.getText();
        String partMin   = partMinBox.getText();
        String partMax   = partMaxBox.getText();
        String partFlex  = partSourceBox.getText();
        
        if ("".equals(partInv)) 
        {
            partInv = "0";
        }
        
           if (isInHouse) 
           {
                Inhouse modifyPart = new Inhouse();
                modifyPart.setPartId(Integer.parseInt(partID));
                modifyPart.setName(partName);
                modifyPart.setPrice(Double.parseDouble(partPrice));
                modifyPart.setInStock(Integer.parseInt(partInv));
                modifyPart.setMin(Integer.parseInt(partMin));
                modifyPart.setMax(Integer.parseInt(partMax));
                modifyPart.setMachineId(Integer.parseInt(partFlex));

                try {
                    modifyPart.isValid();

                    if (modifyPart.isValid() == true) {
                         Inventory.updatePart(modifyPart);                 
                    }

                       Parent loader = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                 Scene scene = new Scene(loader);
                 Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                 window.setScene(scene);
                 window.show();

                 } catch (ValidationException exception) {
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle("Error!");
                     alert.setHeaderText("Part not valid");
                     alert.setContentText(exception.getMessage());
                     alert.showAndWait();
                     }  
            } 
            else 
           {
               Outsourced modifyPart = new Outsourced();
               modifyPart.setPartId(Integer.parseInt(partID));
               modifyPart.setName(partName);
               modifyPart.setPrice(Double.parseDouble(partPrice));
               modifyPart.setInStock(Integer.parseInt(partInv));
               modifyPart.setMin(Integer.parseInt(partMin));
               modifyPart.setMax(Integer.parseInt(partMax));
               modifyPart.setCompanyName(partFlex);

               try {
                   modifyPart.isValid();
                   if (modifyPart.isValid() == true) {
                       Inventory.updatePart(modifyPart);

                   }

                Parent loader = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(loader);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();

               } catch (ValidationException exception) {
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("Error!");
                   alert.setHeaderText("Part not valid");
                   alert.setContentText(exception.getMessage());
                   alert.showAndWait();
                   }  

            }
       
    }
}
    

