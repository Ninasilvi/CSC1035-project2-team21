package csc1035.project2;

import csc1035.project2.interfaces.InputCheckInterface;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.*;

public class InputCheck implements InputCheckInterface {

    Scanner s = new Scanner(System.in);

    /**
     * Checks if 'integer' input is valid
     * @param min_val Minimum input value
     * @param max_val Maximum input value
     * @return User input (Integer)
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
     * Checks if 'Real Number' input is valid
     * @return User Input (Real Number)
     */
    public double get_double_input() {
        double input;

        while (true) {
            System.out.print("> ");
            try {
                input = s.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("\nTry again: Bad input!");
                s.nextDouble();
            }
        }
        return input;
    }

    /**
     * Check if 'Double' input has 'Room Number' pattern
     * @return User Input (Room Number |String|)
     */
    public double get_roomNum_input() {
        double input;
        String str;
        Pattern p = Pattern.compile("[0-9].[0-9][0-9][0-9]");
        while (true) {
            System.out.print("> ");
            try {
                input = s.nextDouble();
                str = String.valueOf(input);
                Matcher m = p.matcher(str);
                if(m.matches()) {
                    break;
                } else {
                    System.out.println("Try again: input should have 3 numbers after decimal. (e.g. '0.365'");
                }

            } catch (InputMismatchException e) {
                System.out.println("\nTry again: Bad input!");
                s.nextDouble();
            }
        }
        return input;
    }


    /**
     * Check if 'String' input is valid
     * @return User input (String)
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
     * Check if 'String' is actual Time and is Valid
     * @return User input (Time (HH-mm) |String|)
     */
    public String get_time_input() {
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
                for(int i = 0; i < 7; i++)
                    if(i != 6) {
                        System.out.print(days[i].substring(0,1).toUpperCase() + days[i].substring(1) + ", ");
                    } else {
                        System.out.println(days[i].substring(0,1).toUpperCase() + days[i].substring(1));
                    }
                }
        }
        System.out.println("Day: " + input.substring(0,1).toUpperCase() + input.substring(1));
        return input.substring(0,1).toUpperCase() + input.substring(1);
    }
}
