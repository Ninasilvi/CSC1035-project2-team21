package csc1035.project2;

import java.util.Scanner;

public interface InputCheckInterface {

    Scanner s = new Scanner(System.in);

    int get_int_input(int min_val, int max_val);

    double get_double_input();

    String get_string_input();

    String get_time_input();
}
