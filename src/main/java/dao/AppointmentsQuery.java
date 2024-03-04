package dao;

import controller.LoginController;
import helper.Filterer;
import helper.TimeZoneHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.GroupByTotals;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * DAO class for Appointments
 * Includes multiple select statements. Selects are used to aggregate number of records with certain traits
 * Defines lambda expressions urgentFilter, customerFilter to quickly filter appointments by a desired
 * time range or specific customer
 * Selects, Inserts, Updates, and Deletes from Database upon request
 */
public class AppointmentsQuery {
    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private static ObservableList<GroupByTotals> appointmnetTotals = FXCollections.observableArrayList();
    public static Filterer<Appointment> urgentFilter = (inputList,minutes) ->
            inputList.filtered(appointment -> (appointment.getStart().compareTo(TimeZoneHelper.getUrgentTimestamp(minutes))<=0)&&
            appointment.getStart().compareTo(TimeZoneHelper.getNowTimestamp())>=0);

    public static Filterer<Appointment> customerFilter = ((inputList, customerID) ->
            inputList.filtered(appointment -> appointment.getCustomer_ID() == customerID));

    /**
     * select class gets All Appointments and adds them to an ObservableList
     * @throws SQLException
     */
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
//            Timestamp startUTC = rs.getTimestamp("START");
//            Timestamp start = TimeZoneHelper.translateToSystemZone(startUTC);
            Timestamp start = rs.getTimestamp("START");
//            Timestamp endUTC = rs.getTimestamp("END");
//            Timestamp end = TimeZoneHelper.translateToSystemZone(endUTC);
            Timestamp end = rs.getTimestamp("END");
            int customer_ID = rs.getInt("CUSTOMER_ID");
            int user_ID = rs.getInt("USER_ID");
            int contact_ID = rs.getInt("CONTACT_ID");
            appointments.add(new Appointment(id,title,description,location,type,start,end,customer_ID,user_ID,contact_ID));
        }
    }

    /**
     * Adds a new record to the database
     * Most params are self-explanatory
     * @param ID appointment ID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customer_ID
     * @param user_ID
     * @param contact_ID
     * @return number of rows affected
     * @throws SQLException
     */
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
        System.out.println("4: start: "+ start);
        int rowsAffected = ps.executeUpdate();
        DBConnection.closeConnection();
        return rowsAffected;
    }
    /**
     * Updates an existing Appointment Record in the database
     * @param ID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customer_ID
     * @param contact_ID
     * @param user_ID
     * @return rows affected
     * @throws SQLException
     */
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
    /**
     * Selects all Appointments in the current month
     * @throws SQLException
     */
    private static void selectByMonth() throws SQLException {
        appointments.clear();
        LocalDateTime startOfMonthDateTime = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonthDateTime = startOfMonthDateTime.plusMonths(1);
//        Timestamp startOfMonthTimestamp = TimeZoneHelper.convertToUTCTimestamp(startOfMonthDateTime);
//        Timestamp endOfMonthTimestamp = TimeZoneHelper.convertToUTCTimestamp(endOfMonthDateTime);
        Timestamp startOfMonthTimestamp = Timestamp.valueOf(startOfMonthDateTime);
        Timestamp endOfMonthTimestamp = Timestamp.valueOf(endOfMonthDateTime);
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
//            Timestamp startUTC = rs.getTimestamp("START");
//            Timestamp start = TimeZoneHelper.translateToSystemZone(startUTC);
            Timestamp start = rs.getTimestamp("START");
//            Timestamp endUTC = rs.getTimestamp("END");
//            Timestamp end = TimeZoneHelper.translateToSystemZone(endUTC);
            Timestamp end = rs.getTimestamp("END");
            int customer_ID  = rs.getInt("CUSTOMER_ID");
            int user_ID = rs.getInt("USER_ID");
            int contact_ID = rs.getInt("CONTACT_ID");
            appointments.add(new Appointment(id,title,description,location,type,start,end,customer_ID,user_ID,contact_ID));
        }
    }
    /**
     * Selects all appointments in the current week, Sunday -> Saturday
     * @throws SQLException
     */
    private static void selectByWeek() throws SQLException {
        appointments.clear();
        LocalDate currentDate = LocalDate.now();
        DayOfWeek today = currentDate.getDayOfWeek();
        //Value of Sunday is 7 in Java DayOfWeek
        int daysUntilSunday =  DayOfWeek.SUNDAY.getValue() - today.getValue();
        if(daysUntilSunday==0) daysUntilSunday=7; //Set to Next Sunday if it is Sunday Today
        LocalDateTime endOfWeek = currentDate.plusDays(daysUntilSunday).atStartOfDay();
        LocalDateTime startOfWeek = endOfWeek.minusDays(7);
//        Timestamp startOfWeekTimestamp = TimeZoneHelper.convertToUTCTimestamp(startOfWeek);
//        Timestamp endOfWeekTimestamp = TimeZoneHelper.convertToUTCTimestamp(endOfWeek);
        Timestamp startOfWeekTimestamp = Timestamp.valueOf(startOfWeek);
        Timestamp endOfWeekTimestamp = Timestamp.valueOf(endOfWeek);
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
//            Timestamp startUTC = rs.getTimestamp("START");
//            Timestamp start = TimeZoneHelper.translateToSystemZone(startUTC);
            Timestamp start = rs.getTimestamp("START");
//            Timestamp endUTC = rs.getTimestamp("END");
//            Timestamp end = TimeZoneHelper.translateToSystemZone(endUTC);
            Timestamp end = rs.getTimestamp("END");
            int customer_ID  = rs.getInt("CUSTOMER_ID");
            int user_ID = rs.getInt("USER_ID");
            int contact_ID = rs.getInt("CONTACT_ID");
            appointments.add(new Appointment(id,title,description,location,type,start,end,customer_ID,user_ID,contact_ID));
        }
    }
    //SIMPLIFIED THE FOLLOWING USING LAMBDAs
