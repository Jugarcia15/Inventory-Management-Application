/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    private ToggleGroup sourceToggleGroup;
    private FXMLDocumentController fxmlController;
    private AddPartController apController;
    @FXML private TextField partIDTextField; 
    @FXML private TextField partNameTextField; 
    @FXML private TextField partInvTextField;
    @FXML private TextField partPCTextField;
    @FXML private TextField partMaxTextField;
    @FXML private TextField partMinTextField;
    @FXML private TextField partCompanyNameTextField;
    @FXML private TextField partMachineIDTextField; 
    @FXML private Label partCompanyNameLabel, partMachineIDLabel;
    private boolean inHouse;
    public Button getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    public RadioButton getInHouseRadioButton() {
        return InHouseRadioButton;
    }

    public void setInHouseRadioButton(RadioButton InHouseRadioButton) {
        this.InHouseRadioButton = InHouseRadioButton;
    }

    public RadioButton getOutsourcedRadioButton() {
        return OutsourcedRadioButton;
    }

    public void setOutsourcedRadioButton(RadioButton OutsourcedRadioButton) {
        this.OutsourcedRadioButton = OutsourcedRadioButton;
    }

    public ToggleGroup getSourceToggleGroup() {
        return sourceToggleGroup;
    }

    public void setSourceToggleGroup(ToggleGroup sourceToggleGroup) {
        this.sourceToggleGroup = sourceToggleGroup;
    }

    public FXMLDocumentController getFxmlController() {
        return fxmlController;
    }

    public void setFxmlController(FXMLDocumentController fxmlController) {
        this.fxmlController = fxmlController;
    }

    public AddPartController getApController() {
        return apController;
    }

    public void setApController(AddPartController apController) {
        this.apController = apController;
    }

    public TextField getPartIDTextField() {
        return partIDTextField;
    }

    public void setPartIDTextField(TextField partIDTextField) {
        this.partIDTextField = partIDTextField;
    }

    public TextField getPartNameTextField() {
        return partNameTextField;
    }

    public void setPartNameTextField(TextField partNameTextField) {
        this.partNameTextField = partNameTextField;
    }

    public TextField getPartInvTextField() {
        return partInvTextField;
    }

    public void setPartInvTextField(TextField partInvTextField) {
        this.partInvTextField = partInvTextField;
    }

    public TextField getPartPCTextField() {
        return partPCTextField;
    }

    public void setPartPCTextField(TextField partPCTextField) {
        this.partPCTextField = partPCTextField;
    }

    public TextField getPartMaxTextField() {
        return partMaxTextField;
    }

    public void setPartMaxTextField(TextField partMaxTextField) {
        this.partMaxTextField = partMaxTextField;
    }

    public TextField getPartMinTextField() {
        return partMinTextField;
    }

    public void setPartMinTextField(TextField partMinTextField) {
        this.partMinTextField = partMinTextField;
    }

    public TextField getPartCompanyNameTextField() {
        return partCompanyNameTextField;
    }

    public void setPartCompanyNameTextField(TextField partCompanyNameTextField) {
        this.partCompanyNameTextField = partCompanyNameTextField;
    }

    public TextField getPartMachineIDTextField() {
        return partMachineIDTextField;
    }

    public void setPartMachineIDTextField(TextField partMachineIDTextField) {
        this.partMachineIDTextField = partMachineIDTextField;
    }

    public Label getPartCompanyNameLabel() {
        return partCompanyNameLabel;
    }

    public void setPartCompanyNameLabel(Label partCompanyNameLabel) {
        this.partCompanyNameLabel = partCompanyNameLabel;
    }

    public Label getPartMachineIDLabel() {
        return partMachineIDLabel;
    }

    public void setPartMachineIDLabel(Label partMachineIDLabel) {
        this.partMachineIDLabel = partMachineIDLabel;
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sourceToggleGroup = new ToggleGroup();
        this.InHouseRadioButton.setToggleGroup(sourceToggleGroup);
        this.OutsourcedRadioButton.setToggleGroup(sourceToggleGroup);
    }    
    @FXML void outsourcedButtonPushed(ActionEvent event)
    {
        inHouse = false;
        partMachineIDLabel.setText("Company Name");
        
    }
    @FXML void inhouseButtonPushed(ActionEvent event)
    {
        inHouse = true;
        partMachineIDLabel.setText("MachineID");
    }
    
    @FXML void cancelButtonPushed(ActionEvent event)
    {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
}
