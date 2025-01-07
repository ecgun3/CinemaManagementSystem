import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class SessionSelectionController {

    @FXML
    private Label availableSeatsLabel;

    @FXML
    private Button backButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<?> hallCombo;

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
    private ComboBox<?> sessionCombo;

    @FXML
    private VBox sessionSelectionRoot;

}
