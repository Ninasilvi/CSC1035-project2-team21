package csc1035.project2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputCheck {
    /**
     * Checks if 'integer' input is valid
     * @param min_val Minimum input value
     * @param max_val Maximum input value
     * @return User input (Integer)
     */
    public int get_int_input(int min_val, int max_val) {
        Scanner s = new Scanner(System.in);
        int input = -1;

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
}
