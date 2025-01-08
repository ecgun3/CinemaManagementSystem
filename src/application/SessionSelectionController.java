package application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SessionSelectionController {

    private MainCashierController mainController;
    private Movie movieData;
    private Session selectedSession;

    private static final String[] HALLS = {"Hall_A", "Hall_B"};
    private static final String[] SESSIONS = {"10:00", "12:00", "14:00", "16:00", "18:00", "20:00"};

    @FXML
    private Label availableSeatsLabel;

    @FXML
    private Button backButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> hallCombo;

    @FXML
    private ImageView moviePosterView;

    @FXML
    private Button nextButton;

    @FXML
    private Label selectedGenreLabel;

    @FXML
    private Label selectedMovieLabel;

    @FXML
    private GridPane selectionGrid;

    @FXML
    private ComboBox<String> sessionCombo;

    @FXML
    private VBox sessionSelectionRoot;

    @FXML
    private void intialize() {

        //Combobox'ları doldurmak için
        hallCombo.getItems().addAll(HALLS);
        sessionCombo.getItems().addAll(SESSIONS);

        //DatePicker'ı bugünden başlatıp 1 ay ile sınırlayalım
        datePicker.setValue(LocalDate.now());
        datePicker.setDayCellFactory(datePicker1 -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                LocalDate oneMonthLater = today.plusMonths(1);
                setDisable(empty || date.compareTo(today) < 0 || date.compareTo(oneMonthLater) > 0);
            }
        });

        //Event Listeners:
        datePicker.valueProperty().addListener((obs, oldVal, newVal) -> updateAvailableSeats());
        hallCombo.valueProperty().addListener((obs, oldVal, newVal) -> updateAvailableSeats());
        sessionCombo.valueProperty().addListener((obs, oldVal, newVal) -> updateAvailableSeats());

        backButton.setOnAction(event -> handleBack());
        nextButton.setOnAction(event -> handleNext());

        // Next butonu başlangıçta devre dışı
        nextButton.setDisable(true);
    }

    public void setMainController(MainCashierController controller) {
        this.mainController = controller;
    }

    public void setMovieData(Movie movie) {
        this.movieData = movie;
        updateMovieInfo();
    }

    private void updateMovieInfo() {
        if (movieData != null) {
            selectedMovieLabel.setText(movieData.getTitle());
            selectedGenreLabel.setText(movieData.getGenre());
            if (movieData.getPosterImage() != null) {
                Image image = new Image(new ByteArrayInputStream(movieData.getPosterImage()));
                moviePosterView.setImage(image);
            }
        }
    }

    private void updateAvailableSeats() {
        if (datePicker.getValue() != null && hallCombo.getValue() != null && sessionCombo.getValue() != null) {
            try {
                // Veritabanından boş koltuk sayısını al
                int availableSeats = getAvailableSeatsCount(
                        datePicker.getValue(),
                        hallCombo.getValue(),
                        sessionCombo.getValue()
                );

                availableSeatsLabel.setText(String.valueOf(availableSeats));
                nextButton.setDisable(availableSeats <= 0);

                // Session nesnesini güncelle
                selectedSession = new Session(
                        0, // ID veritabanından gelecek
                        movieData,
                        LocalDateTime.of(datePicker.getValue(), LocalTime.parse(sessionCombo.getValue())),
                        hallCombo.getValue(),
                        hallCombo.getValue().equals("Hall_A") ? 16 : 48,
                        availableSeats
                );
            } catch (Exception e) {
                showError("Database Error", "Could not fetch available seats: " + e.getMessage());
            }
        } else {
            availableSeatsLabel.setText("--");
            nextButton.setDisable(true);
        }
    }

    private int getAvailableSeatsCount(LocalDate date, String hall, String session) {
        // TODO: Veritabanı sorgusu ile boş koltuk sayısını al
        // Şimdilik örnek olarak:
        return hall.equals("Hall_A") ? 16 : 48;
    }

    @FXML
    private void handleBack() {
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

    @FXML
    private void handleNext() {
        if (selectedSession == null) {
            showError("Selection Error", "Please select date, hall and session");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SeatSelection.fxml"));
            Parent seatView = loader.load();

            SeatSelectionController seatController = loader.getController();
            if (seatController == null) {
                throw new RuntimeException("Could not load seat selection controller");
            }

            seatController.setMainController(mainController);
            seatController.setSessionData(movieData, selectedSession);

            mainController.getContentArea().getChildren().setAll(seatView);
        } catch (IOException e) {
            showError("Loading Error", "Could not load seat selection screen: " + e.getMessage());
        }
    }

    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}