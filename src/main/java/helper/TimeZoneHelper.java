package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;

public class TimeZoneHelper {
    private static ZoneId utcZone = ZoneId.of("UTC");
    public static ZoneId systemZone = ZoneId.systemDefault();
    public static ObservableList<Integer> hours = FXCollections.observableArrayList();
    public static ObservableList<String> minutes = FXCollections.observableArrayList();
    public static ObservableList<String> durations = FXCollections.observableArrayList();
public static Timestamp translateToSystemZone(Timestamp utcTime){
        ZonedDateTime utcZoned = utcTime.toLocalDateTime().atZone(utcZone);
        ZonedDateTime systemZoned = ZonedDateTime.ofInstant(utcZoned.toInstant(),systemZone);
        Timestamp systemTime = Timestamp.valueOf(systemZoned.toLocalDateTime());
        return systemTime;
    }
    public static ZonedDateTime translateToUTC(ZonedDateTime systemDateTime){
        return ZonedDateTime.ofInstant(systemDateTime.toInstant(),utcZone);
    }
    public static int getOffsetHours() {
        LocalDateTime systemDateTime = LocalDateTime.now();
        ZoneOffset systemOffset = systemDateTime.atZone(systemZone).getOffset();
        return systemOffset.getTotalSeconds() / 3600;
    }
    public static Timestamp convertToUTCTimestamp(LocalDateTime selectedDateTime){

        ZonedDateTime localZonedDateTime = selectedDateTime.atZone(ZoneId.systemDefault());

        ZonedDateTime utcZonedDateTime = translateToUTC(localZonedDateTime);

        Timestamp timestamp = Timestamp.valueOf(utcZonedDateTime.toLocalDateTime());

        return timestamp;
    }
    public static String timestampDifference(Timestamp end, Timestamp start){
        Instant endInstant = end.toInstant();
        Instant startInstant = start.toInstant();

        Duration duration = Duration.between(startInstant,endInstant);
        String durationMinutes = duration.toMinutes()%60+"";
        if(durationMinutes.equals("0"))durationMinutes = "00";

        return duration.toHours() + ":" + durationMinutes;
    }
}