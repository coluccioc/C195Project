package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesQuery{
    private static ObservableList<Country> countries = FXCollections.observableArrayList();
    public static ObservableList<Country> select() throws SQLException {
            countries.clear();
            DBConnection.openConnection();
            String sql = "SELECT * FROM COUNTRIES";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                countries.add(new Country(rs.getString("Country"),rs.getInt("COUNTRY_ID")));
            }
            DBConnection.closeConnection();
            return countries;
        }
}
