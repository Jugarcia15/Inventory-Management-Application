package demo;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private String exceptionMessage = new String();
    private int productID;

    @FXML private TextField AddProductsIDField;
    @FXML private TextField AddProductsNameField;
    @FXML private TextField AddProductsPriceField;
    @FXML private TextField AddProductsInvField;
    @FXML private TextField AddProductsMinField;
    @FXML private TextField AddProductsMaxField;
    @FXML private TextField AddProductDeletePartSearchField;
    @FXML private TextField AddProductAddPartSearchField;
    
    @FXML private TableView<Part> AddProductsAddTableView;
    @FXML private TableColumn<Part, Integer> AddProductPartIDCol;
    @FXML private TableColumn<Part, String> AddProductPartNameCol;
    @FXML private TableColumn<Part, Integer> AddProductInvLevelCol;
    @FXML private TableColumn<Part, Double> AddProductPriceCol;
    @FXML private TableView<Part> AddProductsDeleteTableView;
    
    @FXML private TableColumn<Part, Integer> AddProductCurrentPartIDCol;
    @FXML private TableColumn<Part, String> AddProductCurrentPartNameCol;
    @FXML private TableColumn<Part, Integer> AddProductCurrentInvCol;
    @FXML private TableColumn<Part, Double> AddProductCurrentPriceCol;

    public AddProductController() {
    }
    
    @FXML
    void AddProductsSearchPartAddBtn(ActionEvent event) {

        String searchPart = AddProductAddPartSearchField.getText();
        int partIndex = -1;

        if (Inventory.SearchPart(searchPart) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search");
            alert.setHeaderText("Part not found");
            alert.setContentText("The part has not been found.");
            alert.showAndWait();
        } else {
            partIndex = Inventory.SearchPart(searchPart);
            Part tempPart = Inventory.getPartsInv().get(partIndex);

            ObservableList<Part> tempProdList = FXCollections.observableArrayList();
            tempProdList.add(tempPart);
            AddProductsAddTableView.setItems(tempProdList);
        }
    }


    @FXML
    void AddProductsAddPartBtn(ActionEvent event) {
        Part part = AddProductsAddTableView.getSelectionModel().getSelectedItem();
        currentParts.add(part);
        updateCurrentPartTableView();
    }

    @FXML
    void AddProductsDeletePartBtn(ActionEvent event) {

        Part part = AddProductsDeleteTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Current Part Delete!");
        alert.setContentText("Are you sure you want to delete part " + part.getPartName() + " from parts?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.out.println("Part deleted.");
            currentParts.remove(part);
        } else {
            System.out.println("Cancel clicked.");
        }
    }

    @FXML
    void AddProductsSaveButtonPushed(ActionEvent event) throws IOException {

        String productName = AddProductsNameField.getText();
        String productInv = AddProductsInvField.getText();
        String productPrice = AddProductsPriceField.getText();
        String productMin = AddProductsMinField.getText();
        String productMax = AddProductsMaxField.getText();

        try {

            exceptionMessage = Product.isProductValid(productName, Integer.parseInt(productMin), Integer.parseInt(productMax), Integer.parseInt(productInv),
                    Double.parseDouble(productPrice), currentParts, exceptionMessage);

            if (exceptionMessage.length() > 0) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Product");
                alert.setHeaderText("Error");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            } else {
                System.out.println("Product name: " + productName);

                Product newProduct = new Product();

                newProduct.setprodID(productID);
                newProduct.setprodName(productName);
                newProduct.setprodPC(Double.parseDouble(productPrice));
                newProduct.setprodLevel(Integer.parseInt(productInv));
                newProduct.setprodMin(Integer.parseInt(productMin));
                newProduct.setprodMax(Integer.parseInt(productMax));
                newProduct.setprodParts(currentParts);
                Inventory.addProduct(newProduct);

                Parent productsSave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(productsSave);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(scene);
                window.show();
            }
        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Adding Part!");
            alert.setHeaderText("Error!");
            alert.setContentText("Fields cannot be left blank!");
            alert.showAndWait();
        }
    }

    @FXML
    void AddProductsCancelPushed(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation Needed!");
        alert.setHeaderText("Confirm Product Delete!");
        alert.setContentText("Are you sure you want to delete product " + AddProductsNameField.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

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
        AddProductPartIDCol.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        AddProductPartNameCol.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        AddProductInvLevelCol.setCellValueFactory(cellData -> cellData.getValue().partLevelProperty().asObject());
        AddProductPriceCol.setCellValueFactory(cellData -> cellData.getValue().PCProperty().asObject());

        AddProductCurrentPartIDCol.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        AddProductCurrentPartNameCol.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        AddProductCurrentInvCol.setCellValueFactory(cellData -> cellData.getValue().partLevelProperty().asObject());
        AddProductCurrentPriceCol.setCellValueFactory(cellData -> cellData.getValue().PCProperty().asObject());

        updatePartTableView();
        updateCurrentPartTableView();

        productID = Inventory.getProductIDCT();
        AddProductsIDField.setText("AUTO GEN: " + productID);
    }

    public void updatePartTableView() {
        AddProductsAddTableView.setItems(getPartsInv());
    }

    public void updateCurrentPartTableView() {
        AddProductsDeleteTableView.setItems(currentParts);
    }
}