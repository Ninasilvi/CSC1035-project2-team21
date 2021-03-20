package csc1035.project2.interfaces;

import java.util.Scanner;

public interface InputCheckInterface {

    Scanner s = new Scanner(System.in);

    int get_int_input(int min_val, int max_val);

    double get_double_input();

    double get_roomNum_input();

    String get_string_input();

    String get_time_input();

    String get_end_time_input(String startTime);

    String get_day_input();
}
