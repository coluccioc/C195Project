package model;

import dao.ContactsQuery;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Model class for Appointments
 * Getters and setters for object attributes
 * Holds contact name based on search for contact ID
 */
public class Appointment {
    private int appointment_ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private int customer_ID;
    private int user_ID;
    private int contact_ID;
    private String contact;
    private Timestamp start;
    private Timestamp end;

    /**
     * Constructor for Appointments, looks up contact name based on contact ID
     * @param appointment_ID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customer_ID
     * @param user_ID
     * @param contact_ID
     * @throws SQLException
     */
    public Appointment(int appointment_ID, String title, String description, String location, String type,
                       Timestamp start, Timestamp end, int customer_ID, int user_ID, int contact_ID) throws SQLException {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_ID = customer_ID;
        this.user_ID = user_ID;
        this.contact_ID = contact_ID;
        this.contact = ContactsQuery.selectByContactID(contact_ID).toString();
    }

    /**
     * Getter for Appt ID
     * @return ID
     */
    public int getAppointment_ID() {
        return appointment_ID;
    }
    /**
     * Getter for Appt Title
     * @return Title
     */
    public String getTitle() {
        return title;
    }
    /**
     * Getter for Appt Desc
     * @return Desc
     */
    public String getDescription() {
        return description;
    }
    /**
     * Getter for Appt Location
     * @return Location
     */
    public String getLocation() {
        return location;
    }
    /**
     * Getter for Appt Type
     * @return Type
     */
    public String getType() {
        return type;
    }
    /**
     * Getter for Appt Start
     * @return Start
     */
    public Timestamp getStart() {
        return start;
    }
    /**
     * Getter for Appt End
     * @return End
     */
    public Timestamp getEnd() {
        return end;
    }
    /**
     * Getter for Appt customer ID
     * @return customer ID
     */
    public int getCustomer_ID() {
        return customer_ID;
    }
    /**
     * Getter for Appt conatct ID
     * @return contact ID
     */
    public int getContact_ID() {
        return contact_ID;
    }
    /**
     * Getter for Appt conatct
     * @return contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * Getter for Appt user ID
     * @return user ID
     */
    public int getUser_ID() {
        return user_ID;
    }
}