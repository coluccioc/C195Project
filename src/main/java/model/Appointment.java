package model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class Appointment {
    private int appointment_ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;

    public Appointment(int appointment_ID, String title, String description, String location, String type, Timestamp start, Timestamp end) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
    }
    public int getAppointment_ID() {
        return appointment_ID;
    }

    public void setAppointment_ID(int appointment_ID) {
        this.appointment_ID = appointment_ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Timestamp getStart() {
        return start;
    }
    public void setStart(Timestamp start) {
        this.start = start;
    }
    public Timestamp getEnd() {
        return end;
    }
    public void setEnd(Timestamp end) {
        this.end = end;
    }
}
