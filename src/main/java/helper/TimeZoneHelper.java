package helper;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeZoneHelper {
    private static ZoneId utcZone = ZoneId.of("UTC");
    public static ZoneId systemZone = ZoneId.systemDefault();
public static Timestamp translateToSystemZone(Timestamp utcTime){
        ZonedDateTime utcZoned = utcTime.toLocalDateTime().atZone(utcZone);
        ZonedDateTime systemZoned = ZonedDateTime.ofInstant(utcZoned.toInstant(),systemZone);
        Timestamp systemTime = Timestamp.valueOf(systemZoned.toLocalDateTime());
        return systemTime;
    }
    public static ZonedDateTime translateToUTC(ZonedDateTime systemDateTime){
        return ZonedDateTime.ofInstant(systemDateTime.toInstant(),utcZone);
    }
}
