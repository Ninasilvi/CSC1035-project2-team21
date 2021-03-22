package csc1035.project2;

import csc1035.project2.interfaces.InputCheckInterface;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.*;

public class InputCheck implements InputCheckInterface {

    Scanner s = new Scanner(System.in);

    /**
     * Checks if an integer input is valid and within a specified range.
     * @param min_val Minimum input value
     * @param max_val Maximum input value
     * @return User's integer input
     */
    public int get_int_input(int min_val, int max_val) {
        int input;

        while (true) {
            System.out.print("> ");
            try {
                input = s.nextInt();
                if(input >= min_val && input <= max_val){
                    break;
                } else {
                    System.out.println("\nTry again: Input should be an integer between [" + min_val + "-" + max_val + "]");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nTry again: Input should be an integer between [" + min_val + "-" + max_val + "]");
                s.nextLine();
            }
        }
        return input;
    }

    /**
     * Checks if a string input is not empty.
     * @return User's string input
     */
    public String get_string_input() {
        String input;

        while (true) {
            input = s.nextLine();
            if(input.isEmpty() ) {
                System.out.println("\nTry again: Input cannot be empty!");
                System.out.print("> ");
            } else {
                break;
            }
        }
        return input;
    }

    /**
     * Checks if user's input conforms to time format.
     * @return User's time (HH-mm) input
     */
    public String get_time_input() {
        Scanner s = new Scanner(System.in);
        String input;
        Pattern p = Pattern.compile("(?:[01]?[0-9]|2[0-3]):[0-5][0-9]");

        while (true) {
            boolean pass = true;
            input = s.nextLine();

            if (input.length() == 4 )
                input = "0" + input;

            if (input.length() == 5) {
                Matcher m = p.matcher(input);

                if(!m.matches())
                    pass = false;

            } else {
                pass = false;
            }

            if(input.isEmpty() || !pass) {
                System.out.println("\nTry again: Time should be hours:minutes (e.g 09:30)");
                System.out.print("> ");
            } else {
                break;
            }
        }
        return input;
    }

    /**
     * Checks if user's input conforms to time format and occurs after the starting time.
     * @param startTime Starting time of the time period for which the user's input is the ending time
     * @return User's time (HH-mm) input
     */
    public String get_end_time_input(String startTime) {
        boolean validTime = false;
        String timeEnd = "";
        int sHour = Integer.parseInt(startTime.substring(0,2));
        int sMinute = Integer.parseInt(startTime.substring(3));

        while (!validTime) {
            System.out.println("\nEnter End Time:");
            timeEnd = get_time_input();
            int eHour = Integer.parseInt(timeEnd.substring(0, 2));
            int eMinute = Integer.parseInt(timeEnd.substring(3));

            if (sHour < eHour || (sHour == eHour && sMinute < eMinute)) {
                validTime = true;
            } else {
                System.out.println("\nIt cannot end before it started.");
            }
        }
        return timeEnd;
    }

    /**
     * Checks if a string input represents a day of the week.
     * @return User's day input (day of the week with first letter capitalized)
     */
    public String get_day_input() {
        Scanner s = new Scanner(System.in);
        String input;
        boolean pass = false;

        String[] days = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};

        while (true) {
            input = s.nextLine().toLowerCase();

            for (String day : days) {
                if (input.equals(day)) {
                    pass = true;
                    break;
                }
            }

            if (pass) {
                break;
            } else {
                System.out.println("\nTry again: Your input was incorrect.");
                System.out.print("\nIt should be: ");
                for(int i = 0; i < 7; i++) {
                    String printDay = days[i].substring(0, 1).toUpperCase() + days[i].substring(1);
                    if (i != 6) {
                        System.out.print(printDay + ", ");
                    } else {
                        System.out.println(printDay);
                    }
                }
            }
        }
        return input.substring(0,1).toUpperCase() + input.substring(1);
    }
}
