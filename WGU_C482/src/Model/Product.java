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
import javax.xml.bind.ValidationException;

public class Product 
{
    int productId;
    private String productName;
    private double productPrice;
    private int productStock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();;
 
    
    public void setProductId(int productId)
    {
        this.productId = productId;
    }
  
    public int getProductId() 
    {
        return productId;
    }
    
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }    
    
    public String getProductName() 
    {
        return productName;
    }
      
    public void setStock(int inStock) 
    {
        this.productStock = inStock;
    }
    
    public int getStock()  
    {
        return productStock;
    }
       
    public void setMin(int min)
    {
        this.min = min;
    }
    
    public int getMin() 
    {
        return min;
    }

    public void setMax(int max)
    {
        this.max = max;
    }    
    
    public int getMax()
    {
        return max;
    }
    
    public void setProductPrice(double productPrice) 
    {
        this.productPrice = productPrice;
    }    
    
    public double getProductPrice() 
    {
        return productPrice;
    }
    
    public ObservableList<Part> getAllAssociatedParts() 
    {
        return associatedParts;
    }
    
    public int getAssociatedPartsCount()
    {
        return associatedParts.size();
    }
    
    public void addAssociatedParts(Part associatedParts)
    {
        this.associatedParts.add(associatedParts);
    }
    
    
    public Part lookupAssociatedParts(int partID) 
    {
        for (Part i: associatedParts) {
            if (i.getPartId() == partID) {
                return i;
            }
        }
            return null;
    }
    
    public void deleteAllAssociatedParts() 
    {
        associatedParts = FXCollections.observableArrayList();
    }
    
    public boolean deleteAssociatedParts(int partID) 
    {
        for (Part i: associatedParts) {
            if (i.getPartId() == partID) {
                associatedParts.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public boolean isValid() throws ValidationException 
    {
        
        double totalPriceOfParts = 0.00;
        
        for(Part i : getAllAssociatedParts()) 
        {
            totalPriceOfParts += i.getPrice();
        }
        
        if (getProductPrice() < 0) 
        {
            throw new ValidationException("Please enter a valid price. Price must be greater than $0.");
        }        
        
        if (totalPriceOfParts > getProductPrice()) 
        {
            throw new ValidationException("Product price must be greater than total cost of all parts assigned to the product.");
        }
        
        if (getProductName().equals("")) 
        {
            throw new ValidationException("Product name is required."); 
        }
        
        if (getStock() < 0) 
        {
            throw new ValidationException("Please enter a valid amount. Inventory must be greater than 0.");
        }
        
        if (getStock() < getMin() || getStock() > getMax()) 
        {
            throw new ValidationException("Current inventory must be between the minimum and maximum inventory level.");
        }        
        
        if (getMin() < 0)
        {
            throw new ValidationException("Please enter a valid amount. Minimum inventory must be greater than zero.");
        }
        
        if (getMin() > getMax()) 
        {
            throw new ValidationException("Please enter a valid minimum inventory level. Minimum inventory cannot exceed maximum.");
        }
         
        return true;   
    }
     
    
}
