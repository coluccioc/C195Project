package dao;

import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Customer;
import model.FirstLevelDivision;
import model.GroupByTotals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class CustomersQuery {
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static ObservableList<GroupByTotals> customerTotals = FXCollections.observableArrayList();

    /**
     * select class gets All Customers and adds them to an ObservableList
     * @throws SQLException
     */
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
    /**
     * select class gets all customers that match search criteria and adds them to an ObservableList
     * @param searchText given ID or substring to search for a match
     * @throws SQLException
     */
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

    /**
     * Selects customer based on given customerID (Unique)
     * @param ID given ID
     * @return Customer
     * @throws SQLException
     */
    public static Customer selectByCustomerID(int ID) throws SQLException {
        DBConnection.openConnection();
        String sql = "SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,ID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String name = rs.getString("CUSTOMER_NAME");
        String address = rs.getString("ADDRESS");
        String postal_Code = rs.getString("POSTAL_CODE");
        String phone = rs.getString("PHONE");
        int division_ID = rs.getInt("DIVISION_ID");
        Customer customer = new Customer(ID,name,address,postal_Code,phone,division_ID);
        DBConnection.closeConnection();
        return customer;
    }

    /**
     * Selects aggregate of number of customers in First Level Divisions
     * @throws SQLException
     */
    public static void selectCustomerFLDTotals() throws SQLException {
        customerTotals.clear();
        DBConnection.openConnection();
        String sql = "SELECT DIVISION_ID, COUNT(*) AS TOTAL FROM CUSTOMERS GROUP BY DIVISION_ID";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String group = FirstLevelDivisionsQuery.selectGivenFLD(rs.getInt("DIVISION_ID")).toString();
            int total = rs.getInt("TOTAL");
            customerTotals.add(new GroupByTotals(group,total));
        }
    }

    /**
     * Adds a new Customer Record to the database
     * @param ID
     * @param name
     * @param address
     * @param postal
     * @param phone
     * @param divID
     * @return rowsAffected
     * @throws SQLException
     */
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
    /**
     * Updates existing Customer Record in the database
     * @param ID
     * @param name
     * @param address
     * @param postal
     * @param phone
     * @param divID
     * @return rowsAffected
     * @throws SQLException
     */
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

    /**
     * Deletes Customer Record from database
     * @param ID
     * @return rowsAffected
     * @throws SQLException
     */
    public static int delete(int ID) throws SQLException {
        DBConnection.openConnection();
        String sql = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,ID);
        int rowsAffected = ps.executeUpdate();
        DBConnection.closeConnection();
        return rowsAffected;
    }

    /**
     * Finds max customer ID ++
     * @return next ID , 1 by default
     * @throws SQLException
     */
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

    /**
     * public access method to return next ID
     * @return next Customer ID
     * @throws SQLException
     */
    public static int getNextCustomerID() throws SQLException {
        DBConnection.openConnection();
        int nextID = findNextCustomerID();
        DBConnection.closeConnection();
        return nextID;
    }

    /**
     * Returns ObservableList of all customers. Manages DBConnection
     * @return ObservableList of all customers
     * @throws SQLException
     */
    public static ObservableList<Customer> getCustomers() throws SQLException {
        DBConnection.openConnection();
        select();
        DBConnection.closeConnection();
        return customers;
    }

    /**
     * gets totals of number of customers in each FLD
     * @return GroupByTotals list
     * @throws SQLException
     */
    public static ObservableList<GroupByTotals> getCustomersByFLD() throws SQLException {
        DBConnection.openConnection();
        selectCustomerFLDTotals();
        DBConnection.closeConnection();
        return customerTotals;
    }
    /**
     * public access method for selecting customers based on searchText (can be "")
     * @param searchText substring or ID to search
     * @return ObservableList of Customers
     * @throws SQLException
     */
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