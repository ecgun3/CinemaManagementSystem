import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private final String url = "jdbc:mysql://localhost:3306/cinema";
    private final String username = "root";
    private final String password = "1632";

    private Connection connection; 

    public void connectDatabase(){

        try{
            connection = DriverManager.getConnection(url, username, password);
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

    }

    public ResultSet executeQuery(String query){

        try{
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }

    public int executeUpdate(String query){

        try(Statement statement = connection.createStatement()){

            return statement.executeUpdate(query);
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            return -1;
        }
    }

    public ArrayList<Movie> getMovies(){

        ArrayList<Movie> movies = new ArrayList<Movie>();
        String query = "SELECT * FROM movies";

        try{

            ResultSet rs = executeQuery(query);

            while(rs != null && rs.next()){

                Movie movie = new Movie();
                
                movie.setId(rs.getInt("idMovie"));
                movie.setTitle(rs.getString("title"));
                movie.setYear(rs.getInt("year"));
                movie.setGenre(rs.getString("genre"));
                movie.setSummary(rs.getString("summary"));
                movie.setPosterImage(rs.getBytes("poster_url"));
                movies.add(movie);
            }
        }

        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return movies;
    }

    public ArrayList<String> getGenres(){

        ArrayList<String> genres = new ArrayList<String>();
        String query = "SELECT DISTINCT genre FROM movies";

        try{

            ResultSet rs = executeQuery(query);

            while(rs != null && rs.next()){

                String str = rs.getString("genre");

                genres.add(str);
            }
        }

        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return genres;
    }

    public ArrayList<Movie> getMovieGenre(String genre){

        ArrayList<Movie> movies = new ArrayList<Movie>();
        String query = "SELECT * FROM movies";

        try{

            ResultSet rs = executeQuery(query);

            while(rs != null && rs.next()){

                Movie movie = new Movie();

                if(rs.getString("genre").equalsIgnoreCase(genre)){

                    movie.setId(rs.getInt("idMovie"));
                    movie.setTitle(rs.getString("title"));
                    movie.setYear(rs.getInt("year"));
                    movie.setGenre(rs.getString("genre"));
                    movie.setSummary(rs.getString("summary"));
                    movie.setPosterImage(rs.getBytes("poster_url"));
                    movies.add(movie);

                }
                
            }
        }

        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return movies;
    }

    public ArrayList<Movie> getMoviePartial(String str){

        ArrayList<Movie> movies = new ArrayList<Movie>();
        String query = "SELECT * FROM movies WHERE title LIKE '%" + str + "%'";

        try{

            ResultSet rs = executeQuery(query);

            while(rs != null && rs.next()){

                Movie movie = new Movie();

                movie.setId(rs.getInt("idMovie"));
                movie.setTitle(rs.getString("title"));
                movie.setYear(rs.getInt("year"));
                movie.setGenre(rs.getString("genre"));
                movie.setSummary(rs.getString("summary"));
                movie.setPosterImage(rs.getBytes("poster_url"));
                movies.add(movie);

            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return movies;
    }

    public ArrayList<Movie> getMovieTitle(String str){

        ArrayList<Movie> movies = new ArrayList<Movie>();
        String query = "SELECT * FROM movies WHERE title = '" + str + "'";

        try{

            ResultSet rs = executeQuery(query);

            while(rs != null && rs.next()){

                Movie movie = new Movie();

                movie.setId(rs.getInt("idMovie"));
                movie.setTitle(rs.getString("title"));
                movie.setYear(rs.getInt("year"));
                movie.setGenre(rs.getString("genre"));
                movie.setSummary(rs.getString("summary"));
                movie.setPosterImage(rs.getBytes("poster_url"));
                movies.add(movie);

            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return movies;
    }

    public void insertMovie(Movie movie){

        String query = "INSERT INTO movies (title, year, genre, summary, poster_url) VALUES (?,?,?,?,?)";

        try(PreparedStatement pStatement = connection.prepareStatement(query)){
            
            pStatement.setString(1, movie.getTitle());
            pStatement.setInt(2,movie.getYear());
            pStatement.setString(3,movie.getGenre());
            pStatement.setString(4,movie.getSummary());
            pStatement.setBytes(5,movie.getPosterImage());

            if (pStatement.executeUpdate() > 0)
                System.out.println("Movie inserted successfully!");
            else
                System.out.println("Insert failed!");
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void updateMovie(Movie movie, String column, String value){

        int ID = movie.getId();
        String query = "UPDATE employee SET " + column + " = ? WHERE employee_ID = ? ";
        try(PreparedStatement pStatement = connection.prepareStatement(query);){

            pStatement.setString(1, value);
            pStatement.setInt(2, ID);

            if (pStatement.executeUpdate() > 0)
                System.out.println("Movie updated successfully!");
            else
                System.out.println("Update failed.");

        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

    }

    public ArrayList<Product> viewInventory(){
        
        ArrayList<Product> products = new ArrayList<Product>();
        String query = "SELECT * FROM products";

        try{
            ResultSet rs = executeQuery(query);

            while (rs.next()){ 

                Product product = new Product();

                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setType(rs.getString("type"));
                products.add(product);
            }
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    
        return products;
    }


    public void updateInventory(Product product, int quantity){

        int ID = product.getId();
        String query = "UPDATE products SET stock = stock + ? WHERE id = ?";

        try (PreparedStatement pStatement = connection.prepareStatement(query)) {
            pStatement.setInt(1, quantity);
            pStatement.setInt(2, ID);

            if(pStatement.executeUpdate() > 0)
                System.out.println("Stock Updated Successfully!");
            else   
                System.out.println("Stock Update Failed.");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();   
        }
    }

    public void editProductPrice(Product product, Double newprice){

        int ID = product.getId();
        String query = "UPDATE products SET product_price = ? WHERE id = ?";
        
        try (PreparedStatement pStatement = connection.prepareStatement(query)) {
            pStatement.setDouble(1, newprice);
            pStatement.setInt(2, ID);

            if (pStatement.executeUpdate() > 0)
            System.out.println("Product price edited successfully!");
        else
            System.out.println("Product Price edit failed.");

        } catch(SQLException sqlException){
            sqlException.printStackTrace();

        }

    }

    public ArrayList<Employee> viewEmployees(){

        ArrayList<Employee> employees = new ArrayList<Employee>();
        String query = "SELECT * FROM employee";
    
        try {

            ResultSet rs = executeQuery(query);

            while (rs.next()) {

                Employee employee = new Employee();

                employee.setId(rs.getInt("idEmployee"));
                employee.setFirstName(rs.getString("name"));
                employee.setLastName(rs.getString("surname"));
                employee.setUsername(rs.getString("username"));
                employee.setRole(rs.getString("role"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            }
        
        return employees;
        }

    public void editEmployee(Employee employee, String firstName, String lastName, String username, String password, String role){
        
        int ID = employee.getId();
        String query = "UPDATE employee SET name = ?, surname = ?, username = ?, password = ?, role = ? WHERE idEmployee = ?";
        
        try (PreparedStatement pStatement = connection.prepareStatement(query)) {
            pStatement.setString(1, firstName);
            pStatement.setString(2, lastName);
            pStatement.setString(3, username);
            pStatement.setString(4, password);
            pStatement.setString(5, role);
            pStatement.setInt(6, ID);

            if(pStatement.executeUpdate() > 0)
                System.out.println("Employee Updated Successfully!");
            else   
                System.out.println("Employee update failed");

        } catch (SQLException sqlException	) {
                sqlException.printStackTrace();
            }
        }

    public void hireEmployee(Employee employee){
        
        String query = "INSERT INTO employee (name, surname, username, password, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pStatement = connection.prepareStatement(query)) {

            pStatement.setString(1, employee.getFirstName);
            pStatement.setString(2, employee.getLastName);
            pStatement.setString(3, employee.getUsernamesername);
            pStatement.setString(4, employee.getPassword);
            pStatement.setString(5, employee.setRole);

              if (pStatement.executeUpdate() > 0)
                System.out.println("Employee hired successfully!");
            else
                System.out.println("Employee hire failed.");
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void fireEmployee(Employee employee){
        int ID = employee.getId();
        String query = "DELETE FROM employees WHERE id = ?";

        try (PreparedStatement pStatement = connection.prepareStatement(query)) {
           pStatement.setInt(1, ID);

           if (pStatement.executeUpdate() > 0)

           System.out.println("Employee deleted successfully!");
       else
           System.out.println("Employee delete failed!");

        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

    }


    public void editTicketPrice(){

    }

    public double[] viewRevenue(){
        
        double[] revenue = new double[3];
        String query = "SELECT SUM(total_amount) AS totalAmount, SUM(tax_amount) AS totalTax FROM bills";

        try (PreparedStatement pStatement = connection.prepareStatement(query)) {

            ResultSet rs = executeQuery(query);

            if(rs.next()){
                double totalAmount = rs.getDouble("totalAmount");
                double totalTax = rs.getDouble("totalTax");
                double profit = totalAmount - totalTax;

                revenue[0] = totalAmount;
                revenue[1] = totalTax;
                revenue[2] = profit;
            }

        } catch(SQLException sqlException){
             sqlException.printStackTrace();
        }

        return revenue;
    }

        

    
}