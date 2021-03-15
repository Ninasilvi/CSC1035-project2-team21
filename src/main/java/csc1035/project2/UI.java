package csc1035.project2;

import java.util.Scanner;

public class UI {

    public static void main(String[] args) {

        printMenu();
    }

    // Print Menu
    public static void printMenu () {

        Scanner s = new Scanner(System.in);
        Timetable t = new Timetable();

        System.out.println("\n Enter an Option [1-6]");
        System.out.println("1 - List Of Students");
        System.out.println("2 - List of Staff");
        System.out.println("3 - List of Module Requirements");
        System.out.println("4 - Allow Create Timetable");
        System.out.println("5 - Producing Timetable");
        System.out.println("6 - Exit \n");

        if (s.hasNextInt()) {
            printMenu();
            int choice = s.nextInt();

            switch (choice) {
                case 1:
                    t.listOfStudents();
                    break;
                case 2:
                    t.listOfStaff();
                    break;
                case 3:
                    t.listOfModuleReq();
                    break;
                case 4:
                    t.allowCreateTimetable();
                    break;
                case 5:
                    t.producingTimetable();
                    break;
                case 6:
                    return;
            }
        } else {
            System.out.println("Please enter an integer.");
            printMenu();
        }
    }
}