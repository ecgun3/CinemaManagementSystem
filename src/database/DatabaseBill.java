package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.util.ArrayList;

import application.OrderItem;
import application.Bills;
import application.ItemBills;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseBill implements DatabaseSource{
    
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

    public void setBill(ArrayList<ItemBills> itemBills, Bills bill){
        try{
            try{
                connection.setAutoCommit(false); // because we execute more than one sql operation at one time

                String query = "INSERT INTO bills (time, customer, customerBirthDate, sessionId, total_amount, tax_amount)" + 
                                "VALUES (NOW(),?,?,?,?,?)";

                PreparedStatement pStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pStatement.setString(1, bill.getCustomer());
                pStatement.setDate(2,Date.valueOf(bill.getCustomerBirth()));
                pStatement.setInt(3,bill.getSessionId());
                pStatement.setDouble(4,bill.getTotal_amount());
                pStatement.setDouble(5,bill.getTax_amount());
                pStatement.executeUpdate();

                ResultSet generatedKeys = pStatement.getGeneratedKeys();
                int billId = 0;
                if (generatedKeys.next()) {
                    billId = generatedKeys.getInt(1);
                }

                String query2 = "INSERT INTO item_bill (bill_id, item_type, item_name, item_quantity, item_amount, " + 
                                "item_tax) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pStatement2 = connection.prepareStatement(query2);

                for (ItemBills item : itemBills) {
                    pStatement2.setInt(1, billId);
                    pStatement2.setString(2, item.getItem_type());
                    pStatement2.setString(3, item.getItem_name());
                    pStatement2.setInt(4, item.getItem_quantity());
                    pStatement2.setDouble(5, item.getItem_amount());
                    pStatement2.setDouble(6, item.getItem_tax());
                    pStatement2.addBatch();
                }

                pStatement2.executeBatch(); // execute more than one sql operation at one time

                connection.commit(); //make permanent the executions
            }
            catch(SQLException sqlException){
                connection.rollback();
                sqlException.printStackTrace();
            }
            finally{
            connection.setAutoCommit(true); // Default setting
            }
        }catch(SQLException sqlException){
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

