/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author gaweda
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory 
{
    
private final static ObservableList<Part> partInventory = FXCollections.observableArrayList();
private final static ObservableList<Product> productInventory = FXCollections.observableArrayList();

    public static void addPart(Part part)
    {
        partInventory.add(part);
    }
    
    public static boolean deletePart(int PartId) 
    {
        for (Part i: partInventory) {
            if (i.getPartId() == PartId) {
                partInventory.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public static ObservableList<Part> getPartInventory() 
    {
        return partInventory;
    }
    
    public static int getPartsCount() 
    {
        return partInventory.size();
    }
    
   public static void updatePart(Part updatedPart) 
   {
        for (int i = 0; i < partInventory.size(); i++) 
        {
            if (partInventory.get(i).getPartId() == updatedPart.partId) 
            {
                partInventory.set(i, updatedPart);
                break;
            }
        }
    }
    
    public static Part lookupPart (int partId) 
    {
        for (Part i: partInventory) 
        {
            if (i.getPartId() == partId) 
            {
                return i;
            }
        }
        return null;
    }

    public static void addProduct(Product newProduct) 
    {
        productInventory.add(newProduct);
    }
    
    public static boolean canDeleteProduct(Product product) 
    {
        return product.getAssociatedPartsCount() == 0;
    }
    
    public static boolean deleteProduct(int productId)
    {
            for (Product i: productInventory)
            {
                if (i.getProductId() == productId) 
                {
                    productInventory.remove(i);
                    return true;
                }
            }
            return false;
    }
    
    public static ObservableList<Product> getAllProducts()
    {
        return productInventory;
    }
    
    public static int getProductCount()
    {
        return productInventory.size();
    }
    
    public static void updateProduct(Product updatedProduct) 
    {
        for (int i = 0; i < productInventory.size(); i++)
        {
            if (productInventory.get(i).getProductId() == updatedProduct.productId) 
            {
                productInventory.set(i, updatedProduct);
           break;
            }
        }
    }
    
    public static Product lookupProduct(int productId) 
    {
        for (Product i: productInventory) 
        {
            if (i.getProductId() == productId) 
            {
                return i;
            }
        }
        return null;
    }
    

    
    
    
}
