package model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class Appointment {
    private int appointment_ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private int customer_ID;
    private int user_ID;
    private int contact_ID;
    private Timestamp start;
    private Timestamp end;

    public Appointment(int appointment_ID, String title, String description, String location, String type,
                       Timestamp start, Timestamp end, int customer_ID, int user_ID, int contact_ID) {
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
    }
    public int getAppointment_ID() {
        return appointment_ID;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public Timestamp getStart() {
        return start;
    }

    public Timestamp getEnd() {
        return end;
    }
    public int getCustomer_ID() {
        return customer_ID;
    }
    public int getContact_ID() {
        return contact_ID;
    }
    public int getUser_ID() {
        return user_ID;
    }
}
