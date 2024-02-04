package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.FirstLevelDivision;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsQuery {
    private static ObservableList<Contact> contacts = FXCollections.observableArrayList();
    public static ObservableList<Contact> getContacts() throws SQLException {
        contacts.clear();
        DBConnection.openConnection();
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("CONTACT_ID");
            String name = rs.getString("CONTACT_NAME");
            contacts.add(new Contact(id,name));
        }
        DBConnection.closeConnection();
        return contacts;
    }
    public static Contact selectByContactID(int ID) throws SQLException {
        DBConnection.openConnection();
        String sql = "SELECT * FROM CONTACTS WHERE CONTACT_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,ID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String name = rs.getString("CONTACT_NAME");
        Contact contact = new Contact(ID,name);
        DBConnection.closeConnection();
        return contact;
    }
}