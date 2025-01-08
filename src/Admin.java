public class Admin extends Employee {

    public Admin(int employeeId, String firstName, String lastName, String username, String password) {
        super(employeeId, firstName, lastName, username, password, "Admin");
    }

    public void addNewMovie(/*,DatabaseConnector dbConnector,*/String title, String genre, String summary, String posterPath){
         /*
        String query = "INSERT INTO movies SET title, genre, summary, posterPath) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = dbConnector.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, genre);
            pstmt.setString(3, summary);
            pstmt.setString(4, posterPath);
            pstmt.executeUpdate();

            return;
        } catch (SQLException e) {
            throw new RuntimeException("Error adding a new movie", e);
        }
        */

    }


    public void updateMovie(/*,DatabaseConnector dbConnector,*/String title, String genre, String summary, String posterPath, int movieId){
        /*
        String query = "UPDATE movies SET title = ?, genre = ?, summary = ?, posterPath = ? WHERE id = ?";
        try (PreparedStatement pstmt = dbConnector.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, genre);
            pstmt.setString(3, summary);
            pstmt.setString(4, posterPath);
            pstmt.setString(5, movieId);
            pstmt.executeUpdate();

            return;
        } catch (SQLException e) {
            throw new RuntimeException("Error updatinge movie", e);
        }
        */

    }


    public void scheduleMovie(int movieId, String hallName, String sessionTime){

    }


    public void ticketCancellation(int ticketId){

    }
    
}
