/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import Classes.Inventory;
import static Classes.Inventory.getPartsInv;
import static Classes.Inventory.getProductsInv;
import Classes.Part;
import Classes.Product;
import static demo.FXMLDocumentController.ModifyProductInd;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Juan G
 */
public class ModifyProductController implements Initializable {

    @FXML private TextField modifyProductIDTextField;
    @FXML private TextField modifyProductNameTextField;
    @FXML private TextField modifyProductPCTextField;
    @FXML private TextField modifyProductLevelTextField;
    @FXML private TextField modifyProductMinTextField;
    @FXML private TextField modifyProductMaxTextField;
    @FXML private TextField modifyProductSearchTextField;
    
    @FXML private TableView<Part> modifyProductTableView;
    @FXML private TableColumn<Part, Integer> modifyProductIDColumn;
    @FXML private TableColumn<Part, String> modifyProductNameColumn;
    @FXML private TableColumn<Part, Integer> modifyProductLevelColumn;
    @FXML private TableColumn<Part, Double> modifyProductPriceColumn;
    
    @FXML private TableView<Part> modifyProductCurrentTableView;
    @FXML private TableColumn<Part, Integer> modifyCurrentProductIDColumn;
    @FXML private TableColumn<Part, String> modifyCurrentProductNameColumn;
    @FXML private TableColumn<Part, Integer> modifyCurrentProductLevelColumn;
    @FXML private TableColumn<Part, Double> modifyCurrentProductPriceColumn;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    
    private ObservableList<Part> cParts = FXCollections.observableArrayList();
    private String errorMessage = new String();
    private int productID;
    private int modifyProductIndex = ModifyProductInd();
    /**
     * Initializes the controller class.
     */
    
    @FXML void modifyProductSearchButtonPushed(ActionEvent event) 
    {
        String SearchTerm = modifyProductSearchTextField.getText();
        int partIndex = -1;

        if (Inventory.SearchPart(SearchTerm) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search");
            alert.setHeaderText("Part not found");
            alert.setContentText("The part has not been found.");
            alert.showAndWait();
        } else {
            partIndex = Inventory.SearchPart(SearchTerm);
            Part tempPart = Inventory.getPartsInv().get(partIndex);

            ObservableList<Part> tempProdList = FXCollections.observableArrayList();
            tempProdList.add(tempPart);
            modifyProductTableView.setItems(tempProdList);
        }
    }
    
    @FXML void modifyAddProductButtonPushed(ActionEvent event) {
        //•  associate one or more parts with a product
        Part part = modifyProductTableView.getSelectionModel().getSelectedItem();
        cParts.add(part);
        updateProductCurrentTableView();
    }
    
    //•  remove or disassociate a part from a product
    @FXML void modifyProductDeleteButtonPushed(ActionEvent event) 
    {
        Part part = modifyProductCurrentTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Current Part Delete!");
        alert.setContentText("Are you sure you want to delete part " + part.getPartName() + " from parts?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            cParts.remove(part);
        } else {
            System.out.println("Canceled.");
        }
    }
    
    @FXML void modifyProductsSaveButtonPushed(ActionEvent event) throws IOException {
        //•  save modifications to the data and then redirect to the main screen
        String productName = modifyProductNameTextField.getText();
        String productInv = modifyProductLevelTextField.getText();
        String productPrice = modifyProductPCTextField.getText();
        String productMin = modifyProductMinTextField.getText();
        String productMax = modifyProductMaxTextField.getText();

        try {

            errorMessage = Product.isProductValid(productName, Integer.parseInt(productMin), Integer.parseInt(productMax), Integer.parseInt(productInv),
                    Double.parseDouble(productPrice), cParts, errorMessage);

            if (errorMessage.length() > 0) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Product");
                alert.setHeaderText("Error");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            } else {
                System.out.println("Product name: " + productName);

                Product tempProduct = new Product();

                tempProduct.setprodID(productID);
                tempProduct.setprodName(productName);
                tempProduct.setprodPC(Double.parseDouble(productPrice));
                tempProduct.setprodLevel(Integer.parseInt(productInv));
                tempProduct.setprodMin(Integer.parseInt(productMin));
                tempProduct.setprodMax(Integer.parseInt(productMax));
                tempProduct.setprodParts(cParts);
                Inventory.updateProduct(modifyProductIndex, tempProduct);

                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Adding Part!");
            alert.setHeaderText("Error!");
            alert.setContentText("Fields cannot be left blank!");
            alert.showAndWait();
        }
    }

    //•  cancel or exit out of this screen and go back to the main screen
    @FXML void modifyProductsCancelPushed(ActionEvent event) throws IOException {

        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.initModality(Modality.NONE);
        exitAlert.setTitle("Confirmation Needed!");
        exitAlert.setHeaderText("Confirm Product Delete!");
        exitAlert.setContentText("Are you sure you want to delete product " + modifyProductNameTextField.getText() + "?");
        Optional<ButtonType> choice = exitAlert.showAndWait();

        if (choice.get() == ButtonType.OK) {

            final Node source = (Node) event.getSource();
                    final Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
        } else {
            System.out.println("You clicked cancel. Please complete part info.");
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //•  modify or change data values
        Product products = getProductsInv().get(modifyProductIndex);
        productID = getProductsInv().get(modifyProductIndex).getprodID();

        modifyProductNameTextField.setText(products.getprodName());
        modifyProductPCTextField.setText(Double.toString(products.getprodPC()));
        modifyProductLevelTextField.setText(Integer.toString(products.getprodLevel()));
        modifyProductMinTextField.setText(Integer.toString(products.getprodMin()));
        modifyProductMaxTextField.setText(Integer.toString(products.getprodMax()));
        modifyProductIDTextField.setText("Auto Gen:"+ productID);
        
        modifyProductIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        modifyProductNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        modifyProductLevelColumn.setCellValueFactory(cellData -> cellData.getValue().partLevelProperty().asObject());
        modifyProductPriceColumn.setCellValueFactory(cellData -> cellData.getValue().PCProperty().asObject());

        modifyCurrentProductIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        modifyCurrentProductNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        modifyCurrentProductLevelColumn.setCellValueFactory(cellData -> cellData.getValue().partLevelProperty().asObject());
        modifyCurrentProductPriceColumn.setCellValueFactory(cellData -> cellData.getValue().PCProperty().asObject());

        updateProductTableView();
        updateProductCurrentTableView();

    } 
    
    public void updateProductTableView() {
        modifyProductTableView.setItems(getPartsInv());
    }

    public void updateProductCurrentTableView() {
        modifyProductCurrentTableView.setItems(cParts);
    }
    
    @FXML void clearProductsSearchButtonPushed(ActionEvent event) throws IOException
    {
        updateProductTableView();
        modifyProductSearchTextField.setText("");
    }
}
