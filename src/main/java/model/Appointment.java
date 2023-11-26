package model;

public class Appointment {
    private int appointment_ID;
    private String title;
    private String description;
    private String location;
    private String type;

    public Appointment(int appointment_ID, String title, String description, String location, String type) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
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
}
