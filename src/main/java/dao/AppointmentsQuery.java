package dao;

import controller.LoginController;
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
            Timestamp endUTC = rs.getTimestamp("END");
            Timestamp end = TimeZoneHelper.translateToSystemZone(endUTC);
            int customer_ID = rs.getInt("CUSTOMER_ID");
            int user_ID = rs.getInt("USER_ID");
            int contact_ID = rs.getInt("CONTACT_ID");
            appointments.add(new Appointment(id,title,description,location,type,start,end,customer_ID,user_ID,contact_ID));
        }
    }
    public static int insert(int ID, String title,String description,String location,String type,Timestamp start,
                             Timestamp end, int customer_ID, int user_ID, int contact_ID) throws SQLException {
        DBConnection.openConnection();
        ZonedDateTime now = ZonedDateTime.now();
        Timestamp nowTimestamp = Timestamp.from(now.toInstant());
        String sql = "INSERT INTO APPOINTMENTS (APPOINTMENT_ID , " + //1
                "TITLE," + //2
                "DESCRIPTION," + //3
                "LOCATION," + //4
                "TYPE," + //5
                "START," + //6
                "END," + //7
                "CREATE_DATE," + //8
                "CREATED_BY," + //9
                "LAST_UPDATE," + //10
                "LAST_UPDATED_BY," + //11
                "CUSTOMER_ID," + //12
                "USER_ID," + //13
                "CONTACT_ID)" + //14
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, ID);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, type);
        ps.setTimestamp(6, start);
        ps.setTimestamp(7, end);
        ps.setTimestamp(8, nowTimestamp);
        ps.setString(9, LoginController.username);
        ps.setTimestamp(10, nowTimestamp);
        ps.setString(11, LoginController.username);
        ps.setInt(12, customer_ID);
        ps.setInt(13, user_ID);
        ps.setInt(14, contact_ID);
        int rowsAffected = ps.executeUpdate();
        DBConnection.closeConnection();
        return rowsAffected;
    }
    private static void selectByCust(int customer_ID) throws SQLException {
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
            Timestamp endUTC = rs.getTimestamp("END");
            Timestamp end = TimeZoneHelper.translateToSystemZone(endUTC);
            customer_ID  = rs.getInt("CUSTOMER_ID");
            int user_ID = rs.getInt("USER_ID");
            int contact_ID = rs.getInt("CONTACT_ID");
            appointments.add(new Appointment(id,title,description,location,type,start,end,customer_ID,user_ID,contact_ID));
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
            return 1;
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
        selectByCust(customer_ID);
        DBConnection.closeConnection();
        return appointments;
    }
}