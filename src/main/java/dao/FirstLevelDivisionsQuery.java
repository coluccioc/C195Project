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
    public static FirstLevelDivision selectGivenFLD(int firstLevelDivisionID) throws SQLException {
        DBConnection.openConnection();
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,firstLevelDivisionID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String name = rs.getString("DIVISION");
        FirstLevelDivision FLD = new FirstLevelDivision(name,firstLevelDivisionID);
        DBConnection.closeConnection();
        return FLD;
    }
    public static int selectCountryID(int firstLevelDivisionID) throws SQLException {
        DBConnection.openConnection();
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,firstLevelDivisionID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int country_ID = rs.getInt("COUNTRY_ID");
        DBConnection.closeConnection();
        return country_ID;
    }
}
