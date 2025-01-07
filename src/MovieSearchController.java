import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MovieSearchController {

    @FXML
    private Button cancelButton;

    @FXML
    private TableColumn<?, ?> genreColumn;

    @FXML
    private Label movieGenreLabel;

    @FXML
    private TableView<?> movieResultsTable;

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
    private ComboBox<?> searchTypeCombo;

    @FXML
    private TableColumn<?, ?> summaryColumn;

    @FXML
    private TableColumn<?, ?> titleColumn;

}
