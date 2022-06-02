package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.ValidationException;

public class AddProductController implements Initializable 
{
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        productIdBox.setText("Auto Gen - Disabled");
        productIdBox.setDisable(true);
        addPartPartIDColumn.setCellValueFactory(new PropertyValueFactory<Part,Integer>("partID"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
        addPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part,Integer>("inStock"));
        addPartPriceColumn.setCellValueFactory(new PropertyValueFactory<Part,Double>("price"));
        
        currentProductPartsContainedPartIDColumn.setCellValueFactory(new PropertyValueFactory<Part,Integer>("partID"));
        currentProductPartsContainedPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        currentProductPartsContainedInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("inStock"));
        currentProductPartsContainedPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        populateAvailablePartsTable();
        populateCurrentPartsTable();
    } 

    @FXML
    private TextField productIdBox;

    @FXML
    private TextField productNameBox;

    @FXML
    private TextField productInventoryBox;

    @FXML
    private TextField productPriceBox;

    @FXML
    private TextField productMinBox;

    @FXML
    private TextField productMaxBox;

    @FXML
    private TableView<Part> addPartTable;

    @FXML
    private TableColumn<Part, Integer> addPartPartIDColumn;

    @FXML
    private TableColumn<Part, String> addPartNameColumn;

    @FXML
    private TableColumn<Part,Integer> addPartInventoryColumn;

    @FXML
    private TableColumn<Part, Double> addPartPriceColumn;

    @FXML
    private TableView<Part> currentProductPartsContainedTable;

    @FXML
    private TableColumn<Part, Integer> currentProductPartsContainedPartIDColumn;

    @FXML
    private TableColumn<Part, String> currentProductPartsContainedPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> currentProductPartsContainedInventoryColumn;

    @FXML
    private TableColumn<Part, Double> currentProductPartsContainedPriceColumn;

    @FXML
    private TextField searchPartsBox;
    
    private final ObservableList<Part> productParts = FXCollections.observableArrayList();
    
    public void populateAvailablePartsTable() 
    {
         addPartTable.setItems(Model.Inventory.getPartInventory());
    }
    
    public void populateCurrentPartsTable() 
    {
    currentProductPartsContainedTable.setItems(productParts);
    }
    
    @FXML
    void addPartToProductHandler(ActionEvent event) throws IOException 
    {
        Part part = addPartTable.getSelectionModel().getSelectedItem();
        productParts.add(part);
        populateCurrentPartsTable();
    }
    

    @FXML
    void cancelProductHandler(ActionEvent event) throws IOException 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("CANCEL PRODUCT ADD");
        alert.setHeaderText("Please confirm");
        alert.setContentText("Are you sure you want to cancel adding " + productNameBox.getText() + "?");
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
    void deletePartFromProductHandler(ActionEvent event) throws IOException 
    {
        Part part = currentProductPartsContainedTable.getSelectionModel().getSelectedItem();
        productParts.remove(part);
    }

    @FXML
    void saveProductHandler(ActionEvent event) throws IOException, ValidationException 
    {
        String productName = productNameBox.getText();
        String productInventory = productInventoryBox.getText();
        String productPrice = productPriceBox.getText();
        String productMin = productMinBox.getText();
        String productMax = productMaxBox.getText();
       
        if ("".equals(productInventory)) 
        {
            productInventory = "0";
        }
        
        int newProductID = 1;
        for(Product i: Model.Inventory.getAllProducts()) 
        {
            if (i.getProductId() >= newProductID) {
                newProductID = i.getProductId() + 1;
            }
        } 
         
        Product newProduct = new Product();
        newProduct.setProductId(newProductID);
        newProduct.setProductName(productName);
        newProduct.setProductPrice(Double.parseDouble(productPrice));
        newProduct.setStock(Integer.parseInt(productInventory));
        newProduct.setMin(Integer.parseInt(productMin));
        newProduct.setMax(Integer.parseInt(productMax));
   
        productParts.forEach((i) -> { 
            newProduct.addAssociatedParts(i);
        });
         
        try {
            newProduct.isValid();
            
           Inventory.addProduct(newProduct);

            Parent loader = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(loader);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            
        }
            catch (ValidationException i) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR!");
                alert.setHeaderText("Product not valid");
                alert.setContentText(i.getMessage());
                alert.showAndWait();
                    }   
    }

       
    @FXML
    void searchPartsButtonHandler (ActionEvent event) throws IOException 
    {
        String partsSearchIDString = searchPartsBox.getText();
        Part searchedPart = Inventory.lookupPart(Integer.parseInt(partsSearchIDString));
        
        if (searchedPart != null) {
            ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
            filteredPartsList.add(searchedPart);
            addPartTable.setItems(filteredPartsList);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SEARCH ERROR");
            alert.setHeaderText("Part not found in inventory.");
            alert.setContentText("The part searched for does not match any current part in the Inventory!");
            alert.showAndWait();
        }
    }


    
}

