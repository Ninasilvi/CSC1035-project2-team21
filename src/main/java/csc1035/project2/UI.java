package csc1035.project2;

import org.hibernate.Session;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UI {

    static Timetable t = new Timetable();
    static RoomBooking r = new RoomBooking();
    static InputCheck ic = new InputCheck();

    public static void main(String[] args) {
        runMenu();
    }

    public static void runMenu() {
        while (true) {
            printMenu();

            int choice = ic.get_int_input(1, 12);

            switch (choice) {
                case 1 -> listOfStudentsChoice();
                case 2 -> listOfStaffChoice();
                case 3 -> t.listOfModuleReq();
                case 4 -> listOfRooms();
                case 5 -> bookedRoomsList();
                case 6 -> availableRoomsList();
                case 7 -> t.allowCreateTimetable();
                case 8 -> timetableChoice();
                case 9 -> {
                    System.out.println("\nQuitting...");
                    System.exit(420);
                }
                //Testing room booking
                case 10 -> r.bookRooms();
                //Testing room cancelling
                case 11 -> roomCancel();
            }
        }
    }

    // Print Menu
    private static void printMenu() {
        System.out.println("\nPlease enter an option [1-6]:");
        System.out.println("1 - List Of Students Taking a Specific Module");
        System.out.println("2 - List of Staff Teaching a Specific Module");
        System.out.println("3 - List of Module Requirements");
        System.out.println("4 - List of Rooms");
        System.out.println("5 - List of Booked Rooms");
        System.out.println("6 - List of Available Rooms");
        System.out.println("7 - Create a Timetable");
        System.out.println("8 - Produce a Timetable");
        System.out.println("9 - Exit");
    }


    // Gets user's input for module choice for list of students in timetable by calling moduleOptions method
    public static void listOfStudentsChoice() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        String moduleID = moduleOptions(se);

        t.listOfStudents(moduleID, se);

    }

    // Prints the list of students from timetable list of students
    public static void listOfStudentsResult(List<Student> students) {
        if (students.size() == 0) {
            System.out.println("\nNo Students were found in this Module");
        } else {
            String printPeopleFormat = "| %-3s | %-10s | %-20s | %-25s |%n";
            System.out.println("+-----+------------+----------------------+---------------------------+");
            System.out.println("| Row | StudentID  | First Name           | Last Name                 |");
            System.out.println("+-----+------------+----------------------+---------------------------+");

            for (int i = 0; i < students.size(); i++) {
                System.out.format(printPeopleFormat, i + 1, students.get(i).getStudentID(), students.get(i).getFirstName(), students.get(i).getLastName());
            }
            System.out.println("+-----+------------+----------------------+---------------------------+");
        }
    }

    // Gets module choice for list of staff by calling moduleOptions method
    public static void listOfStaffChoice() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        String moduleID = moduleOptions(se);

        t.listOfStaff(moduleID, se);
    }

    // Prints all the staff for a particular module
    public static void listOfStaffResult(List<Staff> staff) {
        if (staff.size() == 0) {
            System.out.println("\nNo Staff were found in this Module");
        } else {
            String printPeopleFormat = "| %-3s | %-10s | %-20s | %-25s |%n";
            System.out.println("+-----+------------+----------------------+---------------------------+");
            System.out.println("| Row | StaffID    | First Name           | Last Name                 |");
            System.out.println("+-----+------------+----------------------+---------------------------+");

            for (int i = 0; i < staff.size(); i++) {
                System.out.format(printPeopleFormat, i + 1, staff.get(i).getStaffID(), staff.get(i).getFirstName(), staff.get(i).getLastName());
            }
            System.out.println("+-----+------------+----------------------+---------------------------+");
        }
    }

    public static void listOfModuleReqResult(List<ModuleRequirements> moduleRequirements) {
        if (moduleRequirements.size() == 0) {
            System.out.println("\nNo ModuleRequirements were found in this Module");
        } else {
            String printPeopleFormat = "| %-3s | %-10s | %-15s | %-13s | %-14s | %-17s | %-16s | %n";
            System.out.println("+-----+------------+-----------------+---------------+----------------+-------------------+------------------+");
            System.out.println("| Row | ModuleID   | Week Commencing | Lectures/week | Lecture Length | Practicals / week | Practical Length |");
            System.out.println("+-----+------------+-----------------+---------------+----------------+-------------------+------------------+");

            for (int i = 0; i < moduleRequirements.size(); i++) {
                //Convert Date with Time to String without Time
                Date date = moduleRequirements.get(i).getWeekCommencing();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = dateFormat.format(date);
                System.out.format(printPeopleFormat, i + 1, moduleRequirements.get(i).getModuleID(), strDate, moduleRequirements.get(i).getLecturesPerWeek(), moduleRequirements.get(i).getLectureLength(), moduleRequirements.get(i).getPracticalsPerWeek(), moduleRequirements.get(i).getPracticalLength());
            }
            System.out.println("+-----+------------+-----------------+---------------+----------------+-------------------+------------------+");
        }
    }

    // Prints module options and takes user input
    public static String moduleOptions(Session se) {
        se.beginTransaction();
        List<Module> modules = se.createQuery("FROM Module").list();
        se.getTransaction().commit();

        System.out.println("Please select a module:\n");

        for (int i = 0; i < modules.size(); i++) {
            System.out.println(i + 1 + " - " + modules.get(i));
        }

        int choice = ic.get_int_input(1, modules.size());
        return "'" + modules.get(choice - 1).getModuleID() + "'";
    }


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

    // Informs the user that the room has been booked successfully
    public static void roomBookingConfirmation(Room room) {
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
            case 2 -> runMenu();
        }
    }

    // Prints a list of rooms that are already booked
    public static void bookedRoomsList() {
        r.listOfRooms();

        if (r.bookedRooms.size() == 0) {
            r.bookedRoomsFile();
        }
        if (r.bookedRooms.size() == 0) {
            System.out.println("\nThere are no rooms currently booked.");
        } else {
            System.out.println("\nBooked rooms:\n");

            for (int i = 0; i < r.bookedRooms.size(); i++) {
                System.out.println(i + 1 + " - " + r.bookedRooms.get(i));
            }
        }
    }

    // Asks the user which room booking they would like to cancel
    public static void roomCancel() {
        bookedRoomsList();

        if (r.bookedRooms.size() == 0) {
            System.out.println("\nYou cannot cancel room bookings without any rooms booked.");
            runMenu();
        } else {
            System.out.println("\nWhich room booking would you like to cancel?\n");
        }
        r.cancelRooms();
    }

    // Informs the user that their room booking was cancelled.
    public static void roomCancelConfirmation(Room room) {
        System.out.println("\n" + room + " booking has been successfully cancelled");
        roomCancelNext();
    }

    // Asks the user what they would like to do after cancelling a room
    public static void roomCancelNext() {
        System.out.println("\nWhat would you like to do next?");
        System.out.println("1 - Cancel another room booking");
        System.out.println("2 - Return to main menu");
        int choice = ic.get_int_input(1,2);

        switch (choice) {
            case 1 -> roomCancel();
            case 2 -> runMenu();
        }
    }

    // Prints out a list of available rooms
    public static void availableRoomsList() {
        r.availableRooms();
        System.out.println("\nAvailable Rooms:\n");

        for (int i = 0; i < r.availableRooms.size(); i++) {
            System.out.println(i + 1 + " - " + r.availableRooms.get(i));
        }
    }

    public static void timetableChoice () {
        System.out.println("\nSelect one of the options:");
        System.out.println("1 - Timetable for Students");
        System.out.println("2 - Timetable for Staff Members");
        System.out.println("3 - Timetable for Rooms");
        System.out.println("4 - Go back");

        int choice = ic.get_int_input(1,3);

        switch (choice) {
            case 1 -> timetableStudentsChoice();
            case 2 -> timetableStaffChoice();
            case 3 -> timetableRoomsChoice();
            case 4 -> runMenu();
        }
    }

    public static void timetableStudentsChoice() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();
        List<Student> students = se.createQuery("FROM Student").list();

        System.out.println("\nPlease pick a student whose timetable you want to produce:\n");

        for (int i = 0; i < students.size(); i++) {
            System.out.println(i+1 + " - " + students.get(i).getStudentID() + " " +
                    students.get(i).getFirstName() + " " + students.get(i).getLastName());
        }

        int choice = ic.get_int_input(1, students.size());

        t.producingStudentTimetable(choice, se, students);
    }

    public static void timetableStudentsResult(List<Student> students, int choice, List<Time> time) {
        String name = students.get(choice-1).getFirstName() + " " +
                students.get(choice-1).getLastName() + " (ID: " + students.get(choice-1).getStudentID() + ")";

        if (!(time.size() == 0)) {
            System.out.println("\nTimetable for " + name);
        }
        timetableFormat(time, name);
        timetableNext();
    }

    public static void timetableStaffChoice() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();
        List<Staff> staff = se.createQuery("FROM Staff").list();

        System.out.println("\nPlease pick a staff member whose timetable you want to produce:\n");

        for (int i = 0; i < staff.size(); i++) {
            System.out.println(i+1 + " - " + staff.get(i).getStaffID() + " " +
                    staff.get(i).getFirstName() + " " + staff.get(i).getLastName());
        }
        int choice = ic.get_int_input(1, staff.size());

        t.producingStaffTimetable(choice, se, staff);
    }

    public static void timetableStaffResult(List<Staff> staff, int choice, List<Time> time) {
        String name = staff.get(choice-1).getFirstName() + " " +
                staff.get(choice-1).getLastName() + " (ID: " + staff.get(choice-1).getStaffID() + ")";

        if (!(time.size() == 0)) {
            System.out.println("\nTimetable for " + name);
        }
        timetableFormat(time, name);
        timetableNext();
    }

    // Takes user input for a room choice
    public static void timetableRoomsChoice() {
        listOfRooms();
        System.out.println("\nPlease pick a room whose timetable you want to produce:");
        int choice = ic.get_int_input(1, r.rooms.size());

        r.producingRoomTimetable(choice);
    }

    // Prints out the results
    public static void timetableRoomsResult(Room room, List<Time> time) {
        if (!(time.size() == 0)) {
            System.out.println("\nTimetable for " + room);
        }
        timetableFormat(time, String.valueOf(room));
        timetableNext();
    }

    public static void timetableFormat(List<Time> time, String info) {
        if (time.size() == 0) {
            System.out.println("\nThe timetable for " + info + " is empty.");
        } else {
            String printTimeFormat = "| %-3s | %-14s | %-8s | %-11s | %-8s | %-11s |%n";
            System.out.println("+-----+----------------+----------+-------------+----------+-------------+");
            System.out.println("| Row | Timetable Name | Day      | Time        | ModuleID | Room Number |");
            System.out.println("+-----+----------------+----------+-------------+----------+-------------+");

            for (int i = 0; i < time.size(); i++) {
                System.out.format(printTimeFormat, i + 1, time.get(i).getTimetableName(), time.get(i).getDay(),
                        time.get(i).getTimeStart() + "-" + time.get(i).getTimeEnd(), time.get(i).getModuleID(),
                        time.get(i).getRoomNumber());
            }
            System.out.println("+-----+----------------+----------+-------------+----------+-------------+");
        }
    }

    public static void timetableNext() {
        System.out.println("\nWhat would you like to do next?");
        System.out.println("1 - View a different timetable");
        System.out.println("2 - Return to main menu");

        int choice = ic.get_int_input(1, 2);

        switch (choice) {
            case 1 -> timetableChoice();
            case 2 -> runMenu();
        }
    }
}
