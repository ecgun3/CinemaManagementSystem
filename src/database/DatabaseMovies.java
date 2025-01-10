package database;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import application.Movie;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseMovies implements DatabaseSource{
    
    private Connection connection; 

    //Database'e bağlan
    public void connectDatabase(){

        try{

            connection = DriverManager.getConnection(url, username, password);

        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

    }

    //Database query hazırlama
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

    //Database query execute
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


    public ArrayList<Movie> getMovieGenre(String genre){

        ArrayList<Movie> movies = new ArrayList<Movie>();
        String query = "SELECT * FROM movies WHERE genre LIKE ?";
        Movie movie;

        try(PreparedStatement prStatement = connection.prepareStatement(query)){

            prStatement.setString(1, "%" + genre + "%");

            ResultSet rs = prStatement.executeQuery();

            while(rs != null && rs.next()){

                movie = new Movie();
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

    public Movie get1Movie(int id){

        Movie movie = new Movie();
        String query = "SELECT * FROM movies WHERE idMovie = '" + id + "'";

        try{

            ResultSet rs = executeQuery(query);

            if (rs != null && rs.next()) {
                movie.setId(rs.getInt("idMovie"));
                movie.setTitle(rs.getString("title"));
                movie.setYear(rs.getInt("year"));
                movie.setGenre(rs.getString("genre"));
                movie.setSummary(rs.getString("summary"));
                movie.setPosterImage(rs.getBytes("poster_url"));
            }
            else{
                System.out.println("No hall found with id: " + id);
            }

                
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return movie;
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
        String query = "UPDATE employee SET " + column + " = ? WHERE idMovie = ? ";
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

    public void deleteMovie(Movie movie){

        int ID = movie.getId();
        String query = "DELETE FROM movies WHERE idMovie = ?";
        try(PreparedStatement pStatement = connection.prepareStatement(query)){
            pStatement.setInt(1, ID);
            if (pStatement.executeUpdate() > 0)
                System.out.println("Movie deleted successfully!");
            else
                System.out.println("Delete failed!");
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

    }

    public void disconnectDatabase(){

        try{
            if(connection!=null && !connection.isClosed())
                connection.close();
            else
                System.out.println("Disconnection error!");
        }
        catch(SQLException sqlException){
        }
    }
}
