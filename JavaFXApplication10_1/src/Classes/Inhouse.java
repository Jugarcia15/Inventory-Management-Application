/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Juan G
 */
public class Inhouse extends Part{
    private IntegerProperty partMachineID;
    public Inhouse()
    {
        super();
        partMachineID = new SimpleIntegerProperty();
    }
    
    public int getpartMachineID()
    {
        return this.partMachineID.get();
    }
    public void setpartMachineID(int partMachineID)
    {
        this.partMachineID.set(partMachineID);
    }
    
    
}
