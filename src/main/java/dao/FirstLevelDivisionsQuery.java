package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionsQuery {
    private static ObservableList<FirstLevelDivision> FLDs = FXCollections.observableArrayList();
    public static ObservableList<FirstLevelDivision> select(int countryID) throws SQLException {
        FLDs.clear();
        DBConnection.openConnection();
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE COUNTRY_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,countryID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            FLDs.add(new FirstLevelDivision(rs.getString("DIVISION"),rs.getInt("DIVISION_ID")));
        }
        DBConnection.closeConnection();
        return FLDs;
    }
}
