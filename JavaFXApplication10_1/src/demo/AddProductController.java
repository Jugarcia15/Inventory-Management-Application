package demo;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Classes.Inventory;
import Classes.Part;
import Classes.Product;
import static Classes.Inventory.getPartsInv;

/**
 * FXML Controller class
 *
 * @author Juan G
 */
public class AddProductController implements Initializable {

    //textfields
    @FXML private TextField addProductIDTextField;
    @FXML private TextField addProductNameTextField;
    @FXML private TextField addProductPriceTextField;
    @FXML private TextField addProductLevelTextField;
    @FXML private TextField addProductMinTextField;
    @FXML private TextField addProductMaxTextField;
    @FXML private TextField addProductSearchTextField;
    
    //table 1
    @FXML private TableView<Part> addProductTableView;
    @FXML private TableColumn<Part, Integer> addProductIDColumn;
    @FXML private TableColumn<Part, String> addProductNameColumn;
    @FXML private TableColumn<Part, Integer> addProductLevelColumn;
    @FXML private TableColumn<Part, Double> addProductPriceColumn;
    
    //table 2
    @FXML private TableView<Part> addProductCurrentTableView;
    @FXML private TableColumn<Part, Integer> addCurrentProductIDColumn;
    @FXML private TableColumn<Part, String> addCurrentProductNameColumn;
    @FXML private TableColumn<Part, Integer> addCurrentProductLevelColumn;
    @FXML private TableColumn<Part, Double> addCurrentProductPriceColumn;

    
    private ObservableList<Part> cParts = FXCollections.observableArrayList();
    private String errorMessage = new String();
    private int productID;
    
    public AddProductController() {
    }
    
    @FXML void addProductSearchButtonPushed(ActionEvent event) 
    {
        String searchPart = addProductSearchTextField.getText();
        int partIndex = -1;

        if (Inventory.SearchPart(searchPart) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search");
            alert.setHeaderText("Part not found");
            alert.setContentText("Part not found.");
            alert.showAndWait();
        } else {
            partIndex = Inventory.SearchPart(searchPart);
            Part tempPart = Inventory.getPartsInv().get(partIndex);

            ObservableList<Part> tempProdList = FXCollections.observableArrayList();
            tempProdList.add(tempPart);
            addProductTableView.setItems(tempProdList);
        }
    }


    @FXML void addProductButtonPushed(ActionEvent event) {
        Part part = addProductTableView.getSelectionModel().getSelectedItem();
        cParts.add(part);
        updateProductCurrentTableView();
    }

    @FXML void addProductDeleteButtonPushed(ActionEvent event) 
    {
        Part part = addProductCurrentTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Current Part Delete!");
        alert.setContentText("Are you sure you want to delete part " + part.getPartName() + " from parts?");
        Optional<ButtonType> result = alert.showAndWait();

        //•  remove or disassociate a part from a product
        if (result.get() == ButtonType.OK) {
            System.out.println("Part deleted.");
            cParts.remove(part);
        } else {
            System.out.println("Canceled.");
        }
    }

    @FXML void AddProductsSaveButtonPushed(ActionEvent event) throws IOException {
        //•  enter name, inventory level, price, and max and min values
        String productName = addProductNameTextField.getText();
        String productInv = addProductLevelTextField.getText();
        String productPrice = addProductPriceTextField.getText();
        String productMin = addProductMinTextField.getText();
        String productMax = addProductMaxTextField.getText();

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
                Inventory.addProduct(tempProduct);
                //•  save the data and then redirect to the main screen
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Adding Product!");
            alert.setHeaderText("Error!");
            alert.setContentText("Fields cannot be left blank!");
            alert.showAndWait();
        }
    }
    
    //•  cancel or exit out of this screen and go back to the main screen
    @FXML void AddProductsCancelPushed(ActionEvent event) throws IOException {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.initModality(Modality.NONE);
        exitAlert.setTitle("Confirmation Needed!");
        exitAlert.setHeaderText("Confirm Product Cancelation!");
        exitAlert.setContentText("Are you sure you want to cancel adding product " + addProductNameTextField.getText() + "?");
        Optional<ButtonType> choice = exitAlert.showAndWait();

        if (choice.get() == ButtonType.OK) {

            final Node source = (Node) event.getSource();
                    final Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
        } else {
            System.out.println("You clicked cancel. Please complete part info.");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //•  associate one or more parts with a product
        addProductIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        addProductNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        addProductLevelColumn.setCellValueFactory(cellData -> cellData.getValue().partLevelProperty().asObject());
        addProductPriceColumn.setCellValueFactory(cellData -> cellData.getValue().PCProperty().asObject());

        addCurrentProductIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        addCurrentProductNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        addCurrentProductLevelColumn.setCellValueFactory(cellData -> cellData.getValue().partLevelProperty().asObject());
        addCurrentProductPriceColumn.setCellValueFactory(cellData -> cellData.getValue().PCProperty().asObject());

        updateProductTableView();
        updateProductCurrentTableView();

        productID = Inventory.getProductIDCT();
        addProductIDTextField.setText("AUTO GEN: " + productID);
    }

    public void updateProductTableView() {
        addProductTableView.setItems(getPartsInv());
    }

    public void updateProductCurrentTableView() {
        addProductCurrentTableView.setItems(cParts);
    }
}