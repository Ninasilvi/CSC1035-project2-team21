package csc1035.project2;

import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

public class UI {

    static Scanner s = new Scanner(System.in);
    static Timetable t = new Timetable();
    static RoomBooking r = new RoomBooking();
    static InputCheck ic = new InputCheck();

    public static void main(String[] args) {

        printMenu();
    }

    // Print Menu
    public static void printMenu () {

        System.out.println("\nPlease enter an option [1-6]:");
        System.out.println("1 - List Of Students Taking a Specific Module");
        System.out.println("2 - List of Staff Teaching a Specific Module");
        System.out.println("3 - List of Module Requirements");
        System.out.println("4 - List of Rooms");
        System.out.println("5 - List of Booked Rooms");
        System.out.println("6 - Create a Timetable");
        System.out.println("7 - Produce a Timetable for a Room");
        System.out.println("8 - Exit \n");

        int choice = ic.get_int_input(1, 9);

        switch (choice) {
            case 1 -> listOfStudentsChoice();
            case 2 -> t.listOfStaff();
            case 3 -> t.listOfModuleReq();
            case 4 -> r.listOfRooms();
            case 6 -> t.allowCreateTimetable();
            case 7 -> t.producingTimetable();
            case 8 -> System.exit(420);
            //Testing room booking
            case 9 -> r.bookRooms();
        }
    }

    public static void listOfStudentsChoice() {
        Session se = HibernateUtil.getSessionFactory().openSession();

        se.beginTransaction();
        List<Module> modules = se.createQuery("FROM Module").list();
        se.getTransaction().commit();

        // Print results
        System.out.println("Please select a module:\n");

        for (int i = 0; i < modules.size(); i++) {
            System.out.println(i + 1 + " - " + modules.get(i));
        }

        int choice = ic.get_int_input(1, modules.size());
        String moduleID = "'" + modules.get(choice - 1).getModuleID() + "'";
        t.listOfStudents(moduleID, se);
    }

    public static void bookRoomsUI() {
        System.out.println("\nWhich room would you like to book?");
    }

    public static void roomAlreadyBooked() {
        System.out.println("\nThis room is already booked.");
        roomBookingNext();
    }

    public static void roomBookingConfirmation(Rooms room) {
        System.out.println("\n" + room + " has been successfully booked.");
        roomBookingNext();
    }

    public static void roomBookingNext() {
        System.out.println("\nWhat would you like to do next?");
        System.out.println("1 - Book another room");
        System.out.println("2 - Return to main menu");
        int choice = ic.get_int_input(1,2);

        switch (choice) {
            case 1 -> r.bookRooms();
            case 2 -> printMenu();
        }
    }

}