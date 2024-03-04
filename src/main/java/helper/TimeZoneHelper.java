package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.*;

/**
 * Helper Class for manipulating TimeZones due to local, business, and UTC zones being in use
 */
public class TimeZoneHelper {
    private static ZoneId utcZone = ZoneId.of("UTC");
    public static ZoneId systemZone = ZoneId.systemDefault();
    public static ObservableList<Integer> hours = FXCollections.observableArrayList();
    public static ObservableList<String> minutes = FXCollections.observableArrayList();
    public static ObservableList<String> durations = FXCollections.observableArrayList();

    /**
     * Translates a UTC Timestamp to the system's local time
     * @param utcTime utc timestamp
     * @return Local Timestamp
     */
    /* NO USAGES. FOUND OUT TIMESTAMPS AUTO CONVERT WHEN SET IN SQL QUERY
    public static Timestamp translateToSystemZone(Timestamp utcTime){
        ZonedDateTime utcZoned = utcTime.toLocalDateTime().atZone(utcZone);
        ZonedDateTime systemZoned = ZonedDateTime.ofInstant(utcZoned.toInstant(),systemZone);
        Timestamp systemTime = Timestamp.valueOf(systemZoned.toLocalDateTime());
        return systemTime;
    }
    */
    /**
     * Translates a system ZonedDateTime to UTC Time
     * @param systemDateTime system ZonedDateTime
     * @return UTC ZonedDateTime
     */
    public static ZonedDateTime translateToUTC(ZonedDateTime systemDateTime) {
        Instant instant = systemDateTime.toInstant();
        ZonedDateTime utcZonedDateTime = instant.atZone(utcZone);
        return utcZonedDateTime;
    }
    /**
     * Finds the offset between system time and UTC
     * @return int offset hrs
     */
    public static int getOffsetHours() {
        LocalDateTime systemDateTime = LocalDateTime.now();
        ZoneOffset systemOffset = systemDateTime.atZone(systemZone).getOffset();
        return systemOffset.getTotalSeconds() / 3600;
    }
    /**
     * Converts system TimeStamp to UTC TimeStamp
     * @param selectedDateTime local dateTime
     * @return UTC Timestamp
     */
    /* NO USAGES. FOUND OUT TIMESTAMPS AUTO CONVERT WHEN SET IN SQL QUERY
    public static Timestamp convertToUTCTimestamp(LocalDateTime selectedDateTime){

        ZonedDateTime localZonedDateTime = selectedDateTime.atZone(ZoneId.systemDefault());
        System.out.println("1: "+localZonedDateTime);

        ZonedDateTime utcZonedDateTime = translateToUTC(localZonedDateTime);
        System.out.println("2: "+utcZonedDateTime);

        Timestamp timestamp = Timestamp.valueOf(utcZonedDateTime.toLocalDateTime());
        System.out.println("3: "+timestamp);

        return timestamp;
    }
    */
    /**
     * Finds difference between timestamps to determine duration
     * @param end end Timestamp
     * @param start start Timestamp
     * @return String for duration
     */
    public static String timestampDifference(Timestamp end, Timestamp start){
        Instant endInstant = end.toInstant();
        Instant startInstant = start.toInstant();

        Duration duration = Duration.between(startInstant,endInstant);
        String durationMinutes = duration.toMinutes()%60+"";
        if(durationMinutes.equals("0"))durationMinutes = "00";

        return duration.toHours() + ":" + durationMinutes;
    }
    /**
     * Gets now as Timestamp
     * @return now
     */
    public static Timestamp getNowTimestamp(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
        return currentTimestamp;
    }
    /**
     * Gets Timestamp based on now + given minutes
     * @param minutes minutes considered urgent
     * @return urgent Timestamp
     */
    public static Timestamp getUrgentTimestamp(int minutes){
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime urgentDateTime = currentDateTime.plusMinutes(minutes);
        Timestamp urgentTimestamp = Timestamp.valueOf(urgentDateTime);
        return urgentTimestamp;
    }
}