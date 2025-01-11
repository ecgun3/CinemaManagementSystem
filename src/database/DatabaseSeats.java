package database;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import application.Halls;
import application.Movie;
import application.Seat;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSeats implements DatabaseSource{
    
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

    public ArrayList<Seat> getSeats(int id){

        String query = "SELECT * FROM seats WHERE session_id = ?";
        ArrayList<Seat> seats = new ArrayList<>();

        try(PreparedStatement prStatement = connection.prepareStatement(query)){
            
            prStatement.setInt(1,id);

            ResultSet rs = prStatement.executeQuery();

            while (rs.next()){
                Seat seat = new Seat();

                seat.setId(rs.getInt("id"));
                seat.setSeat(rs.getString("seatNo"));
                seat.setSession(rs.getInt("session_id"));
                seat.setOccupied(rs.getBoolean("taken"));
                seats.add(seat);

            }
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } 

        return seats;
    }

    public Boolean checkSeat(int id, String seatNo){
        
        String query = "SELECT * FROM seats WHERE session_id = ? AND seatNo = ?";
        Boolean bl = false;

        try(PreparedStatement prStatement = connection.prepareStatement(query)){
            
            prStatement.setInt(1,id);
            prStatement.setString(2,seatNo);

            ResultSet rs = prStatement.executeQuery();

            while (rs.next()){ 
                bl = rs.getBoolean("taken");
            }
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } 
    
        return bl;
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
