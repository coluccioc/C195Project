package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersQuery {
    private static ObservableList<User> users = FXCollections.observableArrayList();
    /**
     * Selects based on exact match to given Username. Returns corresponding password
     * @param username given username
     * @return password String
     * @throws SQLException
     */
    public static String select(String username) throws SQLException {
        DBConnection.openConnection();
        String sql = "SELECT * FROM USERS WHERE User_Name = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1,username);
        ResultSet rs = ps.executeQuery();
        if(!rs.next()){
            return null;
        }
        String password = rs.getString("Password");
        DBConnection.closeConnection();
        return password;
    }
    /**
     * Selects a User by their given ID
     * @param ID user ID
     * @return User
     * @throws SQLException
     */
    public static User selectByUserID(int ID) throws SQLException {
        DBConnection.openConnection();
        String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,ID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String name = rs.getString("USER_NAME");
        User user = new User(ID,name);
        DBConnection.closeConnection();
        return user;
    }
    /**
     * Returns list of all Users
     * @return ObservableList of Users
     * @throws SQLException
     */
    public static ObservableList<User> getUsers() throws SQLException {
        users.clear();
        DBConnection.openConnection();
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
           int id = rs.getInt("USER_ID");
           String name = rs.getString("USER_NAME");
           users.add(new User(id,name));
        }
        DBConnection.closeConnection();
        return users;
    }
}