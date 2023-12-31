package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersQuery {
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
}