package application;

import java.io.IOException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MonthlySchedulesController {

    @FXML
    private DatePicker ScheduleDateChoice;

    @FXML
    private ChoiceBox<String> ScheduleMovieChoice;

    @FXML
    private ChoiceBox<String> ScheduleHallChoice;

    @FXML
    private ChoiceBox<String> ScheduleSessionChoice;

    @FXML
    private Button backFromSchedules;

    @FXML
    private Button createSchedulesButton;

    @FXML
    private TableColumn<Schedule, LocalDate> dateScheduleTable;

    @FXML
    private TableColumn<Schedule, String> hallScheduleTable;

    @FXML
    private TableColumn<Schedule, String> movieScheduleTable;

    @FXML
    private TableView<Schedule> schedulesTable;

    @FXML
    private TableColumn<Schedule, String> sessionScheduleTable;

    @FXML
    private Button updateSchedulesButton;

    private ObservableList<Schedule> scheduleList = FXCollections.observableArrayList();

    @FXML
    private Button logoutButton;

    @FXML
    private HBox topBar;

    @FXML
    private Label userInfoLabel;

    @FXML
    public void initialize() {
        dateScheduleTable.setCellValueFactory(new PropertyValueFactory<>("date"));
        hallScheduleTable.setCellValueFactory(new PropertyValueFactory<>("hall"));
        sessionScheduleTable.setCellValueFactory(new PropertyValueFactory<>("session"));
        movieScheduleTable.setCellValueFactory(new PropertyValueFactory<>("movie"));

        scheduleList = null;//TestData.getTestSchedules();
        schedulesTable.setItems(scheduleList);

        ScheduleHallChoice.setItems(FXCollections.observableArrayList("Hall A", "Hall B"));
        ScheduleSessionChoice.setItems(FXCollections.observableArrayList("12:00", "14:00", "16:00"));
        ScheduleMovieChoice.setItems(FXCollections.observableArrayList("Movie A", "Movie B"));
    }

    @FXML
    void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Admin.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleCreateButton(ActionEvent event) {
        LocalDate date = ScheduleDateChoice.getValue();
        String hall = ScheduleHallChoice.getValue();
        String session = ScheduleSessionChoice.getValue();
        String movie = ScheduleMovieChoice.getValue();

        if (date != null && hall != null && session != null && movie != null) {
            Schedule newSchedule = new Schedule(scheduleList.size() + 1, date, hall, session, movie);
            scheduleList.add(newSchedule);
            schedulesTable.setItems(scheduleList);
            clearFields();
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "All fields are required!");
        }
    }

    @FXML
    void handleUpdateButton(ActionEvent event) {
        Schedule selectedSchedule = schedulesTable.getSelectionModel().getSelectedItem();

        if (selectedSchedule != null) {
            LocalDate date = ScheduleDateChoice.getValue();
            String hall = ScheduleHallChoice.getValue();
            String session = ScheduleSessionChoice.getValue();
            String movie = ScheduleMovieChoice.getValue();

            if (date != null) selectedSchedule.setDate(date);
            if (hall != null) selectedSchedule.setHall(hall);
            if (session != null) selectedSchedule.setSession(session);
            if (movie != null) selectedSchedule.setMovie(movie);

            schedulesTable.refresh();
            clearFields();
        }
    }

    @FXML
    void handleLogoutButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while loading the Login page.");
        }
    }

    private void clearFields() {
        ScheduleDateChoice.setValue(null);
        ScheduleHallChoice.setValue(null);
        ScheduleSessionChoice.setValue(null);
        ScheduleMovieChoice.setValue(null);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}