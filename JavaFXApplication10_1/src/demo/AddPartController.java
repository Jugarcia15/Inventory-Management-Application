/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import Classes.Inhouse;
import Classes.Inventory;
import Classes.Part;
import Classes.Outsourced;
import Classes.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Juan G
 */
public class AddPartController implements Initializable {

    //RadioButton and toggle for Inhouse and outsourced
    @FXML private RadioButton InHouseRadioButton;
    @FXML private RadioButton OutsourcedRadioButton;
    private ToggleGroup sourceToggleGroup;
    
    @FXML private TextField partIDTextField; 
    @FXML private TextField partNameTextField; 
    @FXML private TextField partInvTextField;
    @FXML private TextField partPCTextField;
    @FXML private TextField partMaxTextField;
    @FXML private TextField partMinTextField;
    @FXML private TextField partMachineIDCompanyNameTextField;
    
    @FXML private Label partMachineIDLabel;

    Part selectedPart;
    Boolean isPartOutsourced = true;
    private int partID;
    private String ErrorMessage = new String();
    
    //•  enter name, inventory level, price, max and min values, and company name or machine ID
    //•  save the data and then redirect to the main screen    
    @FXML void addButtonPushed(ActionEvent event) throws IOException
    {
        String partName = partNameTextField.getText();
        String partInv = partInvTextField.getText();
        String partPC = partPCTextField.getText();
        String partMachineIDCompanyName = partMachineIDCompanyNameTextField.getText();
        String partMin = partMinTextField.getText();
        String partMax = partMaxTextField.getText();
        
        try{
            ErrorMessage = Part.isPartValid(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPC), ErrorMessage);
            if(ErrorMessage.length()>0)
            {
                Alert simpleAlert = new Alert(AlertType.INFORMATION);
                simpleAlert.setTitle("Error when attempting to add part.");
                simpleAlert.setTitle("Error, please review");
                simpleAlert.setContentText(ErrorMessage);
                simpleAlert.showAndWait();
                ErrorMessage = "";
            }
            else{
                if(isPartOutsourced == false)
                {
                    Inhouse inPart = new Inhouse();
                    inPart.setPartID(partID);
                    inPart.setPartName(partName);
                    inPart.setPartPC(Double.parseDouble(partPC));
                    inPart.setPartLevel(Integer.parseInt(partInv));
                    inPart.setPartMax(Integer.parseInt(partMax));
                    inPart.setPartMin(Integer.parseInt(partMin));
                    inPart.setpartMachineID(Integer.parseInt(partMachineIDCompanyName));
                    Inventory.addPart(inPart); 
                }
                else
                {
                    Outsourced outPart = new Outsourced();
                    outPart.setPartID(partID);
                    outPart.setPartName(partName);
                    outPart.setPartPC(Double.parseDouble(partPC));
                    outPart.setPartLevel(Integer.parseInt(partInv));
                    outPart.setPartMax(Integer.parseInt(partMax));
                    outPart.setPartMin(Integer.parseInt(partMin));
                    outPart.setPartCompanyName(partMachineIDCompanyName);
                    Inventory.addPart(outPart);  
                }
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close(); 
            }
        }
        catch(NumberFormatException ex)
        {
            Alert Error = new Alert(AlertType.INFORMATION);
            Error.setTitle("Error");
            Error.setContentText("Some fields are blank");
            Error.showAndWait();
        }
    }   
    
    //•  cancel or exit out of this screen and go back to the main screen
    @FXML void cancelButtonPushed(ActionEvent event)
    {
        Alert exit = new Alert(AlertType.CONFIRMATION);
        exit.initModality(Modality.NONE);
        exit.setContentText("Are you sure you want to cancel adding " +partNameTextField.getText()+"?");
        Optional<ButtonType> Cancel = exit.showAndWait();
        if(Cancel.get() == ButtonType.OK)
                {
                    final Node source = (Node) event.getSource();
                    final Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
        else
        {
            System.out.println("Canceled.");
        }
        
    }
    
    
    //radio buttons
    //•  select “In-House” or “Outsourced”
    @FXML void outsourcedButtonPushed(ActionEvent event)
    {
        isPartOutsourced = true;
        partMachineIDLabel.setText("Company Name");
        
    }
    @FXML void inhouseButtonPushed(ActionEvent event)
    {
        isPartOutsourced = false;
        partMachineIDLabel.setText("Machine ID");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //configure radiobuttons •  select “In-House” or “Outsourced”
        sourceToggleGroup = new ToggleGroup();
        this.InHouseRadioButton.setToggleGroup(sourceToggleGroup);
        this.OutsourcedRadioButton.setToggleGroup(sourceToggleGroup);
        partID = Inventory.getPartIDCT();
        partIDTextField.setText("AUTO GEN:" + partID);
    }
}
