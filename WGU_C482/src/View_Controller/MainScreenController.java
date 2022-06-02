package View_Controller;


import Inventory.Main;
import Model.Part;
import Model.Product;
import static Model.Inventory.getAllProducts;
import static Model.Inventory.canDeleteProduct;
import static Model.Inventory.deletePart;
import static Model.Inventory.deleteProduct;
import static Model.Inventory.getPartInventory;
import java.io.IOException;
import java.util.Optional;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class MainScreenController implements Initializable 
{

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    { 
        setModifyPart(null);
        setModifiedProduct(null);
        refreshPartsTable();
        refreshProductsTable();  
        
        mainWindowPartIDColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPartId()).asObject());
        mainWindowPartNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        mainWindowPartInventoryColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInStock()).asObject());
        mainWindowPartPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        
        mainWindowProductIDColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProductId()).asObject());
        mainWindowProductNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));
        mainWindowProductInventoryColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        mainWindowProductPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProductPrice()).asObject()); 
    }        
    
    @FXML
    private TableView<Part> mainWindowPartsTable;

    @FXML
    private TableColumn<Part, Integer> mainWindowPartIDColumn;

    @FXML
    private TableColumn<Part, String> mainWindowPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> mainWindowPartInventoryColumn;

    @FXML
    private TableColumn<Part, Double> mainWindowPartPriceColumn;

    @FXML
    private TextField partsSearchBox;

    @FXML
    private TableView<Product> mainWindowProductTable;

    @FXML
    private TableColumn<Product, Integer> mainWindowProductIDColumn;

    @FXML
    private TableColumn<Product, String> mainWindowProductNameColumn;

    @FXML
    private TableColumn<Product, Integer> mainWindowProductInventoryColumn;

    @FXML
    private TableColumn<Product, Double> mainWindowProductPriceColumn;

    @FXML
    private TextField productSearchBox;
   
    private static Part currentPartMod;
    
    private static Product currentProductMod;

    @FXML
    void addPartMainHandler(ActionEvent event) throws IOException  {
        openAddPartWindow(event);
    }
    
      @FXML
    void partModMainHandler(ActionEvent event) throws IOException  {
        openModifyPartWindow(event);
    }

    @FXML
    void addProductMainHandler(ActionEvent event) throws IOException  {
        openAddProductWindow(event);
    }
    
    @FXML
    void deletePartHandler(ActionEvent event) throws IOException  
    {
        Part part = mainWindowPartsTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("DELETE PART");
        alert.setHeaderText("Please Confirm");
        alert.setContentText("Are you sure you want to delete " + part.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            deletePart(part.getPartId());
            refreshPartsTable();
    }
}
    
    @FXML
    void deleteProductHandler(ActionEvent event) throws IOException  
    {
        Product product = mainWindowProductTable.getSelectionModel().getSelectedItem();
        if (!canDeleteProduct(product)) 
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERROR!");
            alert.setHeaderText("This product cannot be removed");
            alert.setContentText("This product has parts associated with it. Please disassociate those parts and then try again.");
            alert.showAndWait();
        }
        else 
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("DELETING PRODUCT");
            alert.setHeaderText("Please confirm deletion.");
            alert.setContentText("Are you sure you want to delete " + product.getProductName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) 
            {
                deleteProduct(product.getProductId());
                refreshPartsTable();
            }
        }
    }
    
    @FXML
    void exitApplicationHandler(ActionEvent event) throws IOException  
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("EXIT CONFIRMATION");
        alert.setHeaderText("Please confirm that you want to exit!");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) 
        {
            System.exit(0);
        }        
    }

    @FXML
    void modifyPartMainHandler(ActionEvent event) throws IOException  
    {
        currentPartMod = mainWindowPartsTable.getSelectionModel().getSelectedItem();
        setModifyPart(currentPartMod);
        openModifyPartWindow(event);
    }

    @FXML
    void mainModifyProductHandler(ActionEvent event) throws IOException  
    {
        currentProductMod = mainWindowProductTable.getSelectionModel().getSelectedItem();
        setModifiedProduct(currentProductMod);
        openModifyProductWindow(event);
            
    }

    @FXML
    void searchPartsHandler(ActionEvent event) throws IOException  
    {
        Model.Inventory.getPartInventory().stream().filter((i) -> (i.getName().equalsIgnoreCase(partsSearchBox.getText()))).forEachOrdered((i) -> {
            mainWindowPartsTable.getSelectionModel().select(i);
        });
    }

    @FXML
    void searchProductsHandler(ActionEvent event) throws IOException 
    {
        Model.Inventory.getAllProducts().stream().filter((i) -> (i.getProductName().equalsIgnoreCase(productSearchBox.getText()))).forEachOrdered((i) -> {
            mainWindowProductTable.getSelectionModel().select(i);
        });
    }
        
    public void openAddPartWindow(ActionEvent event) throws IOException 
    {
        Parent loader = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene scene = new Scene(loader);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
     
    public void openModifyPartWindow(ActionEvent event) throws IOException 
    {
        if (currentPartMod != null)
        {
            Parent loader = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
            Scene scene = new Scene(loader);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("NO PART SELECTED");
                   alert.setHeaderText("Please select a Part to Modify");
                   alert.setContentText("Click okay to return to main window.");
                   alert.showAndWait();
        }  
    }
    
    public void openAddProductWindow(ActionEvent event) throws IOException 
    {
        Parent loader = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene scene = new Scene(loader);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
     
    public void openModifyProductWindow(ActionEvent event) throws IOException 
    {
         if (currentProductMod != null)
         {
              Parent loader = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
              Scene scene = new Scene(loader);
              Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
              window.setScene(scene);
              window.show();
          }
          else    
          {
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle("NO PRODUCT SELECTED");
                     alert.setHeaderText("Please select a Product to Modify");
                     alert.setContentText("Click okay to return to the main window.");
                     alert.showAndWait();
          }  
     }
    
    public static Part getModifyPart()
    {
        return currentPartMod;
    }

    public void setModifyPart(Part modifyPart) 
    {
        View_Controller.MainScreenController.currentPartMod = modifyPart;
    }
    
    public static Product getModifiedProduct()  
    {
        return currentProductMod;
    }
  
    public void setModifiedProduct(Product modifiedProduct) 
    {
        MainScreenController.currentProductMod = modifiedProduct;
    }
    
    public void refreshPartsTable()
    {
        mainWindowPartsTable.setItems(getPartInventory());
    }
   
    public void refreshProductsTable()
    {
        mainWindowProductTable.setItems(getAllProducts());
    }
    
    public void setApp(Main mainApp)
    {
        refreshPartsTable();
        refreshProductsTable();
    }
}
    

