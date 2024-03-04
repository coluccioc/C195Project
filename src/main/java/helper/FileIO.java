package helper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

/**
 * Helper Class to manage writing to a file to track login attempts
 */
public class FileIO {
    /**
     * Providing clarity that this will not be instantiated
     */
    private FileIO(){}
    /**
     * Method to write to a file in a local filepath. Tracks User, Success, Date, Time
     * @param user attempted username input
     * @param success bool success
     */
    public static void addLoginAttempt(String user, boolean success) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("loginAttempts.txt",true))){
            ZonedDateTime nowDateTime = ZonedDateTime.now();
            ZonedDateTime nowDateTimeUTC = TimeZoneHelper.translateToUTC(nowDateTime);
            LocalDate nowDateUTC = nowDateTimeUTC.toLocalDate();
            LocalTime nowTimeUTC = nowDateTimeUTC.toLocalTime();
            writer.println("USER INPUT: " + user + ",SUCCESS: " + success + ",DATE: " + nowDateUTC +",TIMESTAMP: "+nowTimeUTC);
        } catch (IOException e) {
            System.out.println("Login not tracked \n" + e);
        }
    }
}