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

    

}