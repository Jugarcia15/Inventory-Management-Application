/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Juan G
 */
public class Outsourced extends Part{
    private StringProperty partCompanyName;
    public Outsourced(){
        super();
        partCompanyName = new SimpleStringProperty();
    }

    public String getPartCompanyName() {
        return this.partCompanyName.get();
    }

    public void setPartCompanyName(String partCompanyName) {
        this.partCompanyName.set(partCompanyName);
    }
    
            
}