//    private static void selectByUrgent() throws SQLException {
//        appointments.clear();
//        String sql = "SELECT * FROM APPOINTMENTS WHERE START >= ? AND START < ? ORDER BY START";
//        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
//        ps.setTimestamp(1,TimeZoneHelper.getNowTimestamp());
//        ps.setTimestamp(2,TimeZoneHelper.getUrgentTimestamp(15));
//        ResultSet rs = ps.executeQuery();
//        while(rs.next()){
//            int id = rs.getInt("APPOINTMENT_ID");
//            String title = rs.getString("TITLE");
//            String description = rs.getString("DESCRIPTION");
//            String location = rs.getString("LOCATION");
//            String type = rs.getString("TYPE");
//            Timestamp startUTC = rs.getTimestamp("START");
//            Timestamp start = TimeZoneHelper.translateToSystemZone(startUTC);
//            Timestamp endUTC = rs.getTimestamp("END");
//            Timestamp end = TimeZoneHelper.translateToSystemZone(endUTC);
//            int customer_ID  = rs.getInt("CUSTOMER_ID");
//            int user_ID = rs.getInt("USER_ID");
//            int contact_ID = rs.getInt("CONTACT_ID");
//            appointments.add(new Appointment(id,title,description,location,type,start,end,customer_ID,user_ID,contact_ID));
//        }
//    }
//    private static void selectByCust(int customer_ID) throws SQLException {
//        appointments.clear();
//        String sql = "SELECT * FROM APPOINTMENTS WHERE CUSTOMER_ID=?";
//        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
//        ps.setInt(1,customer_ID);
//        ResultSet rs = ps.executeQuery();
//        while(rs.next()){
//            int id = rs.getInt("APPOINTMENT_ID");
//            String title = rs.getString("TITLE");
//            String description = rs.getString("DESCRIPTION");
//            String location = rs.getString("LOCATION");
//            String type = rs.getString("TYPE");
//            Timestamp startUTC = rs.getTimestamp("START");
//            Timestamp start = TimeZoneHelper.translateToSystemZone(startUTC);
//            Timestamp endUTC = rs.getTimestamp("END");
//            Timestamp end = TimeZoneHelper.translateToSystemZone(endUTC);
//            customer_ID  = rs.getInt("CUSTOMER_ID");
//            int user_ID = rs.getInt("USER_ID");
//            int contact_ID = rs.getInt("CONTACT_ID");
//            appointments.add(new Appointment(id,title,description,location,type,start,end,customer_ID,user_ID,contact_ID));
//        }
//    }
//    private static void selectByContact(int contact_ID) throws SQLException {
//        appointments.clear();
//        String sql = "SELECT * FROM APPOINTMENTS WHERE CONTACT_ID=?";
//        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
//        ps.setInt(1,contact_ID);
//        ResultSet rs = ps.executeQuery();
//        while(rs.next()){
//            int id = rs.getInt("APPOINTMENT_ID");
//            String title = rs.getString("TITLE");
//            String description = rs.getString("DESCRIPTION");
//            String location = rs.getString("LOCATION");
//            String type = rs.getString("TYPE");
//            Timestamp startUTC = rs.getTimestamp("START");
//            Timestamp start = TimeZoneHelper.translateToSystemZone(startUTC);
//            Timestamp endUTC = rs.getTimestamp("END");
//            Timestamp end = TimeZoneHelper.translateToSystemZone(endUTC);
//            int customer_ID  = rs.getInt("CUSTOMER_ID");
//            int user_ID = rs.getInt("USER_ID");
//            contact_ID = rs.getInt("CONTACT_ID");
//            appointments.add(new Appointment(id,title,description,location,type,start,end,customer_ID,user_ID,contact_ID));
//        }
//    }

    /**
     * Finds current highest ID ++ , private
     * @return next Appointmnet ID
     * @throws SQLException
     */
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
    /**
     * Deletes a record from database based on matching ID (unique)
     * @param ID appointment ID
     * @return rowsAffected
     * @throws SQLException
     */
    public static int delete(int ID) throws SQLException {
        DBConnection.openConnection();
        String sql = "DELETE FROM APPOINTMENTS WHERE APPOINTMENT_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,ID);
        int rowsAffected = ps.executeUpdate();
        DBConnection.closeConnection();
        return rowsAffected;
    }

    /**
     * Returns next Appointment ID, public
     * @return nextAppointmentID
     * @throws SQLException
     */
    public static int getNextAppointmentID() throws SQLException {
        DBConnection.openConnection();
        int nextID = findNextAppointmentID();
        DBConnection.closeConnection();
        return nextID;
    }
    /**
     * Public access to select results. Manages DBConnection
     * @return results from select()
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
            DBConnection.openConnection();
            select();
            DBConnection.closeConnection();
            return appointments;
    }
    /**
     * Public access to select results. Manages DBConnection
     * @return results from selectByMonth()
     * @throws SQLException
     */
    public static ObservableList<Appointment> getMonthAppointments() throws SQLException {
        DBConnection.openConnection();
        selectByMonth();
        DBConnection.closeConnection();
        return appointments;
    }
    /**
     * Public access to select results. Manages DBConnection
     * @return results from selectByWeek()
     * @throws SQLException
     */
    public static ObservableList<Appointment> getWeekAppointments() throws SQLException {
        DBConnection.openConnection();
        selectByWeek();
        DBConnection.closeConnection();
        return appointments;
    }
    //INCORPORATED INTO LAMBDA FILTER
