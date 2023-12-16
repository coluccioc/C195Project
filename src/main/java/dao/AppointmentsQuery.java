package dao;

import helper.TimeZoneHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class AppointmentsQuery {
    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private static void select() throws SQLException {
        appointments.clear();
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("APPOINTMENT_ID");
            String title = rs.getString("TITLE");
            String description = rs.getString("DESCRIPTION");
            String location = rs.getString("LOCATION");
            String type = rs.getString("TYPE");
            Timestamp startUTC = rs.getTimestamp("START");
            Timestamp start = TimeZoneHelper.translateToSystemZone(startUTC);
            appointments.add(new Appointment(id,title,description,location,type,start));
        }
    }
    private static void select(int customer_ID) throws SQLException {
        appointments.clear();
        String sql = "SELECT * FROM APPOINTMENTS WHERE CUSTOMER_ID=?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,customer_ID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("APPOINTMENT_ID");
            String title = rs.getString("TITLE");
            String description = rs.getString("DESCRIPTION");
            String location = rs.getString("LOCATION");
            String type = rs.getString("TYPE");
            Timestamp startUTC = rs.getTimestamp("START");
            Timestamp start = TimeZoneHelper.translateToSystemZone(startUTC);
            appointments.add(new Appointment(id,title,description,location,type,start));
        }
    }
    private static int findNextAppointmentID() throws SQLException {
        String sql = "SELECT MAX(Appointment_ID) + 1 AS NextID FROM APPOINTMENTS";
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
    public static int getNextAppointmentID() throws SQLException {
        DBConnection.openConnection();
        int nextID = findNextAppointmentID();
        DBConnection.closeConnection();
        return nextID;
    }
    public static ObservableList<Appointment> getAppointments() throws SQLException {
            DBConnection.openConnection();
            select();
            DBConnection.closeConnection();
            return appointments;
    }
    public static ObservableList<Appointment> getCustomerAppointments(int customer_ID) throws SQLException {
        DBConnection.openConnection();
        select(customer_ID);
        DBConnection.closeConnection();
        return appointments;
    }
}
