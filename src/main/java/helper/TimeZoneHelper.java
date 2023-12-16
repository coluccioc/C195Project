package helper;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeZoneHelper {
    public ZonedDateTime translateToSystemZone(ZonedDateTime utcDateTime){
        ZoneId systemZone = ZoneId.systemDefault();
        return ZonedDateTime.ofInstant(utcDateTime.toInstant(),systemZone);
    }
    public ZonedDateTime translateToUTC(ZonedDateTime systemDateTime){
        ZoneId utcZone = ZoneId.of("UTC");
        return ZonedDateTime.ofInstant(systemDateTime.toInstant(),utcZone);
    }
}
