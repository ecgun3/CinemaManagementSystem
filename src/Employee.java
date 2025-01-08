public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String role;

    public Employee(int employeeId, String firstName, String lastName, String username, String password, String role) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    
    public void setEmployeeId(int employeeId)  {
        this.employeeId = employeeId;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    protected void setUsername(String username) {
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

    protected void setRole(String role) {
        this.role = role;
    }

}