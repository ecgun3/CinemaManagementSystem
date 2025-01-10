import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load Admin.fxml
            FXMLLoader loaderAdmin = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Parent rootAdmin = loaderAdmin.load();
            Scene sceneAdmin = new Scene(rootAdmin);

            primaryStage.setTitle("Admin Menu");
            primaryStage.setScene(sceneAdmin); // Set the first scene (Admin.fxml)
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
