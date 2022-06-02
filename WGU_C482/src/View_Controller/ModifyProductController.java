package View_Controller;

import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import static View_Controller.MainScreenController.getModifiedProduct;
import java.net.URL;
import java.util.Optional;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.ValidationException;

public class ModifyProductController implements Initializable 
{
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {  
        productIdBox.setDisable(true);
        productIdBox.setText(Integer.toString(currentProductMod.getProductId()));
        productNameBox.setText(currentProductMod.getProductName());
        productInventoryBox.setText(Integer.toString(currentProductMod.getStock()));
        productPriceBox.setText(Double.toString(currentProductMod.getProductPrice()));
        productMinBox.setText(Integer.toString(currentProductMod.getMin()));
        productMaxBox.setText(Integer.toString(currentProductMod.getMax()));

        productParts = currentProductMod.getAllAssociatedParts();
        
        
        addPartPartIDColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPartId()).asObject());
        addPartNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        addPartInventoryColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInStock()).asObject());
        addPartPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        
        currentProductPartsContainedPartIDColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPartId()).asObject());
        currentProductPartsContainedPartNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        currentProductPartsContainedInventoryColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInStock()).asObject());
        currentProductPartsContainedPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

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
    private ObservableList<Part> productParts = FXCollections.observableArrayList();
    private final Product currentProductMod;
    
    public ModifyProductController() 
    {
        this.currentProductMod = getModifiedProduct();
    }
    
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
        alert.setTitle("CANCEL PRODUCT MODIFICATION");
        alert.setHeaderText("Please confirm.");
        alert.setContentText("Please confirm you want to cancel the update to the product " + productNameBox.getText() + ".");
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
        populateCurrentPartsTable();
        populateAvailablePartsTable();

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
        
        Product newProduct = new Product();
        newProduct.setProductName(productName);
        newProduct.setProductPrice(Double.parseDouble(productPrice));
        newProduct.setStock(Integer.parseInt(productInventory));
        newProduct.setMin(Integer.parseInt(productMin));
        newProduct.setMax(Integer.parseInt(productMax));
        
        if (currentProductMod != null) 
        {
            currentProductMod.deleteAllAssociatedParts();
        }
        
        productParts.forEach((i) -> {
            newProduct.addAssociatedParts(i);
        });
        
        try {
            newProduct.isValid();
        
        
            if (currentProductMod == null) 
            {
                newProduct.setProductId(Inventory.getProductCount());
                Inventory.addProduct(newProduct);
            }
            else 
            {
                newProduct.setProductId(currentProductMod.getProductId());
                Inventory.updateProduct(newProduct);
            }
            
            Parent loader = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(loader);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            
        }
            catch (ValidationException i) 
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR");
                alert.setHeaderText("Product not valid");
                alert.setContentText(i.getMessage());
                alert.showAndWait();
        }   
    }

    @FXML
    void searchPartsButtonHandler (ActionEvent event) throws IOException {
        String partSearchIDString = searchPartsBox.getText();
        Part searchedPart = Inventory.lookupPart(Integer.parseInt(partSearchIDString));
        
        if (searchedPart != null) {
            ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
            filteredPartsList.add(searchedPart);
            addPartTable.setItems(filteredPartsList);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("Part not found.");
            alert.setContentText("The part searched for does not match any part in Inventory!");
            alert.showAndWait();
        }
    }
}

