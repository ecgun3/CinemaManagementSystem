import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


public class CustomerInfoController {

    private MainCashierController mainController;
    private Movie movieData;
    private Session sessionData;
    private List<Seat> selectedSeats;
    private double totalTicketPrice = 0.0;


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
        loadProducts();
    }

    // Bunlar eksik:
    private void loadProducts() {
        // Veritabanından ya da sabit listeden ürünleri yükle
        // Örnek ürünler ekleyelim
        List<Product> products = List.of(
                new Product(1, "Cola", 15.0, 50, "beverage"),
                new Product(2, "Popcorn", 25.0, 50, "biscuit"),
                new Product(3, "Toy Story Figure", 45.0, 20, "toy")
        );
        productsTable.getItems().addAll(products);
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private void applyAgeDiscount() {
        // %50 indirim uygula
        totalTicketPrice = totalTicketPrice * 0.5;
        updateTotalPrice();
    }

    private double calculateProductsTotal() {
        return productsTable.getItems().stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
    }

    private void addToSummary(Product product) {
        OrderItem item = new OrderItem(
                0, // id
                product.getName(),
                1, // quantity
                product.getPrice(),
                0, // no discount for products
                "product"
        );
        summaryTable.getItems().add(item);
    }

    private void loadMovieSearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieSearch.fxml"));
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
        productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
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
                movieData.getTitle(), sessionData.getHall(), sessionData.getDateTime()));

        String seatNumbers = selectedSeats.stream()
                .map(Seat::getSeatNumber)
                .collect(Collectors.joining(", "));
        selectedSeatsLabel.setText("Selected Seats: " + seatNumbers);

        calculateInitialTotal();
    }

    private void calculateInitialTotal() {
        totalTicketPrice = selectedSeats.stream()
                .mapToDouble(Seat::getPrice)
                .sum();
        updateTotalPrice();
    }

    private void validateAge() {
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
        if (product.getQuantity() > 0) {
            product.setQuantity(product.getQuantity() - 1);
            addToSummary(product);
            updateTotalPrice();
        }
    }

    private void handleRemoveProduct(Product product) {
        // Remove product from summary
        // Update prices
    }

    private void updateTotalPrice() {
        double totalProducts = calculateProductsTotal();
        double total = totalTicketPrice + totalProducts;
        double vat = total * 0.20; // 20% VAT

        vatLabel.setText(String.format("%.2f TL", vat));
        totalPriceLabel.setText(String.format("%.2f TL", total + vat));
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SeatSelection.fxml"));
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
