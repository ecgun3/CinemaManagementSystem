import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import java.io.IOException;

public class MovieSearchController {

    private MainCashierController mainController;

    //Seçilen film bilgilerini tutacak model sınıf
    private Movie selectedMovie;

    @FXML
    private Button cancelButton;

    @FXML
    private TableColumn<Movie, String> genreColumn;

    @FXML
    private Label movieGenreLabel;

    @FXML
    private TableView<Movie> movieResultsTable;

    @FXML
    private VBox movieSearchRoot;

    @FXML
    private TextArea movieSummaryArea;

    @FXML
    private Label movieTitleLabel;

    @FXML
    private Button nextButton;

    @FXML
    private ImageView posterImageView;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private ComboBox<String> searchTypeCombo; //Generic tipini String olarak değiştirdim

    private static final String[] SEARCH_TYPES = {
            "Search by Genre",
            "Search by Partial Name",
            "Search by Full Name"
    };

    @FXML
    private TableColumn<Movie, String> summaryColumn;

    @FXML
    private TableColumn<Movie, String> titleColumn;

    public void setMainController(MainCashierController controller) {
        this.mainController = controller;
    }

    @FXML
    private void initialize() {

        /*Burayı SİL
        try {
            String path = "/denemeFoto.jpg";
            InputStream stream = getClass().getResourceAsStream(path);

            if(stream != null) {
                Image image = new Image(stream);
                posterImageView.setImage(image);
            } else {
                System.out.println("Resim dosyası bulunmadı");
            }
        } catch (Exception e) {
            System.err.println("Resmi Yükleme Hatası " + e.getMessage());
        } */

        searchTypeCombo.getItems().addAll(SEARCH_TYPES);
        searchTypeCombo.getSelectionModel().selectFirst();

        //Tablo sütunları ayarı:
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        summaryColumn.setCellValueFactory(new PropertyValueFactory<>("summary")); // Bu eksik!


        //Tablo seçim event lisetener:
        movieResultsTable.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newWal) -> {
            if(newWal != null) {
                selectedMovie = newWal;
                updateMovieDetails();
            }
        });

        //next butonu:
        searchButton.setOnAction(event -> handleSearch());
        cancelButton.setOnAction(event -> handleCancel());
        nextButton.setOnAction(event -> loadSessionSelection());    }

    private void updateMovieDetails() {
        if (selectedMovie != null) {
            movieTitleLabel.setText(selectedMovie.getTitle());
            movieGenreLabel.setText(selectedMovie.getGenre());
            movieSummaryArea.setText(selectedMovie.getSummary());

            // Poster'ı güncelle
            if (selectedMovie.getPosterImage() != null) {
                Image image = new Image(new ByteArrayInputStream(selectedMovie.getPosterImage()));
                posterImageView.setImage(image);
            }
        }
    }

    //VERİTABANI İÇİN ÖRNEK KOD:
    /*
    private List<Movie> searchByGenre(String genre) {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies WHERE genre LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + genre + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("summary"),
                        rs.getBytes("poster_image"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            showError("Database Error", "Error while fetching movies: " + e.getMessage());
        }
        return movies;
    } */


    @FXML
    private void handleSearch() {
        String searchType = searchTypeCombo.getValue();
        String searchTerm = searchTextField.getText().trim();

        if (searchTerm.isEmpty()) {
            showError("Search Error", "Please enter a search term");
            return;
        }

        List<Movie> results;
        try {
            switch(searchType) {
                case "Search by Genre":
                    results = searchByGenre(searchTerm);
                    break;
                case "Search by Partial Name":
                    results = searchByPartialName(searchTerm);
                    break;
                case "Search by Full Name":
                    results = searchByFullName(searchTerm);
                    break;
                default:
                    results = new ArrayList<>();
            }
            movieResultsTable.getItems().setAll(results);
        } catch (Exception e) {
            showError("Search Error", "Error while searching: " + e.getMessage());
        }
    }

    // Search metodları (veritabanı bağlantısı gerekecek)
    private List<Movie> searchByGenre(String genre) {
        // Veritabanından genre'a göre filmleri çek
        return new ArrayList<>(); // TODO: implement
    }

    private List<Movie> searchByPartialName(String partialName) {
        // Veritabanından partial name'e göre filmleri çek
        return new ArrayList<>(); // TODO: implement
    }

    private List<Movie> searchByFullName(String fullName) {
        // Veritabanından tam isme göre filmleri çek
        return new ArrayList<>(); // TODO: implement
    }

    @FXML
    private void handleCancel() {
        // Seçimleri temizle
        searchTextField.clear();
        movieResultsTable.getSelectionModel().clearSelection();
        movieTitleLabel.setText("");
        movieGenreLabel.setText("");
        movieSummaryArea.clear();
        posterImageView.setImage(null);
        selectedMovie = null;
    }

    private void loadSessionSelection() {
        if (selectedMovie == null) {
            showError("Selection Error", "Please select a movie first");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SessionSelection.fxml"));
            Parent sessionView = loader.load();

            SessionSelectionController sessionController = loader.getController();
            if (sessionController == null) {
                throw new RuntimeException("Could not load session controller");
            }

            sessionController.setMainController(mainController);
            sessionController.setMovieData(selectedMovie);

            mainController.getContentArea().getChildren().setAll(sessionView);
        } catch (IOException e) {
            showError("Loading Error", "Could not load session selection screen: " + e.getMessage());
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
