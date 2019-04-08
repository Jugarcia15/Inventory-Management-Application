/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

/**
 *
 * @author Juan G
 */
public class Inventory {
    private static ObservableList<Part> partsInv = FXCollections.observableArrayList();
    private static ObservableList<Product> productsInv = FXCollections.observableArrayList();
    private static int partIDCT = 0;
    private static int productIDCT = 0;

    public Inventory(){
        
    }
    
    //is input integer
    public static boolean isInt(String input)
    {
        try{
            Integer.parseInt(input);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    //partsInv Observable List with add, delete, update
//•  redirect the user to the “Add Part”, “Modify Part”, “Add Product”, or “Modify Product” screens
//•  delete a selected part or product from the list
        
    public static ObservableList<Part> getPartsInv() 
    {
        return partsInv;
    }
    
    public static void addPart(Part part) 
    {
        partsInv.add(part);
    }
    
    public static void updatePart(int ind, Part part)
    {
        partsInv.set(ind, part);
    }
    
    public static void removePart(Part part)
    {
        partsInv.remove(part);
    }
    
    public static int getPartIDCT() 
    {
        partIDCT++;
        return partIDCT;
    }
    
    
    //validate deletes
    //•  including a confirm dialogue for all “Delete” and “Cancel” buttons
    public static boolean validatePartDelete(Part part)
    {
        boolean found = false;
        for(int i=0;i<productsInv.size();i++)
        {
            if(productsInv.get(i).getProductParts().contains(part))
            {
                found=true;
            }
        }
        return found;
    }
    
    //search for a part or product and display matching results
    public static int SearchPart(String searchField)
    {
        boolean found = false;
        int ind = 0;
        if(isInt(searchField))
        {
            for(int i = 0; i < partsInv.size();i++)
            {
                if(Integer.parseInt(searchField) == partsInv.get(i).getPartID())
                {
                    ind = i;
                    found = true;
                }
            }
        }
        else
        {
            for(int i=0;i<partsInv.size();i++)
            {
                if(searchField.equals(partsInv.get(i).getPartName()))
                {
                    ind=i;
                    found=true;
                }
            }
        }
        if(found=true)
        {
            return ind;
        }
        else
        {
            System.out.println("No parts found.");
            return -1;
        }

    }
    
    public static int SearchProduct(String searchField)
    {
        boolean found = false;
        int ind = 0;
        if(isInt(searchField))
        {
            for(int i = 0; i < productsInv.size();i++)
            {
                if(Integer.parseInt(searchField) == productsInv.get(i).getprodID())
                {
                    ind = i;
                    found = true;
                }
            }
        }
        else
        {
            for(int i=0;i<productsInv.size();i++)
            {
                if(searchField.equals(productsInv.get(i).getprodName()))
                {
                    ind=i;
                    found=true;
                }
            }
        }
        if(found = true)
        {
            return ind;
        }
        else
        {
            System.out.println("No products found.");
            return -1;
        }
    }
    
    //ProductsInv Observable List with add, delete, update
    //•  redirect the user to the “Add Part”, “Modify Part”, “Add Product”, or “Modify Product” screens
    //•  delete a selected part or product from the list
    public static ObservableList<Product> getProductsInv() 
    {
        return productsInv;
    }
    public static void addProduct(Product product) 
    {
        productsInv.add(product);
    }
    
    public static void updateProduct(int ind, Product product)
    {
        productsInv.set(ind, product);
    }
    
    public static void deleteProduct(Product product)
    {
        productsInv.remove(product);
    }
    
    public static int getProductIDCT() 
    {
        productIDCT++;
        return productIDCT;

    }
    
    

    
    
    
    
    
    
}
