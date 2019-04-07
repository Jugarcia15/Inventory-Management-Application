/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import Classes.Inventory;
import Classes.Part;
import Classes.Product;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Classes.Inventory;
import Classes.Part;
import Classes.Product;
import static Classes.Inventory.getPartsInv;
import static Classes.Inventory.getProductsInv;
import static Classes.Inventory.validatePartDelete;
//import static Classes.Inventory.validateProductDelete;
import static Classes.Inventory.removePart;
import static Classes.Inventory.deleteProduct;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

/**
 *
 * @author Juan G
 */
public class FXMLDocumentController implements Initializable {
    @FXML TableView<Part> partTable;
    @FXML TableColumn<Part, Integer> partLevel;
    @FXML TableColumn<Part, Integer> partID;
    @FXML TableColumn<Part, String> partName;
    @FXML TableColumn<Part, Double> partCost;
    
    @FXML TableView<Product> productTable;
    @FXML TableColumn<Product, Integer> productLevel;
    @FXML TableColumn<Product,Integer> productID;
    @FXML TableColumn<Product, String> productName;
    @FXML TableColumn<Product, Double> productCost;
    @FXML TextField partFilterString;
    @FXML TextField productFilterString;
    
    private static Part ModifyPart;
    private static Product ModifyProduct;
    private static int modPartInd;
    private static int modProductInd;
    
    public void updatePartTable()
    {
        partTable.setItems(getPartsInv());
    }
    public void updateProductTable()
    {
        productTable.setItems(getProductsInv());
    }
    
    public static int ModifyPartInd()
    {
        return modPartInd;
    }
    public static int ModifyProductInd()
    {
        return modProductInd;
    }
    
    @FXML void PartSearchButtonPushed(ActionEvent event)
    {
        String SearchPartField = partFilterString.getText();
        int partsInd = -1;
        if(Inventory.SearchPart(SearchPartField) == -1)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Part not found");
            alert.setContentText("Term does not match any parts");
            alert.showAndWait();
        }
        else{
            partsInd = Inventory.SearchPart(SearchPartField);
            Part partTemp = Inventory.getPartsInv().get(partsInd);
            ObservableList<Part> partTempList = FXCollections.observableArrayList();
            partTempList.add(partTemp);
            partTable.setItems(partTempList);
        }
    }
    
    
    @FXML void ProductSearchButtonPushed(ActionEvent event)
    {
        String SearchProductField = productFilterString.getText();
        int productInd = -1;
        if(Inventory.SearchProduct(SearchProductField) == -1)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Product not found");
            alert.setContentText("Term does not match any product");
            alert.showAndWait(); 
        }
        else{
            productInd = Inventory.SearchProduct(SearchProductField);
            Product productTemp = Inventory.getProductsInv().get(productInd);
            ObservableList<Product> productTempList = FXCollections.observableArrayList();
            productTempList.add(productTemp);
            productTable.setItems(productTempList);
        }
    }
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
         productID.setCellValueFactory(cellData -> cellData.getValue().prodIDProperty().asObject());
         productLevel.setCellValueFactory(cellData -> cellData.getValue().prodLevelProperty().asObject());
         productCost.setCellValueFactory(cellData -> cellData.getValue().prodPCProperty().asObject());
         productName.setCellValueFactory(cellData -> cellData.getValue().prodNameProperty());
         partID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
         partLevel.setCellValueFactory(cellData -> cellData.getValue().partLevelProperty().asObject());
         partCost.setCellValueFactory(cellData -> cellData.getValue().PCProperty().asObject());
         partName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
         updatePartTable();
         updateProductTable();

    }
    //button actions
    
    //PARTS BUTTONS
    @FXML void addPartButtonPushed(ActionEvent event)
    {
        try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPart.fxml"));
                    Parent root = (Parent) loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Add Part Window");
                    stage.setScene(new Scene(root));
                    stage.show();
        }
        catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    
    
    @FXML void modifyPartButtonPushed(ActionEvent Event)
    {
        ModifyPart = partTable.getSelectionModel().getSelectedItem();
        modPartInd = getPartsInv().indexOf(ModifyPart);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
            Parent root = (Parent) loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Modify Part Window");
                    stage.setScene(new Scene(root));
                    stage.show();
        }
        catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    @FXML void deletePartButtonPushed(ActionEvent event) throws IOException
    {
        Part temp = partTable.getSelectionModel().getSelectedItem();
        if (validatePartDelete(temp)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Part Delete Error!");
            alert.setHeaderText("Part cannot be removed!");
            alert.setContentText("This part is used in a product.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Product Delete");
            alert.setHeaderText("Confirm?");
            alert.setContentText("Are you sure you want to delete " + temp.getPartName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                removePart(temp);
                updatePartTable();
                System.out.println("Part " + temp.getPartName() + " was removed.");
            } else {
                System.out.println("Part " + temp.getPartName() + " was not removed.");
            }
        }
    }
    
    
    //PRODUCT BUTTONS
    @FXML void addProductButtonPushed(ActionEvent event) throws IOException
    {
        try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
                    Parent root = (Parent) loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Add Part Window");
                    stage.setScene(new Scene(root));
                    stage.show();
        }
        catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    @FXML void modifyProductButtonPushed(ActionEvent event)
    {
        ModifyProduct = productTable.getSelectionModel().getSelectedItem();
        modProductInd = getProductsInv().indexOf(ModifyProduct);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
            Parent root = (Parent) loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Modify Part Window");
                    stage.setScene(new Scene(root));
                    stage.show();
        }
        catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    @FXML void deleteProductButtonPushed(ActionEvent event)
    {
        Product temp = productTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Confirm?");
        alert.setContentText("Are you sure you want to delete " + temp.getprodName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            deleteProduct(temp);
            updateProductTable();
            System.out.println("Product " + temp.getprodName() + " was removed.");
        } else {
            System.out.println("Product " + temp.getprodName() + " was not removed.");
        }
    }
    
    //EXIT BUTTON
    @FXML void exitButtonPushed(ActionEvent event)
    {
        Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
        exit.initModality(Modality.NONE);
        Optional<ButtonType> choice = exit.showAndWait();
        if(choice.get() == ButtonType.OK)
        {
        Platform.exit();
        }
        else 
        {
            System.out.println("Canceled");
        }
    }
    
@FXML void clearPartsSearchButtonPushed(ActionEvent event) throws IOException
    {
        updatePartTable();
        partFilterString.setText("");
    }
@FXML void clearProductsSearchButtonPushed(ActionEvent event) throws IOException
    {
        updateProductTable();
        productFilterString.setText("");
    }
    
}

    
    
    
    

    
    

    
    