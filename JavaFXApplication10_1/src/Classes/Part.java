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

/**
 *
 * @author Juan G
 */
public class Part {    
    private StringProperty PartName;
    private IntegerProperty partID;
    private IntegerProperty partLevel;
    private IntegerProperty PartMin;
    private IntegerProperty PartMax;
    private DoubleProperty PC;
    public Part()
    {
        partID = new SimpleIntegerProperty();
        PartName = new SimpleStringProperty();
        PC = new SimpleDoubleProperty();
        partLevel = new SimpleIntegerProperty();
        PartMin = new SimpleIntegerProperty();
        PartMax = new SimpleIntegerProperty();
    }
    
    public IntegerProperty partIDProperty() {
        return partID;
    }

    public StringProperty partNameProperty() {
        return PartName;
    }

    public DoubleProperty PCProperty() {
        return PC;
    }

    public IntegerProperty partLevelProperty() {
        return partLevel;
    }

//Getters
    public int getPartID() {
        return this.partID.get();
    }

    public String getPartName() {
        return this.PartName.get();
    }

    public double getPartPC() {
        return this.PC.get();
    }

    public int getPartLevel() {
        return this.partLevel.get();
    }

    public int getPartMin() {
        return this.PartMin.get();
    }

    public int getPartMax() {
        return this.PartMax.get();
    }

    //Setters
    public void setPartID(int partID) {
        this.partID.set(partID);
    }

    public void setPartName(String name) {
        this.PartName.set(name);
    }

    public void setPartPC(double pc) {
        this.PC.set(pc);
    }

    public void setPartLevel(int partLevel) {
        this.partLevel.set(partLevel);
    }

    public void setPartMin(int PartMin) {
        this.PartMin.set(PartMin);
    }

    public void setPartMax(int PartMax) {
        this.PartMax.set(PartMax);
    }

    
//•  entering an inventory value that exceeds the minimum or maximum value for that part or product
//
//•  preventing the minimum field from having a value above the maximum field
//
//•  preventing the maximum field from having a value below the minimum field    
    public static String isPartValid(String name, int min, int max, int inv, double price, String errorMessage)
    {
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
        if(inv<min || inv>max)
        {
            errorMessage = errorMessage+("Inventory value must be in acceptable range.");
        }
        return errorMessage;
    }
}


