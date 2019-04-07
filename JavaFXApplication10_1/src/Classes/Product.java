/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Juan G
 */
public class Product {
    private StringProperty prodName;
    private IntegerProperty prodID;
    private IntegerProperty prodLevel;
    private IntegerProperty ProdMin;
    private IntegerProperty ProdMax;
    private DoubleProperty PC;
    private static ObservableList<Part> parts = FXCollections.observableArrayList();

    public Product()
    {
        prodID = new SimpleIntegerProperty();
        prodName = new SimpleStringProperty();
        PC = new SimpleDoubleProperty();
        prodLevel = new SimpleIntegerProperty();
        ProdMin = new SimpleIntegerProperty();
        ProdMax = new SimpleIntegerProperty();
    }
    

    
    public IntegerProperty prodIDProperty() {
        return prodID;
    }

    public StringProperty prodNameProperty() {
        return prodName;
    }

    public DoubleProperty prodPCProperty() {
        return PC;
    }

    public IntegerProperty prodLevelProperty() {
        return prodLevel;
    }

//Getters
    public int getprodID() {
        return this.prodID.get();
    }

    public String getprodName() {
        return this.prodName.get();
    }

    public double getprodPC() {
        return this.PC.get();
    }

    public int getprodLevel() {
        return this.prodLevel.get();
    }

    public int getprodMin() {
        return this.ProdMin.get();
    }

    public int getprodMax() {
        return this.ProdMax.get();
    }

    //Setters
    public void setprodID(int ProdID) {
        this.prodID.set(ProdID);
    }

    public void setprodName(String name) {
        this.prodName.set(name);
    }

    public void setprodPC(double PC) {
        this.PC.set(PC);
    }

    public void setprodLevel(int ProdLevel) {
        this.prodLevel.set(ProdLevel);
    }

    public void setprodMin(int prodMin) {
        this.ProdMin.set(prodMin);
    }

    public void setprodMax(int ProdMax) {
        this.ProdMax.set(ProdMax);
    }
    public void setprodParts(ObservableList<Part> parts)
    {
        this.parts = parts;
    }
    public ObservableList getProductParts()
    {
        return parts;
    }
    
    
    
   

    
    
//•  entering an inventory value that exceeds the minimum or maximum value for that part or product
//
//•  preventing the minimum field from having a value above the maximum field
//
//•  preventing the maximum field from having a value below the minimum field
//
//•  ensuring that a product must always have at least one part
//•  ensuring that the price of a product cannot be less than the cost of the parts
//
//•  ensuring that a product must have a name, price, category, and inventory level (default 0)
    
    public static String isProductValid(String name, int min, int max, int inv, double price, ObservableList<Part> parts, String errorMessage)
    {
        double sumOfParts = 0.00;

        for(int i =0; i<parts.size();i++)
        {
            sumOfParts = parts.get(i).getPartPC() + sumOfParts;
        }
        if(name == null)
        {
            errorMessage = errorMessage+("Name text field is empty");
        }
        if(inv < 1){
            errorMessage = errorMessage+("Inventory Ct cannot be lower than 0");
        }
        if(price<1)
        {
            errorMessage = errorMessage+("Price must be higher than 0, since nothing is free");
        }
        if(min>max)
        {
            errorMessage = errorMessage+("Minimum must always be less than Maximum.");
        }
        if(sumOfParts > price)
        {
            errorMessage = errorMessage+("Product Price must be great than the cost of parts");
        }
        if(inv<min || inv>max)
        {
            errorMessage = errorMessage+("Inventory value must be in acceptable range.");
        }
        if(parts.size()<1)
        {
            errorMessage = errorMessage+("Product must contain at least 1 part ");
        }
        return errorMessage;
    }
    
}
