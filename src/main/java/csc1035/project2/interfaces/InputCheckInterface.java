package csc1035.project2.interfaces;

public interface InputCheckInterface {

    int get_int_input(int min_val, int max_val);

    String get_string_input();

    String get_time_input();

    String get_end_time_input(String startTime);

    String get_day_input();
}
