package dao;

import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class CustomersQuery {
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static void select() throws SQLException {
        customers.clear();
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("CUSTOMER_ID");
            String name = rs.getString("CUSTOMER_NAME");
            String address = rs.getString("ADDRESS");
            String postal_Code = rs.getString("POSTAL_CODE");
            String phone = rs.getString("PHONE");
            int division_ID = rs.getInt("DIVISION_ID");
            customers.add(new Customer(id,name,address,postal_Code,phone,division_ID));
        }
    }
    private static void select(String searchText) throws SQLException {
        customers.clear();
        if(!searchText.isEmpty()) {
            try {
                int searchID = Integer.parseInt(searchText);
                String sql = "SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
                PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
                ps.setInt(1,searchID);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    int id = rs.getInt("CUSTOMER_ID");
                    String name = rs.getString("CUSTOMER_NAME");
                    String address = rs.getString("ADDRESS");
                    String postal_Code = rs.getString("POSTAL_CODE");
                    String phone = rs.getString("PHONE");
                    int division_ID = rs.getInt("DIVISION_ID");
                    customers.add(new Customer(id, name, address, postal_Code, phone, division_ID));
                }
            }
            catch(NumberFormatException ex){
                String sql = "SELECT * FROM CUSTOMERS WHERE CUSTOMER_NAME LIKE ?";
                PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
                ps.setString(1,"%"+searchText+"%");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("CUSTOMER_ID");
                    String name = rs.getString("CUSTOMER_NAME");
                    String address = rs.getString("ADDRESS");
                    String postal_Code = rs.getString("POSTAL_CODE");
                    String phone = rs.getString("PHONE");
                    int division_ID = rs.getInt("DIVISION_ID");
                    customers.add(new Customer(id, name, address, postal_Code, phone, division_ID));
                }
            }
        }
    }
    public static int insert(int ID, String name,String address,String postal,String phone,int divID) throws SQLException {
        DBConnection.openConnection();
        ZonedDateTime now = ZonedDateTime.now();
        Timestamp nowTimestamp = Timestamp.from(now.toInstant());
        String sql = "INSERT INTO CUSTOMERS (CUSTOMER_ID , " + //1
                "CUSTOMER_NAME," + //2
                "ADDRESS," + //3
                "POSTAL_CODE," + //4
                "PHONE," + //5
                "CREATE_DATE," + //6
                "CREATED_BY," + //7
                "LAST_UPDATE," + //8
                "LAST_UPDATED_BY," + //9
                "DIVISION_ID) " + //10
                "VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, ID);
        ps.setString(2, name);
        ps.setString(3, address);
        ps.setString(4, postal);
        ps.setString(5, phone);
        ps.setTimestamp(6, nowTimestamp);
        ps.setString(7, LoginController.username);
        ps.setTimestamp(8, nowTimestamp);
        ps.setString(9, LoginController.username);
        ps.setInt(10, divID);
        int rowsAffected = ps.executeUpdate();
        DBConnection.closeConnection();
        return rowsAffected;
    }
    public static int update(int ID, String name,String address,String postal,String phone,int divID) throws SQLException {
        DBConnection.openConnection();
        ZonedDateTime now = ZonedDateTime.now();
        Timestamp nowTimestamp = Timestamp.from(now.toInstant());
        String sql = "UPDATE CUSTOMERS " +
                "SET CUSTOMER_NAME = ?, " + //1
                "ADDRESS = ?," + //2
                "POSTAL_CODE = ?," + //3
                "PHONE = ?," + //4
                "LAST_UPDATE = ?," + //5
                "LAST_UPDATED_BY = ?," + //6
                "DIVISION_ID = ? " + //7
                "WHERE CUSTOMER_ID = ?;"; //8
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setTimestamp(5, nowTimestamp);
        ps.setString(6, LoginController.username);
        ps.setInt(7, divID);
        ps.setInt(8, ID);

        int rowsAffected = ps.executeUpdate();
        DBConnection.closeConnection();
        return rowsAffected;
    }
    public static int delete(int ID) throws SQLException {
        DBConnection.openConnection();
        String sql = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,ID);
        int rowsAffected = ps.executeUpdate();
        DBConnection.closeConnection();
        return rowsAffected;
    }
    private static int findNextCustomerID() throws SQLException {
        String sql = "SELECT MAX(Customer_ID) + 1 AS NextID FROM CUSTOMERS";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            return rs.getInt("NextID");
        }
        else {
            DBConnection.closeConnection();
            return 1;
        }
    }
    public static int getNextCustomerID() throws SQLException {
        DBConnection.openConnection();
        int nextID = findNextCustomerID();
        DBConnection.closeConnection();
        return nextID;
    }
    public static ObservableList<Customer> getCustomers() throws SQLException {
        DBConnection.openConnection();
        select();
        DBConnection.closeConnection();
        return customers;
    }
    public static ObservableList<Customer> getCustomers(String searchText) throws SQLException {
        DBConnection.openConnection();
        if(searchText.isEmpty()) {
            select();
        }
        else{
            select(searchText);
        }
        DBConnection.closeConnection();
        return customers;
    }

}