package database;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

import application.Session;
import application.Halls;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSession implements DatabaseSource{
    
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
    
    public ArrayList<Session> getSessions(){

        ArrayList<Session> sessions = new ArrayList<Session>();
        String query = "SELECT * FROM session";

        try{

            ResultSet rs = executeQuery(query);

            while(rs != null && rs.next()){

                Session ses = new Session();

                ses.setId(rs.getInt("idSession"));
                ses.setAvailableSeats(rs.getInt("available_seats"));

                String dateTime = rs.getString("session_time");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                ses.setDateTime(LocalDateTime.parse(dateTime,formatter));

                DatabaseHalls dataH = new DatabaseHalls();
                dataH.connectDatabase();
                ses.setHall(dataH.getHall(rs.getInt("hallId")));
                dataH.disconnectDatabase();

                DatabaseMovies dataM = new DatabaseMovies();
                dataM.connectDatabase();
                ses.setMovie(dataM.get1Movie(rs.getInt("movieId")));
                dataM.disconnectDatabase();

                sessions.add(ses);

            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return sessions;
    }

    public ArrayList<String> getSessionHours(int movieId){

        ArrayList<String> sessions = new ArrayList<String>();
        String query = "SELECT DISTINCT session_time FROM session WHERE movieId = ?";

        try(PreparedStatement prStatement = connection.prepareStatement(query)){

            prStatement.setInt(1, movieId);

            ResultSet rs = prStatement.executeQuery();

            while(rs != null && rs.next()){

                String ses = "";

                String dateTime = rs.getString("session_time");
                ses= dateTime.substring(11, 16);
                sessions.add(ses);

            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return sessions;
    }

    public ArrayList<String> getHallNames(int movieId){

        ArrayList<String> sessions = new ArrayList<String>();
        String query = "SELECT DISTINCT hallId FROM session WHERE movieId = ?";

        try(PreparedStatement prStatement = connection.prepareStatement(query)){

            prStatement.setInt(1, movieId);

            ResultSet rs = prStatement.executeQuery();

            while(rs != null && rs.next()){

                String ses = "";
                DatabaseHalls dataH = new DatabaseHalls();
                dataH.connectDatabase();
                ses=dataH.getHallNames(rs.getInt("hallId"));
                
                if(!ses.equals(""))
                    sessions.add(ses);

            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return sessions;
    }

    public ArrayList<String> getSessionDates(int movieId){

        ArrayList<String> sessions = new ArrayList<String>();
        String query = "SELECT * FROM session WHERE movieId = ?";

        try(PreparedStatement prStatement = connection.prepareStatement(query)){

            prStatement.setInt(1, movieId);

            ResultSet rs = prStatement.executeQuery();

            while(rs != null && rs.next()){

                String ses = "";
                String dateTime = rs.getString("session_time");
                ses= dateTime.substring(0, 10);
                sessions.add(ses);

            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return sessions;
    }

    public Session specsSession(String date, String hall, String time){

        String session = date + " " + time;
        int hallId;
        Session ses = new Session();

        if(hall=="Hall_A")
            hallId=1;
        else
            hallId=2;

        String query = "SELECT * FROM session WHERE session_time LIKE ? AND hallId = ?";

        try(PreparedStatement prStatement = connection.prepareStatement(query)){

            prStatement.setString(1, "'%" + session + "%'");
            prStatement.setInt(2, hallId);

            ResultSet rs = executeQuery(query);

            ses.setId(rs.getInt("idSession"));
            ses.setAvailableSeats(rs.getInt("available_seats"));

            String dateTime = rs.getString("session_time");
            ses.setDateTime(LocalDateTime.parse(dateTime));

            DatabaseHalls dataH = new DatabaseHalls();
            ses.setHall(dataH.getHall(rs.getInt("hallId")));
            dataH.disconnectDatabase();

            DatabaseMovies dataM = new DatabaseMovies();
            ses.setMovie(dataM.get1Movie(rs.getInt("movieId")));
            dataM.disconnectDatabase();

        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return ses;
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
