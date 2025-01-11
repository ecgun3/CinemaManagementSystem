package application;

import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.image.Image;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;

public class MovieManagementController {

    @FXML
    private TableView<Movie> TableMovieManagement;

    @FXML
    private TableColumn<Movie, String> tableMovieTitle;

    @FXML
    private TableColumn<Movie, String> tableMovieGenre;

    @FXML
    private TableColumn<Movie, String> tableMovieSummary;

    @FXML
    private TableColumn<Movie, String> tableMoviePoster;

    @FXML
    private TableColumn<Movie, String> tableMovieYear;

    @FXML
    private TextField TitleAddMovie;

    @FXML
    private TextField GenreAddMovie;

    @FXML
    private TextField SummaryAddMovie;

    @FXML
    private ImageView PosterAddMovieImageView;

    @FXML
    private TextField YearAddMovie;

    private ObservableList<Movie> movieList;

    @FXML
    public void initialize() {
        movieList = FXCollections.observableArrayList();

        // Tablodaki sütunlarla Movie sınıfının özelliklerini eşleştir
        tableMovieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableMovieYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        tableMovieGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        tableMovieSummary.setCellValueFactory(new PropertyValueFactory<>("summary"));
        tableMoviePoster.setCellValueFactory(new PropertyValueFactory<>("poster"));

        // Tablonun veri setini ayarla
        TableMovieManagement.setItems(movieList);

        // Veritabanından filmleri yükle
        loadMoviesFromDatabase();
    }

    @FXML
    void handleAddButton(ActionEvent event) {
        String title = TitleAddMovie.getText().trim();
        String genre = GenreAddMovie.getText().trim();
        String summary = SummaryAddMovie.getText().trim();
        byte[] poster = PosterAddMovieImageView.getImage() != null ? PosterAddMovieImageView.getImage().getUrl() : "";
        String year = YearAddMovie.getText().trim();
        int yearval = 0;

        if (title.isEmpty() || genre.isEmpty() || summary.isEmpty() || poster.isEmpty()|| year.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Information", "Please fill in all fields.");
            return;
        }

        if (title.length() > 100) {
            showAlert(Alert.AlertType.WARNING, "Title Too Long", "The title cannot be longer than 100 characters.");
            return;
        }

        if (summary.length() > 500) {
            showAlert(Alert.AlertType.WARNING, "Summary Too Long", "The summary cannot be longer than 500 characters.");
            return;
        }
    
        try {
            int yearValue = Integer.parseInt(year);
            if (yearValue < 1850 || yearValue > 2025) {
                showAlert(Alert.AlertType.WARNING, "Invalid Year", "Please enter a valid year (1850-2025).");
                return;
            }
            yearval=yearValue;
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Invalid Year", "Year must be a number.");
            return;
        }

        Movie newMovie = new Movie(0, title, yearval, genre, summary, poster);

        try {
            addMovieToDatabase(newMovie);
            movieList.add(newMovie);
            clearInputFields();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while adding the movie to the database.");
        }
    }

    @FXML
    void handleDeleteButton(ActionEvent event) {
        Movie selectedMovie = TableMovieManagement.getSelectionModel().getSelectedItem();

        if (selectedMovie == null) {
            showAlert(Alert.AlertType.INFORMATION, "Row Not Selected", "Please select a row to delete.");
            return;
        }

        try {
            deleteMovieFromDatabase(selectedMovie);
            movieList.remove(selectedMovie);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the movie.");
        }
    }

    @FXML
    void handleUpdateButton(ActionEvent event) {
        Movie selectedMovie = TableMovieManagement.getSelectionModel().getSelectedItem();

        if (selectedMovie == null) {
            showAlert(Alert.AlertType.INFORMATION, "Row Not Selected", "Please select a row to update.");
            return;
        }

        String newTitle = TitleAddMovie.getText().trim();
        String newGenre = GenreAddMovie.getText().trim();
        String newSummary = SummaryAddMovie.getText().trim();
        String newPoster = PosterAddMovieImageView.getImage() != null ? PosterAddMovieImageView.getImage().getUrl() : selectedMovie.getPoster();

        boolean isUpdated = false;

        if (!newTitle.isEmpty() && !newTitle.equals(selectedMovie.getTitle())) {
            selectedMovie.setTitle(newTitle);
            isUpdated = true;
        }

        if (!newGenre.isEmpty() && !newGenre.equals(selectedMovie.getGenre())) {
            selectedMovie.setGenre(newGenre);
            isUpdated = true;
        }

        if (!newSummary.isEmpty() && !newSummary.equals(selectedMovie.getSummary())) {
            selectedMovie.setSummary(newSummary);
            isUpdated = true;
        }

        if (!newPoster.equals(selectedMovie.getPoster())) {
            selectedMovie.setPoster(newPoster);
            isUpdated = true;
        }

        if (isUpdated) {
            try {
                updateMovieInDatabase(selectedMovie);
                TableMovieManagement.refresh();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "There was an error updating the movie.");
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "No Update Performed", "No field has been changed.");
        }
    }

    @FXML
    private void handleChoosePosterButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Poster");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", ".jpeg", ".gif"));

        File selectedFile = fileChooser.showOpenDialog(PosterAddMovieImageView.getScene().getWindow());
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                PosterAddMovieImageView.setImage(image);
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Image could not be uploaded. Please select a suitable file.");
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Poster Not Selected", "Please select a poster.");
        }
    }

    private void clearInputFields() {
        TitleAddMovie.clear();
        GenreAddMovie.clear();
        SummaryAddMovie.clear();
        PosterAddMovieImageView.setImage(null);
        YearAddMovie.clear();
    }

    private void loadMoviesFromDatabase() {
        // Veritabanından veri yükleme
    }

    private void addMovieToDatabase(Movie movie) {
        // Veritabanına film ekleme
    }

    private void deleteMovieFromDatabase(Movie movie) {
        // Veritabanından film silme
    }

    private void updateMovieInDatabase(Movie movie) {
        // Veritabanında güncelleme
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void titleEnterPressed(ActionEvent event) {
        // This method is triggered when the Enter key is pressed in the Title TextField
        GenreAddMovie.requestFocus();
    }

    @FXML
    void genreEnterPressed(ActionEvent event) {
        // This method is triggered when the Enter key is pressed in the Genre TextField
        SummaryAddMovie.requestFocus();
    }

    @FXML
    void summaryEnterPressed(ActionEvent event) {
        // This method is triggered when the Enter key is pressed in the Summary TextField
        YearAddMovie.requestFocus();
    }

    @FXML
    void yearEnterPressed(ActionEvent event) {
        // This method is triggered when the Enter key is pressed in the Year TextField
        TitleAddMovie.requestFocus();
    }

    @FXML
    void handleBackButton(ActionEvent event) {
        try {
            // Load the Admin.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Admin.fxml"));
            Parent root = loader.load();

            // Get the current window (Stage) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while loading the Admin page.");
        }
    }

    @FXML
    void handleLogoutButton(ActionEvent event) {
        try {
            // Load the Login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();

            // Get the current window (Stage) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while loading the Login page.");
        }
    }

}
