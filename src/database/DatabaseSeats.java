package database;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import application.Halls;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSeats implements DatabaseSource {

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

    public ArrayList<Halls> viewHalls(){
        
        ArrayList<Halls> halls = new ArrayList<Halls>();
        String query = "SELECT * FROM products";

        try{
            ResultSet rs = executeQuery(query);

            while (rs.next()){ 

                Halls hall = new Halls();

                hall.setId_halls(rs.getInt("idhalls"));
                hall.setName(rs.getString("name"));
                hall.setCapacity(rs.getInt("capacity"));
                halls.add(hall);
            }
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    
        return halls;
    }

}