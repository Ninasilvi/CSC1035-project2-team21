package csc1035.project2;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.*;

public class InputCheck {
    /**
     * Checks if 'integer' input is valid
     * @param min_val Minimum input value
     * @param max_val Maximum input value
     * @return User input (Integer)
     */
    public int get_int_input(int min_val, int max_val) {
        Scanner s = new Scanner(System.in);
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
        Scanner s = new Scanner(System.in);
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
     * Check if 'String' input is valid
     * @return User input (String)
     */
    public String get_string_input() {
        Scanner s = new Scanner(System.in);
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
     * @return User input (Time hh-mm |String|)
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
}
