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
        System.out.println("6 - List of Available Rooms");
        System.out.println("7 - Create a Timetable");
        System.out.println("8 - Produce a Timetable for a Room");
        System.out.println("9 - Exit \n");

        int choice = ic.get_int_input(1, 10);

        switch (choice) {
            case 1 -> listOfStudentsChoice();
            case 2 -> t.listOfStaff();
            case 3 -> t.listOfModuleReq();
            case 4 -> listOfRooms();
            case 5 -> bookedRoomsList();
            case 6 -> availableRoomsList();
            case 7 -> t.allowCreateTimetable();
            case 8 -> t.producingTimetable();
            case 9 -> System.exit(420);
            //Testing room booking
            case 10 -> r.bookRooms();
        }
    }

    // Gets user's input for module choice for list of students in timetable
    public static void listOfStudentsChoice() {
        Session se = HibernateUtil.getSessionFactory().openSession();

        se.beginTransaction();
        List<Module> modules = se.createQuery("FROM Module").list();
        se.getTransaction().commit();

        System.out.println("Please select a module:\n");

        for (int i = 0; i < modules.size(); i++) {
            System.out.println(i + 1 + " - " + modules.get(i));
        }

        int choice = ic.get_int_input(1, modules.size());
        String moduleID = "'" + modules.get(choice - 1).getModuleID() + "'";
        t.listOfStudents(moduleID, se);
    }

    // Prints the list of students from timetable list of students
    public static void listOfStudentsResult(List<Student> students) {
        for(int i = 0; i < students.size(); i++) {
            System.out.println(i+1 + " - ID: " + students.get(i).getStudentID() + " | First Name: " +
                    students.get(i).getFirstName() + " | Last Name: " + students.get(i).getLastName());
        }
        if(students.size() == 0) {
            System.out.println("\nNo Students were found in this Module");
        }
        printMenu();
    }


    // Space for list of Staff


    // Prints out a list of all rooms
    public static void listOfRooms() {
        r.listOfRooms();

        for (int i = 0; i < r.rooms.size(); i++) {
            System.out.println(i + 1 + " - " + r.rooms.get(i));
        }
    }

    // Asks the user which room they would like to book
    public static void bookRoomsText() {
        System.out.println("\nWhich room would you like to book?");
    }

    // Informs the user that the room is already booked
    public static void roomAlreadyBooked() {
        System.out.println("\nThis room is already booked.");
        roomBookingNext();
    }

    // Informs the user that the room has been booked successfully
    public static void roomBookingConfirmation(Rooms room) {
        System.out.println("\n" + room + " has been successfully booked.");
        roomBookingNext();
    }

    // Asks the user what would they like to do after booking a room, redirecting to r.bookRooms or printMenu
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

    // Prints a list of rooms that are already booked
    // Does not work when rooms are booked during the same code execution
    public static void bookedRoomsList() {
        r.listOfRooms();
        r.bookedRoomsFile();

        if (r.bookedRooms.size() == 0) {
            System.out.println("\nThere are no rooms currently booked.");
        } else {
            System.out.println("\nBooked rooms:\n");
            for (int i = 0; i < r.bookedRooms.size(); i++) {
                System.out.println(i + 1 + " - " + r.bookedRooms.get(i));
            }
        }
        printMenu();
    }

    // Prints out a list of available rooms
    // Does not work when rooms are booked during the same code execution
    public static void availableRoomsList() {
        r.availableRooms();

        System.out.println("\nAvailable Rooms:\n");
        for (int i = 0; i < r.availableRooms.size(); i++) {
            System.out.println(i + 1 + " - " + r.availableRooms.get(i));
        }
        printMenu();
    }

}