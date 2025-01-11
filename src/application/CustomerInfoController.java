package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import database.DatabasePrice;
import database.DatabaseProduct;


public class CustomerInfoController {

    private MainCashierController mainController;
    private Movie movieData;
    private Session sessionData;
    private List<Seat> selectedSeats;
    private double totalTicketPrice = 0.0;
    private Price prices;

    @FXML
    private Button backButton;

    @FXML
    private TextField birthDateField;

    @FXML
    private Button confirmPayButton;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField idNumberField;

    @FXML
    private TableColumn<OrderItem, Double> itemDiscountColumn;

    @FXML
    private TableColumn<OrderItem, String> itemNameColumn;

    @FXML
    private TableColumn<OrderItem, Double> itemPriceColumn;

    @FXML
    private TableColumn<OrderItem, Integer> itemQuantityColumn;

    @FXML
    private TableColumn<OrderItem, Double> itemTotalColumn;

    @FXML
    private TextField lastNameField;

    @FXML
    private Label movieInfoLabel;

    @FXML
    private TableColumn<Product, String> productActionColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    @FXML
    private TableColumn<Product, Integer> productQuantityColumn;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableView<OrderItem> summaryTable;

    @FXML
    private Label selectedSeatsLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label vatLabel;

    @FXML
    private void initialize() {
        // Tablo sütunlarını ayarla
        setupTables();

        // Event listeners
        backButton.setOnAction(event -> handleBack());
        confirmPayButton.setOnAction(event -> handleConfirmPay());
        birthDateField.setOnAction(event -> validateAge());

        // Ürünleri yükle
        DatabasePrice dataPr = new DatabasePrice();
        dataPr.connectDatabase();
        prices = dataPr.getPrices();
        dataPr.disconnectDatabase();
        loadProducts();
    }

    private void loadProducts() {
        // Veritabanından ürünleri yükle
        DatabaseProduct dataP = new DatabaseProduct();
        dataP.connectDatabase();
        List<Product> products = dataP.viewInventory();
        dataP.disconnectDatabase();
        productsTable.getItems().addAll(products);
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private void applyAgeDiscount() {
        // %50 indirim uygula
        double discountRate = prices.getDiscountPercentage()/100;
        totalTicketPrice = totalTicketPrice * discountRate;
        updateTotalPrice();
    }

    private double calculateProductsTotal() {
        return summaryTable.getItems().stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
    }

    private void addToSummary(Product product) {
        if(product.getStock()>0){
            int flag = 0;
            for(OrderItem item : summaryTable.getItems()){
                if(item.getName().equals(product.getName())){
                    item.setQuantity(item.getQuantity()+1);
                    flag=1;
                    break;
                }
            }
            if(flag==0){
                OrderItem itemNew = new OrderItem(
                    0, // id
                    product.getName(),
                    1, // quantity
                    product.getPrice(),
                    0, // no discount for products
                    "product"
                    );
                summaryTable.getItems().add(itemNew);
            }
            product.setStock(product.getStock()-1);
            summaryTable.refresh();
            productsTable.refresh();
        }
    }

    private void removeFromSummary(Product product) {
        OrderItem itemToRemove = new OrderItem();
        for(OrderItem item : summaryTable.getItems()){
            if(item.getName().equals(product.getName())){
                product.setStock(product.getStock()+1);
                item.setQuantity(item.getQuantity()-1);
                if(item.getQuantity()==0)
                    itemToRemove=item;
                break;
            }
        }
        summaryTable.getItems().remove(itemToRemove);
        summaryTable.refresh();
        productsTable.refresh();
    }

    private void loadMovieSearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/MovieSearch.fxml"));
            Parent movieSearchView = loader.load();
            MovieSearchController movieController = loader.getController();
            movieController.setMainController(mainController);
            mainController.getContentArea().getChildren().setAll(movieSearchView);
        } catch (IOException e) {
            showError("Navigation Error", "Could not return to movie search: " + e.getMessage());
        }
    }

    private void setupTables() {
        // Ürünler tablosu
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        setupActionColumn();

        // Özet tablosu
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemDiscountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        itemTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    private void setupActionColumn() {
        productActionColumn.setCellFactory(column -> new TableCell<>() {
            final Button addButton = new Button("+");
            final Button removeButton = new Button("-");
            final HBox buttonBox = new HBox(5, addButton, removeButton);

            {
                addButton.setOnAction(event -> handleAddProduct(getTableRow().getItem()));
                removeButton.setOnAction(event -> handleRemoveProduct(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttonBox);
                }
            }
        });
    }

    public void setMainController(MainCashierController controller) {
        this.mainController = controller;
    }

    public void setBookingData(Movie movie, Session session, List<Seat> seats) {
        this.movieData = movie;
        this.sessionData = session;
        this.selectedSeats = seats;
        updateBookingInfo();
    }

    private void updateBookingInfo() {
        movieInfoLabel.setText(String.format("Movie: %s - Hall: %s - Date: %s",
                movieData.getTitle(), sessionData.getHall().getName(), sessionData.getDateTime()));

        String seatNumbers = selectedSeats.stream()
                .map(Seat::getSeat)
                .collect(Collectors.joining(", "));
        selectedSeatsLabel.setText("Selected Seats: " + seatNumbers);

        calculateInitialTotal();
    }

    private void calculateInitialTotal() {

        totalTicketPrice = selectedSeats.size()*prices.getPrice();
        updateTotalPrice();
    }

    private void validateAge() {//                                                      ECE date picker ile yapılacak!!!
        try {
            LocalDate birthDate = LocalDate.parse(birthDateField.getText(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int age = calculateAge(birthDate);
            if (age < 18 || age > 60) {
                applyAgeDiscount();
            }
        } catch (Exception e) {
            showError("Invalid Date", "Please enter date in DD/MM/YYYY format");
        }
    }

    private void handleAddProduct(Product product) {
        addToSummary(product);
        updateTotalPrice();
    }

    private void handleRemoveProduct(Product product) {
        // Remove product from summary
        removeFromSummary(product);
        updateTotalPrice();
        // Update prices
    }

    private void updateTotalPrice() {
        double totalProducts = calculateProductsTotal();
        double total = totalTicketPrice + totalProducts;
        double vat = total * prices.getTaxPercentage()/100; // Taxes

        vatLabel.setText(String.format("%.2f TL", vat));
        totalPriceLabel.setText(String.format("%.2f TL", total));
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/SeatSelection.fxml"));
            Parent seatView = loader.load();

            SeatSelectionController seatController = loader.getController();
            seatController.setMainController(mainController);
            seatController.setSessionData(movieData, sessionData);

            mainController.getContentArea().getChildren().setAll(seatView);
        } catch (IOException e) {
            showError("Navigation Error", "Could not return to seat selection: " + e.getMessage());
        }
    }

    @FXML
    private void handleConfirmPay() {
        if (!validateFields()) {
            return;
        }

        try {
            // Save customer info
            // Process payment
            // Generate tickets and invoice
            // Return to movie search
            loadMovieSearch();
        } catch (Exception e) {
            showError("Payment Error", "Could not process payment: " + e.getMessage());
        }
    }

    private boolean validateFields() {
        if (firstNameField.getText().trim().isEmpty() ||
                lastNameField.getText().trim().isEmpty() ||
                idNumberField.getText().trim().isEmpty() ||
                birthDateField.getText().trim().isEmpty()) {
            showError("Validation Error", "Please fill all customer information fields");
            return false;
        }
        return true;
    }

    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
