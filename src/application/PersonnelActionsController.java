package application;

import java.io.IOException;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PersonnelActionsController {

    @FXML
    private Button ApplyHire;

    @FXML
    private Button ClickFire;

    @FXML
    private TextField EditFirstName;

    @FXML
    private TextField EditLastName;

    @FXML
    private TextField EditPassword;


    @FXML
    private TextField EditRole;

    @FXML
    private TextField EditUsername;

    @FXML
    private TableColumn<Employee, String> EditFirstNameCol;

    @FXML
    private TableColumn<Employee, String> EditLastNameCol;

    @FXML
    private TableColumn<Employee, String> EditPasswordCol;

    @FXML
    private TableColumn<Employee, String> EditRoleCol;

    @FXML
    private TableColumn<Employee, String> EditUsernameCol;

    @FXML
    private TableColumn<Employee, String> FireFirstName;

    @FXML
    private TableColumn<Employee, String> FireLastName;

    @FXML
    private TableColumn<Employee, String> FirePassword;

    @FXML
    private TableColumn<Employee, String> FireRole;

    @FXML
    private TableColumn<Employee, String> FireUsername;

    @FXML
    private TextField HireFirstName;

    @FXML
    private TextField HireLastName;

    @FXML
    private TextField HireUsername;

    @FXML
    private Button Logout;

    @FXML
    private Button Logout1;

    @FXML
    private Button Logout2;

    @FXML
    private Button Logout3;

    @FXML
    private Button PersonnelEditApply;

    @FXML
    private ChoiceBox<String> RoleChoice;

    @FXML
    private Button SearchEditClick;

    @FXML
    private Button SearchFireClick;

    @FXML
    private TextField SearchPersonnelEdit;

    @FXML
    private TextField SearchPersonnelFire;

    @FXML
    private Button backToManagerMenu;

    @FXML
    private Button backToManagerMenu1;

    @FXML
    private Button backToManagerMenu2;

    @FXML
    private Button backToManagerMenu21;

    @FXML
    private TableColumn<Employee, String> firstName;

    @FXML
    private TableColumn<Employee, String> lastName;

    @FXML
    private TableColumn<Employee, String> password;

    @FXML
    private TableView<Employee> personnelAction;

    @FXML
    private TableView<Employee> personnelAction1;

    @FXML
    private TableView<Employee> personnelAction11;

    @FXML
    private TableColumn<Employee, String> role;

    @FXML
    private TableColumn<Employee, String> username;

    private Employee currentEmployee;


    @FXML 
    public void initialize(){
        RoleChoice.setItems(FXCollections.observableArrayList("Admin", "Manager", "Cashier"));
        configureTableColumns(personnelAction);
        configureTableColumns(personnelAction1);
        configureTableColumns(personnelAction11);

        //loadPersonnelTable();
    }

    private void configureTableColumns(TableView<Employee> tableView){
        firstName.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("surname"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    
    /*
    private void loadPersonnelTable(){
        ArrayList<Employee> employeeList = DatabaseEmployee.getEmployees();
        ObservableList<Employee> employee = FXCollections.observableArrayList(employeeList);
        personnelAction.setItems(employee);
    }
    
    

    @SuppressWarnings("unused")
    private void insertPersonnelButton(ActionEvent event) throws IOException{
        String name = HireFirstName.getText();
        String surname = HireLastName.getText();
        String username = HireUsername.getText();
        String role = RoleChoice.getValue();

        if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || role.isEmpty()){
            System.out.println("Please fill all fields");
            return;
        }

        Employee employee = new Employee(name, surname, username, role);
        DatabaseEmployee.insertEmployee(employee);
        loadPersonnelTable();
    }


    @SuppressWarnings("unused")
    private void firePersonnelSearchButton(ActionEvent event) throws IOException{
        String username = SearchPersonnelFire.getText();

        if (username.isEmpty()){
            System.out.println("Please fill the username");
            return;
        }

        ObservableList<Employee> employee = FXCollections.observableArrayList(DatabaseEmployee.getEmployeeUsername(username));
        personnelAction1.setItems(employee);
    }


    @SuppressWarnings("unused")
    private void firePersonnelButton(ActionEvent event) throws IOException{
        String username = SearchPersonnelFire.getText();

        if (username.isEmpty()){
            System.out.println("Please fill the username");
            return;
        }
        
        Employee employee = DatabaseEmployee.getEmployeeUsername(username);
        DatabaseEmployee.deleteEmployee(employee);
    }


    @SuppressWarnings("unused")
    private void EditPersonnelSearchButton(ActionEvent event) throws IOException{
        String username = SearchPersonnelEdit.getText();

        if (username.isEmpty()){
            System.out.println("Please fill the username");
            return;
        }

        ObservableList<Employee> employee = FXCollections.observableArrayList(DatabaseEmployee.getEmployeeUsername(username));
        personnelAction11.setItems(employee);

        if (employee.isEmpty()) {
            System.out.println("No employee found with the given username.");
            return;
        }

        currentEmployee = employee.get(0);

        EditFirstName.setText(currentEmployee.getName());
        EditLastName.setText(currentEmployee.getSurname());
        EditUsername.setText(currentEmployee.getUsername());
        EditPassword.setText(currentEmployee.getPassword());
        EditRole.setText(currentEmployee.getRole());

    }


    @SuppressWarnings("unused")
    private void EditPersonnelButton(ActionEvent event) throws IOException{
        if (currentEmployee == null) {
            System.out.println("No employee selected.");
            return;
        }

        String name = EditFirstName.getText();
        String surname = EditLastName.getText();
        String username = EditUsername.getText();
        String password = EditPassword.getText();
        String role = EditRole.getText();

        if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty()){
            System.out.println("Please fill all fields");
            return;
        } 

        DatabaseEmployee.updateEmployee(currentEmployee, "name", name);
        DatabaseEmployee.updateEmployee(currentEmployee, "surname", surname);
        DatabaseEmployee.updateEmployee(currentEmployee, "username", username);
        DatabaseEmployee.updateEmployee(currentEmployee, "password", password);
        DatabaseEmployee.updateEmployee(currentEmployee, "role", role);
        System.out.println("Employee updated successfully.");
        
    }
        */

    @FXML
    @SuppressWarnings("unused")
    private void handleLogoutAction(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Login.fxml"));
    Parent root = loader.load();

    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    }

    @FXML
    @SuppressWarnings("unused")
    private void handleBackToManagerMenuAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ManagerMenu.fxml"));
        Parent root = loader.load();
    
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        }

}





