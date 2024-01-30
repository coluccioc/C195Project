package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersQuery {
    private static ObservableList<User> users = FXCollections.observableArrayList();
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