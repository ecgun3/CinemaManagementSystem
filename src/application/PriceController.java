package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PriceController {

    @FXML
    private Button ApplyDiscount18;

    @FXML
    private Button ApplyDiscount60;

    @FXML
    private Button BackToManagerMenu;

    @FXML
    private Button BackToManagerMenu1;

    @FXML
    private Label DiscountRate1;

    @FXML
    private Label DiscountRate2;

    @FXML
    private Label EditPrice;

    @FXML
    private Button Logout;

    @FXML
    private Button Logout1;

    @FXML
    private Tab Prices;

    @FXML
    private Tab Prices1;

    @FXML
    private Button SetPricesButton;

    @FXML
    private TableColumn<?, ?> category;

    @FXML
    private TableColumn<?, ?> categoryPrice;

    @FXML
    private TextField newDiscountRate18;

    @FXML
    private TextField newDiscountRate60;

    @FXML
    private TableView<?> personnelAction1;

    @FXML
    private TextField setNewPrice1;

    @FXML
    private TextField setNewPrice2;

    @FXML
    private TextField setNewPrice3;

    @FXML
    private TextField setNewPrice4;

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

