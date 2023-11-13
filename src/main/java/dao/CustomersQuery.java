package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private static int findNextCustomerID() throws SQLException {
        String sql = "SELECT MAX(Customer_ID) + 1 AS NextID FROM CUSTOMERS";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            return rs.getInt("NextID");
        }
        else {
            DBConnection.closeConnection();
            return -1;
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
}
