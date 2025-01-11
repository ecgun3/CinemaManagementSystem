package application;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class InventoryController {

    @FXML
    private Button backToManagerMenu;
    
    @FXML
    private Button Logout;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> stockStatus;
    

    @FXML
    private Button updateStockOK;

    @FXML
    private TextField updateStockBeverage;

    @FXML
    private TextField updateStockBiscuit;

    @FXML
    private TextField updateStockToy;

    public void initialize(){
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockStatus.setCellValueFactory(new PropertyValueFactory<>("stock"));
        loadInventory();
    }
    
    
    public void loadInventory(){
        //databaseden product listesini çekiyor
        //ArrayList<Product> productList = DatabaseProduct.viewInventory();
        ArrayList<Product> products = new ArrayList<Product>();

        Product product1 = new Product(1, "beverage", 50, 100);
        Product product2 = new Product(2, "biscuit", 30, 200);
        Product product3 = new Product(3, "toy", 100, 40);
        products.add(product1);
        products.add(product2);
        products.add(product3);

        ObservableList<Product> listofproducts = FXCollections.observableArrayList(products);
        productTable.setItems(listofproducts);
    }


    public void updateStock(){
    
        try{
            int beverageQuantity = Integer.parseInt(updateStockBeverage.getText());
            int biscuitQuantity = Integer.parseInt(updateStockBiscuit.getText());
            int toyQuantity = Integer.parseInt(updateStockToy.getText());
            
            //databesedeki productlar güncellencek
            //DatabaseProduct.updateProduct(1,"stock", beverageQuantity);
            //DatabaseProduct.updateProduct(2, "stock", biscuitQuantity);
            //DatabaseProduct.updateProduct(3,"stock", toyQuantity);

        loadInventory();
        } catch (NumberFormatException e) {
            System.out.println("Please Enter Valid Numbers!");
        }
    }

    @FXML
    @SuppressWarnings("unused")
    private void handleLogoutAction(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Login.fxml"));
    Parent root = loader.load();

    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    }

    
    @FXML
    @SuppressWarnings("unused")
    private void handleBackToManagerMenuAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ManagerMenu.fxml"));
        Parent root = loader.load();
    
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        }

}

