package SchedulingHeuristic;

import com.google.api.client.auth.oauth2.Credential;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {
    public static void main(String[] args) {
        DutyCalendar cal = new DutyCalendar(
                LocalDate.of(2015, 05, 19),
                LocalDate.of(2015, 05, 26),
                1,
                2);
        HashMap<LocalDate, Double> points = new HashMap<>();
        points.put(LocalDate.of(2015, 05, 19), 1.0);
        points.put(LocalDate.of(2015, 05, 20), 1.0);
        points.put(LocalDate.of(2015, 05, 21), 1.5);
        points.put(LocalDate.of(2015, 05, 22), 2.0);
        points.put(LocalDate.of(2015, 05, 23), 2.0);
        points.put(LocalDate.of(2015, 05, 24), 1.0);
        points.put(LocalDate.of(2015, 05, 25), 1.0);
        points.put(LocalDate.of(2015, 05, 26), 1.0);
        cal.setDayValues(points);
        ArrayList<LocalDate> micksBlackout = new ArrayList<>();
        micksBlackout.add(LocalDate.of(2015, 05, 20));
        micksBlackout.add(LocalDate.of(2015, 05, 21));
        cal.addRa("Mick", micksBlackout);
        cal.addRa("Tatev", new ArrayList<>());
        cal.addRa("David", new ArrayList<>());
        cal.addRa("Cliff", new ArrayList<>());
        ArrayList<DutyBlock> result = cal.assignDuty();
        System.out.println(cal);
        System.out.println(cal.printRaPointValues());
        
        try (Scanner input = new Scanner(System.in);) {
            Credential cred = GoogleCalendarApiAccess.getStoredCredential("id_1");
            if (cred == null) {
                System.out.println("Paste this code into your browser.");
                System.out.println(GoogleCalendarApiAccess.requestAuthorizationCode());
                System.out.println("Enter code and press enter.");
                String code = input.nextLine();
                GoogleCalendarApiAccess.getTokenCredential(code, "id_1");
                cred = GoogleCalendarApiAccess.getStoredCredential("id_1");
            }
            GoogleCalendarApiAccess.createNewDutyCalendar("Test", "id_1", result);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
