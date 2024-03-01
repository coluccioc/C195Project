package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsQuery {
    private static ObservableList<Contact> contacts = FXCollections.observableArrayList();

    /**
     * Selects and Returns all contacts
     * @return ObservableList of Contacts
     * @throws SQLException
     */
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

    /**
     * Selects and returns contact matching given ID
     * @param ID given contactID
     * @return Contact obj
     * @throws SQLException
     */
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