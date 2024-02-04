package dao;

import controller.LoginController;
import helper.TimeZoneHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public static int update(int ID, String title,String description,String location,String type, Timestamp start,
                             Timestamp end,int customer_ID,int contact_ID,int user_ID) throws SQLException {
        DBConnection.openConnection();
        ZonedDateTime now = ZonedDateTime.now();
        Timestamp nowTimestamp = Timestamp.from(now.toInstant());
        String sql = "UPDATE APPOINTMENTS " +
                "SET TITLE = ?, " + //1
                "DESCRIPTION = ?," + //2
                "LOCATION = ?," + //3
                "TYPE = ?," + //4
                "START = ?," + //5
                "END = ?," + //6
                "CUSTOMER_ID = ?, " + //7
                "CONTACT_ID = ?, " + //8
                "USER_ID = ?, " + //9
                "LAST_UPDATE = ?," + //10
                "LAST_UPDATED_BY = ?" + //11
                "WHERE APPOINTMENT_ID = ?;"; //12
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setInt(7, customer_ID);
        ps.setInt(8, contact_ID);
        ps.setInt(9, user_ID);
        ps.setTimestamp(10, nowTimestamp);
        ps.setString(11, LoginController.username);
        ps.setInt(12, ID);
        int rowsAffected = ps.executeUpdate();
        DBConnection.closeConnection();
        return rowsAffected;
    }
    private static void selectByMonth() throws SQLException {
        appointments.clear();
        LocalDateTime startOfMonthDateTime = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonthDateTime = startOfMonthDateTime.plusMonths(1);
        Timestamp startOfMonthTimestamp = TimeZoneHelper.convertToUTCTimestamp(startOfMonthDateTime);
        Timestamp endOfMonthTimestamp = TimeZoneHelper.convertToUTCTimestamp(endOfMonthDateTime);
        String sql = "SELECT * FROM APPOINTMENTS WHERE START >= ? AND START < ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setTimestamp(1,startOfMonthTimestamp);
        ps.setTimestamp(2,endOfMonthTimestamp);
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
            int customer_ID  = rs.getInt("CUSTOMER_ID");
            int user_ID = rs.getInt("USER_ID");
            int contact_ID = rs.getInt("CONTACT_ID");
            appointments.add(new Appointment(id,title,description,location,type,start,end,customer_ID,user_ID,contact_ID));
        }
    }
    private static void selectByWeek() throws SQLException {
        appointments.clear();
        LocalDate currentDate = LocalDate.now();
        DayOfWeek today = currentDate.getDayOfWeek();
        //Value of Sunday is 7 in Java DayOfWeek
        int daysUntilSunday =  DayOfWeek.SUNDAY.getValue() - today.getValue();
        LocalDateTime endOfWeek = currentDate.plusDays(daysUntilSunday).atStartOfDay();
        LocalDateTime startOfWeek = endOfWeek.minusDays(7);
        Timestamp startOfWeekTimestamp = TimeZoneHelper.convertToUTCTimestamp(startOfWeek);
        Timestamp endOfWeekTimestamp = TimeZoneHelper.convertToUTCTimestamp(endOfWeek);
        String sql = "SELECT * FROM APPOINTMENTS WHERE START >= ? AND START < ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setTimestamp(1,startOfWeekTimestamp);
        ps.setTimestamp(2,endOfWeekTimestamp);
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
            int customer_ID  = rs.getInt("CUSTOMER_ID");
            int user_ID = rs.getInt("USER_ID");
            int contact_ID = rs.getInt("CONTACT_ID");
            appointments.add(new Appointment(id,title,description,location,type,start,end,customer_ID,user_ID,contact_ID));
        }
    }
    private static void selectByUrgent() throws SQLException {
        appointments.clear();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime urgentDateTime = currentDateTime.plusMinutes(15);
        Timestamp currentTimestamp = TimeZoneHelper.convertToUTCTimestamp(currentDateTime);
        Timestamp urgentTimestamp = TimeZoneHelper.convertToUTCTimestamp(urgentDateTime);
        String sql = "SELECT * FROM APPOINTMENTS WHERE START >= ? AND START < ? ORDER BY START";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setTimestamp(1,currentTimestamp);
        ps.setTimestamp(2,urgentTimestamp);
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
            int customer_ID  = rs.getInt("CUSTOMER_ID");
            int user_ID = rs.getInt("USER_ID");
            int contact_ID = rs.getInt("CONTACT_ID");
            appointments.add(new Appointment(id,title,description,location,type,start,end,customer_ID,user_ID,contact_ID));
        }
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
    public static int delete(int ID) throws SQLException {
        DBConnection.openConnection();
        String sql = "DELETE FROM APPOINTMENTS WHERE APPOINTMENT_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,ID);
        int rowsAffected = ps.executeUpdate();
        DBConnection.closeConnection();
        return rowsAffected;
    }
    public static int getNextAppointmentID() throws SQLException {
        DBConnection.openConnection();
        int nextID = findNextAppointmentID();
        DBConnection.closeConnection();
        return nextID;
    }
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
            DBConnection.openConnection();
            select();
            DBConnection.closeConnection();
            return appointments;
    }
    public static ObservableList<Appointment> getMonthAppointments() throws SQLException {
        DBConnection.openConnection();
        selectByMonth();
        DBConnection.closeConnection();
        return appointments;
    }
    public static ObservableList<Appointment> getWeekAppointments() throws SQLException {
        DBConnection.openConnection();
        selectByWeek();
        DBConnection.closeConnection();
        return appointments;
    }
    public static ObservableList<Appointment> getUrgentAppointments() throws SQLException {
        DBConnection.openConnection();
        selectByUrgent();
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