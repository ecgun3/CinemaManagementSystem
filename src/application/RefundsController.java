package application;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

public class RefundsController {

    @FXML
    private Button backFromRefunds;

    @FXML
    private TableColumn<Bills, ?> birthReceiptCol;

    @FXML
    private Button logoutFromRefund;

    @FXML
    private TableColumn<Bills, ?> nameReceiptCol;

    @FXML
    private TextField noOfReceiptText;

    @FXML
    private TableColumn<Bills, ?> noReceiptCol;

    @FXML
    private TableView<Bills> receiptTable;

    @FXML
    private Button refundButton;

    @FXML
    private Button searchByNameRefund;

    @FXML
    private TextField searchByNameText;

    @FXML
    private TableColumn<Bills, ?> taxReceiptCol;

    @FXML
    private TableColumn<Bills, ?> timeReceiptCol;

    @FXML
    private TableColumn<Bills, ?> totalReceiptCol;

    public void initialize(){
        noReceiptCol.setCellValueFactory(new PropertyValueFactory<>("bill_id"));
        timeReceiptCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        nameReceiptCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        birthReceiptCol.setCellValueFactory(new PropertyValueFactory<>("customerBirth"));
        totalReceiptCol.setCellValueFactory(new PropertyValueFactory<>("total_amount"));
        taxReceiptCol.setCellValueFactory(new PropertyValueFactory<>("tax_amount"));


        loadBills();
        }
    
    private void loadBills(){

        //database'den verileri çek
        //örnek veri
        ArrayList<Bills> list = new ArrayList<>();
        list.add(new Bills(1, LocalDateTime.parse("2024-01-20T14:30:00"),"karan1", LocalDate.parse("2001-02-25"), 1, 20.00, 30.00));
        list.add(new Bills(2, LocalDateTime.parse("2024-01-21T14:30:00"),"karan2", LocalDate.parse("2024-01-12"), 2, 20.00, 30.00));
        list.add(new Bills(3, LocalDateTime.parse("2024-01-22T14:30:00"),"karan3", LocalDate.parse("2000-01-01"), 3, 20.00, 30.00));
        list.add(new Bills(4, LocalDateTime.parse("2024-01-23T14:30:00"),"karan4", LocalDate.parse("2000-01-25"), 4, 20.00, 30.00));


        //Database fonksiyonu ile listeyi ArrayList olarak al ..

        //observableliste çevir
        ObservableList<Bills> bills = FXCollections.observableArrayList(list);

        //tabloya geçir
        receiptTable.setItems(bills);
    }

    @FXML
    void handleBackToAdminMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Admin.fxml"));
        Parent root = loader.load();
    
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        }

    @FXML
    void handleLogout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Login.fxml"));
        Parent root = loader.load();
    
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        }

    @FXML
    void handleRefund(ActionEvent event) {
        //try catch ile receipt id alma
        try {
            int receiptno = Integer.parseInt(noOfReceiptText.getText());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input, Please Try Again.");
        }

        //receiptno ile sorgulanıp db'den fatura silincek

        //String query = "DELETE * FROM bills WHERE receiptno ?";
        //pstmt.setInt(1, receiptno);
    }

    @FXML
    void handleSearchByNameRefund(ActionEvent event) throws IOException{
        String customerName = searchByNameText.getText(); 

        if (customerName.isEmpty()){
            System.out.println("Please enter a name ");//gets the name 
            return;
        }

        //customername ile query

        //database'den verileri çek
        ArrayList<Bills> list = new ArrayList<>();
        list.add(new Bills(22, LocalDateTime.parse("2024-01-20T14:30:00"),"karanoooo", LocalDate.parse("2001-02-25"), 1, 20.00, 30.00));
        ObservableList<Bills> bills = FXCollections.observableArrayList(list);

        receiptTable.setItems(bills);

    }

}
