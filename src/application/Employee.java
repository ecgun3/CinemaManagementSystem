package application;
import java.sql.Date;

public class Employee {
    protected int employeeID;

    protected String username;
    protected String password;
    protected String role;
    protected String name;
    protected String surname;
    protected String phoneNo;
    protected String email;

    protected Date dateOfBirth;
    protected Date dateOfStart;

    public Employee(){
        this.employeeID = 0;
        this.username = null;
        this.password = null;
        this.role = null;
        this.name = null;
        this.surname = null;
        this.phoneNo = null;
        this.dateOfBirth = null;
        this.dateOfStart = null;
        this.email = null;
    }
    
    public Employee(int employeeID, String username, String password, String role, String name, String surname,
            String phoneNo, String email, Date dateOfBirth, Date dateOfStart) {
        this.employeeID = employeeID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.phoneNo = phoneNo;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.dateOfStart = dateOfStart;
    }
    
    public int getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public Date getDateOfStart() {
        return dateOfStart;
    }
    public void setDateOfStart(Date dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

}