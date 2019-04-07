/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import Classes.Inhouse;
import Classes.Inventory;
import static Classes.Inventory.getPartsInv;
import Classes.Outsourced;
import Classes.Part;
import static demo.FXMLDocumentController.ModifyPartInd;
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
public class ModifyPartController implements Initializable {

    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private RadioButton InHouseRadioButton;
    @FXML private RadioButton OutsourcedRadioButton;
    @FXML private TextField partIDTextField; 
    @FXML private TextField partNameTextField; 
    @FXML private TextField partInvTextField;
    @FXML private TextField partPCTextField;
    @FXML private TextField partMaxTextField;
    @FXML private TextField partMinTextField;
    @FXML private TextField partCompanyNameMachineIDTextField; 
    @FXML private Label partCompanyNameMachineIDLabel;
    private ToggleGroup sourceToggleGroup;
    private boolean isPartOutsourced;
    int modifyPartIndex = ModifyPartInd();
    private String errorMessage = new String();
    private int partID;
    
    @FXML void modifyProductSaveButtonPushed(ActionEvent event) throws IOException
    {
        String partName = partNameTextField.getText();
        String partInv = partInvTextField.getText();
        String partPrice = partPCTextField.getText();
        String partMin = partMinTextField.getText();
        String partMax = partMaxTextField.getText();
        String partDyn = partCompanyNameMachineIDTextField.getText();
        try {
            errorMessage = Part.isPartValid(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), errorMessage);
            if (errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Part");
                alert.setHeaderText("Error!");
                alert.setContentText(errorMessage);
                alert.showAndWait();
            } else {
                if (isPartOutsourced == false) {
                    Inhouse tempInHousePart = new Inhouse();
                    tempInHousePart.setPartID(partID);
                    tempInHousePart.setPartName(partName);
                    tempInHousePart.setPartPC(Double.parseDouble(partPrice));
                    tempInHousePart.setPartLevel(Integer.parseInt(partInv));
                    tempInHousePart.setPartMin(Integer.parseInt(partMin));
                    tempInHousePart.setPartMax(Integer.parseInt(partMax));
                    tempInHousePart.setpartMachineID(Integer.parseInt(partDyn));
                    Inventory.updatePart(modifyPartIndex, tempInHousePart);
                } else {
                    Outsourced tempOutsourcedPart = new Outsourced();
                    tempOutsourcedPart.setPartID(partID);
                    tempOutsourcedPart.setPartName(partName);
                    tempOutsourcedPart.setPartPC(Double.parseDouble(partPrice));
                    tempOutsourcedPart.setPartLevel(Integer.parseInt(partInv));
                    tempOutsourcedPart.setPartMin(Integer.parseInt(partMin));
                    tempOutsourcedPart.setPartMax(Integer.parseInt(partMax));
                    tempOutsourcedPart.setPartCompanyName(partDyn);
                    Inventory.updatePart(modifyPartIndex, tempOutsourcedPart);
                }

                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close(); 
            }
        } catch (NumberFormatException e) {
            System.out.println("Blank Fields");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Adding Part!");
            alert.setHeaderText("Error");
            alert.setContentText("Form contains blank field.");
            alert.showAndWait();
        }
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Part part = getPartsInv().get(modifyPartIndex);
        partID = getPartsInv().get(modifyPartIndex).getPartID();
        partIDTextField.setText("Part ID: " + partID);
        partNameTextField.setText(part.getPartName());
        partInvTextField.setText(Integer.toString(part.getPartLevel()));
        partPCTextField.setText(Double.toString(part.getPartPC()));
        partMinTextField.setText(Integer.toString(part.getPartMin()));
        partMaxTextField.setText(Integer.toString(part.getPartMax()));
        if(part instanceof Inhouse)
        {
            partCompanyNameMachineIDTextField.setText(Integer.toString(((Inhouse) getPartsInv().get(modifyPartIndex)).getpartMachineID()));   
            partCompanyNameMachineIDLabel.setText("Machine ID");
            InHouseRadioButton.setSelected(true);
        }
        else
        {
            partCompanyNameMachineIDTextField.setText(((Outsourced) getPartsInv().get(modifyPartIndex)).getPartCompanyName());   
            partCompanyNameMachineIDLabel.setText("Company Name");
            OutsourcedRadioButton.setSelected(true);
        }
    sourceToggleGroup = new ToggleGroup();
        this.InHouseRadioButton.setToggleGroup(sourceToggleGroup);
        this.OutsourcedRadioButton.setToggleGroup(sourceToggleGroup);   
    }    
    @FXML void outsourcedButtonPushed(ActionEvent event)
    {
        isPartOutsourced = true;
        partCompanyNameMachineIDLabel.setText("Company Name");
        
    }
    @FXML void inhouseButtonPushed(ActionEvent event)
    {
        isPartOutsourced = false;
        partCompanyNameMachineIDLabel.setText("MachineID");
    }
    
    @FXML void cancelButtonPushed(ActionEvent event)
    {
        Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
        exit.initModality(Modality.NONE);
        exit.setContentText("Are you sure you want to cancel" +partNameTextField.getText()+"?");
        Optional<ButtonType> Cancel = exit.showAndWait();
        if(Cancel.get() == ButtonType.OK)
                {
                    final Node source = (Node) event.getSource();
                    final Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
        else
        {
            System.out.println("Cancel has been clicked.");
        }
        
    }
    
}
