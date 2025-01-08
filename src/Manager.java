import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class Manager extends Employee {

    public Manager(int employeeId, String firstName, String lastName, String username, String password) {
        super(employeeId, firstName, lastName, username, password, "Manager");
    }


    public void viewInventory(/*DatabaseConnector dbConnector*/){
        /*

         try {
            String query = "SELECT * FROM products";
            ResultSet rs = dbConnector.executeQuery(query);

            while (rs.next()) {
                System.out.println("Product ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name");
                System.out.println("Price: " + rs.getString("price"));
                System.out.println("Stock: " + rs.getString("stock"));
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Employe view error: ", e);
        }

        */ 
    }


    public void increaseInventory(int id, int quantity/*,DatabaseConnector dbConnector*/){
        /* 
         String query = "UPDATE products SET stock = stock + ? WHERE id = ?";
        try (PreparedStatement pstmt = dbConnector.prepareStatement(query)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error increasing stock: ", e);
        */
    }


    public void viewEmployees(/*DatabaseConnector dbConnector*/){

         /*

         try {
            String query = "SELECT * FROM employees";
            ResultSet rs = dbConnector.executeQuery(query);

            while (rs.next()) {
                System.out.println("Employee ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Role: " + rs.getString("role"));
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Employe view error ", e);
        }

        */

    }


    public void editEmployee(/*DatabaseConnector dbConnector,*/int employeeId, String firstName, String lastName, String username, String password, String role){
        /*
        String query = "UPDATE employees SET first_name = ?, last_name = ?, username = ?, password = ?, role = ? WHERE id = ?";
        try (PreparedStatement pstmt = dbConnector.prepareStatement(query)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.setString(5, role);
            pstmt.executeUpdate();

            return;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating employee", e);
        }
        */
    }

    public void hireEmployee(/*DatabaseConnector dbConnector,*/String firstName, String lastName, String username, String password, String role){
         /*
        String query = "INSERT INTO employees SET first_name, last_name, username, password, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = dbConnector.prepareStatement(query)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.setString(5, role);
            pstmt.executeUpdate();

            return;
        } catch (SQLException e) {
            throw new RuntimeException("Error hiring employee", e);
        }
        */
    }

    public void fireEmployee(/*DatabaseConnector dbConnector,*/int employeeId){
        /*
       String query = "DELETE FROM employees WHERE id = ?";
       try (PreparedStatement pstmt = dbConnector.prepareStatement(query)) {
           pstmt.setInt(1, employeeId);
           pstmt.executeUpdate();

           return;
       } catch (SQLException e) {
           throw new RuntimeException("Error firing employee", e);
       }
       */
   }

    public double getTicketPrice(/*DatabaseConnector dbConnector*/){
        /*
        try {
            String query = "SELECT ticket_price FROM settings ";
            ResultSet rs = dbConnector.executeQuery(query);
            if(rs.next())
                return rs.getDouble("ticket_price");
        } catch (SQLException e) {
             throw new RuntimeException("getting ticket_price error", e);
        }
        return 0.0;
        */
    }


    public void editTicketPrice(double ticketPrice/*,DatabaseConnector dbConnector*/){
        /*
        String query = "UPDATE settings SET ticket_price = ?";
        try (PreparedStatement pstmt = dbConnector.prepareStatement(query)) {
            pstmt.setDouble(1, newPrice);
            pstmt.executeUpdate();

            return;
        } catch (SQLException e) {
            throw new RuntimeException("Error editing ticket_price", e);
        }
        */
    }


    public void editProductPrice(int id, Double productPrice/*,DatabaseConnector dbConnector*/){
        /*
        String query = "UPDATE products SET product_price = ? WHERE id = ?";
        try (PreparedStatement pstmt = dbConnector.prepareStatement(query)) {
            pstmt.setInt(2, productId);
            pstmt.setDouble(1, newPrice);
            pstmt.executeUpdate();

            return;
        } catch (SQLException e) {
            throw new RuntimeException("Error editing product_price", e);
        }
        */

    }
 

    public void viewRevenue(/*DatabaseConnector dbConnector,*/){
    
    }




    


    

}
