package helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileIO {
    private FileIO(){}
    public static void addLoginAttempt(String user, boolean success) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("loginAttempts.txt",true))){
            writer.println("USER INPUT: " + user + "  |  SUCCESS: " + success);
        } catch (IOException e) {
            System.out.println("Login not tracked \n" + e.toString());
        }
    }
}
