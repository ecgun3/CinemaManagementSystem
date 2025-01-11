package application;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Labeled;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
public class PersonnelActionsController {

    @FXML
    private TextField EditEmail;

    @FXML
    private TableColumn<Employee, String> EditEmailCol;
    
    @FXML
    private TextField HireEmail;
    
    @FXML
    private TableColumn<Employee, String> email;

    @FXML
    private TableColumn<Employee, String> fireemail;

    @FXML
    private TextField EditPhoneNo;

    @FXML
    private TableColumn<Employee, String> EditPhoneNoCol;

    @FXML
    private TextField HirePhoneNo;

    @FXML
    private TableColumn<Employee, String> phoneno;

    @FXML
    private TableColumn<Employee, String> firephoneno;

    @FXML
    private Button ApplyHire;

    @FXML
    private DatePicker Birthdate;

    @FXML
    private Button ClickFire;

    @FXML
    private DatePicker EditBirthDate;

    @FXML
    private TableColumn<Employee, Date> EditBirthDateCol;

    @FXML
    private TextField EditFirstName;

    @FXML
    private TableColumn<Employee, String> EditFirstNameCol;

    @FXML
    private TableColumn<Employee, Integer> EditIdCol;

    @FXML
    private TextField EditLastName;

    @FXML
    private TextField EditPassword;

    @FXML
    private TableColumn<Employee, String> EditPasswordCol;

    @FXML
    private ChoiceBox<String> EditRole;

    @FXML
    private TableColumn<Employee, String> EditRoleCol;

    @FXML
    private DatePicker EditStartDate;

    @FXML
    private TableColumn<Employee, Date> EditStartDateCol;

    @FXML
    private TableColumn<Employee, String> EditSurnameCol;

    @FXML
    private TextField EditUsername;

    @FXML
    private TableColumn<Employee, String> EditUsernameCol;

    @FXML
    private TextField HireFirstName;

    @FXML
    private TextField HireLastName;

    @FXML
    private TextField HirePassword;

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
    private ChoiceBox<String> HireRoleChoice;

    @FXML
    private Button SearchEditClick;

    @FXML
    private Button SearchFireClick;

    @FXML
    private TextField SearchPersonnelEdit;

    @FXML
    private TextField SearchPersonnelFire;

    @FXML
    private DatePicker StartDate;

    @FXML
    private Button backToManagerMenu;

    @FXML
    private Button backToManagerMenu1;

    @FXML
    private Button backToManagerMenu2;

    @FXML
    private Button backToManagerMenu21;

    @FXML
    private TableColumn<Employee, Date> birthdate;

    @FXML
    private TableColumn<Employee, Integer> employeeid;

    @FXML
    private TableColumn<Employee, Date> firebirthdate;

    @FXML
    private TableColumn<Employee, Integer> fireid;

    @FXML
    private TableColumn<Employee, String> firename;

    @FXML
    private TableColumn<Employee, String> firepassword;

    @FXML
    private TableColumn<Employee, String> firerole;

    @FXML
    private TableColumn<Employee, Date> firestartdate;

    @FXML
    private TableColumn<Employee, String> firesurname;

    @FXML
    private TableColumn<Employee, String> fireusername;

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
    private TableView<Employee> personnelAction2;

    @FXML
    private TableColumn<Employee, String> role;

    @FXML
    private TableColumn<Employee, Date> startdate;

    @FXML
    private TableColumn<Employee, String> username;

    private Employee currentEmployee;



    @FXML 
    public void initialize(){
        HireRoleChoice.setItems(FXCollections.observableArrayList("Admin", "Manager", "Cashier"));
        EditRole.setItems(FXCollections.observableArrayList("Admin", "Manager", "Cashier"));
        configureTableColumns(personnelAction);
        configureTableColumns(personnelAction1);
        configureTableColumns(personnelAction2);

        loadPersonnelTable();
    }

    private void configureTableColumns(TableView<Employee> tableView){
        employeeid.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("surname"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        birthdate.setCellValueFactory(new PropertyValueFactory<>("dateOfStart"));
        startdate.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
    }

    private void loadPersonnelTable(){
        //deneme
        Employee employee1 = new Employee("Kran", "Tug" ,"karo","Manager");
        Employee employee2 = new Employee("Kran2", "Tug2" ,"karo2","Manager2");
        ArrayList<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);

        ObservableList<Employee> employee = FXCollections.observableArrayList(employeeList);
        personnelAction.setItems(employee);
    }


    @FXML
    @SuppressWarnings("unused")
    private void firesearchemployee(){

        String searchText = SearchPersonnelFire.getText();

        if(searchText.isEmpty()){
            System.err.println("Please enter a value to search!");
        }
        //databaseden employee çekme
        // Employee employee = EmployeeDatabase.getEmployeeUsername(searchText);
        /*
        if (employee == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Employee Not Found!");
            alert.setContentText("No employee found with the username: " + searchText);
            alert.showAndWait();
        return;
        }

        firesearchloadtable(employee);
      */  
    }


    //bi üstteki fonksiyonda kullanıldı
    /*
    private void firesearchloadtable(Employee employee){
        ArrayList<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        ObservableList<Employee> fireemployee = FXCollections.observableArrayList(employeeList);
        personnelAction1.setItems(fireemployee);
    }


    //onaction ile tuşa eklenicek
    @FXML 
    @SuppressWarnings("unused")
    private void fireemployee(){
        DatabaseEmployee.deleteEmployee(SearchPersonnelFire.getText());
        
    }
    
    */
    

    


    @FXML
    @SuppressWarnings("unused")
    private void editsearchemployee(){

        String searchText = SearchPersonnelEdit.getText();

        if(searchText.isEmpty()){
            System.err.println("Please enter a value to search!");
        }
        //databaseden employee çekme
        // Employee employee = EmployeeDatabase.getEmployeeUsername(searchText);
        /*
        if (employee == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Employee Not Found!");
            alert.setContentText("No employee found with the username: " + searchText);
            alert.showAndWait();
        return;
        }

        editsearchloadtable(employee);
        */
    }

    /*
    private void editsearchloadtable(Employee employee){

        ArrayList<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        ObservableList<Employee> editemployee = FXCollections.observableArrayList(employeeList);
        personnelAction2.setItems(editemployee);
    }
    */

    
    //onaction ile tuşa eklenicek
    /*
    @FXML 
    @SuppressWarnings("unused")
    private void editemployee(){
        Employee employee = DatabaseEmployee.(SearchPersonnelEdit.getText());

        DatabaseEmployee.updateEmployee(employee, "name", EditFirstName.getText());
        DatabaseEmployee.updateEmployee(employee, "surname", EditLastName.getText());
        DatabaseEmployee.updateEmployee(employee, "username", EditUsername.getText());
        DatabaseEmployee.updateEmployee(employee, "password", EditPassword.getText());
        DatabaseEmployee.updateEmployee(employee, "email", EditEmail.getText());
        DatabaseEmployee.updateEmployee(employee, "phoneNo", EditPhoneNo.getText());
        DatabaseEmployee.updateEmployee(employee, "role", EditRole.getText());              //choicebox
        DatabaseEmployee.updateEmployee(employee, "dateOfBirth", EditBirthDate.getText()); //datepicker
        DatabaseEmployee.updateEmployee(employee, "dateOfStart", EditStartDate.getText());  //datepicker
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