//    public static ObservableList<Appointment> getUrgentAppointments() throws SQLException {
//        DBConnection.openConnection();
//        selectByUrgent();
//        DBConnection.closeConnection();
//        return appointments;
//    }
//    public static ObservableList<Appointment> getCustomerAppointments(int customer_ID) throws SQLException {
//        DBConnection.openConnection();
//        selectByCust(customer_ID);
//        DBConnection.closeConnection();
//        return appointments;
//    }
//    public static ObservableList<Appointment> getContactAppointments(int contact_ID) throws SQLException {
//        DBConnection.openConnection();
//        selectByContact(contact_ID);
//        DBConnection.closeConnection();
//        return appointments;
//    }

    /**
     * Aggregate selection. Finds totals by Type
     * @throws SQLException
     */
    private static void selectAppointmentTypeTotals() throws SQLException {
        appointmnetTotals.clear();
        String sql = "SELECT TYPE, COUNT(*) AS TOTAL FROM appointments GROUP BY TYPE";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String group = rs.getString("TYPE");
            int total = rs.getInt("TOTAL");
            appointmnetTotals.add(new GroupByTotals(group,total));
        }
    }
    /**
     * Aggregate selection. Finds totals by Month
     * @throws SQLException
     */
    private static void selectAppointmentMonthTotals() throws SQLException {
        appointmnetTotals.clear();
        String sql = "SELECT CONCAT(YEAR(APPOINTMENTS.Start), '-', LPAD(MONTH(APPOINTMENTS.Start), 2, '0')) AS `YEAR-MONTH`, " +
                "COUNT(*) AS TOTAL " +
                "FROM APPOINTMENTS " +
                "GROUP BY `YEAR-MONTH` " +
                "ORDER BY `YEAR-MONTH`;";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String group = rs.getString("YEAR-MONTH");
            int total = rs.getInt("TOTAL");
            appointmnetTotals.add(new GroupByTotals(group,total));
        }
    }
    /**
     *
     * @param groupBy type of aggregate result desired
     * @return list of GroupByTotals object
     * @throws SQLException
     */
    public static ObservableList<GroupByTotals> getAppointmentTotals(String groupBy) throws SQLException {
        DBConnection.openConnection();
        if(groupBy == "Type") {
            selectAppointmentTypeTotals();
        }
        else if(groupBy == "Month"){
            selectAppointmentMonthTotals();
        }
        DBConnection.closeConnection();
        return appointmnetTotals;
    }
}