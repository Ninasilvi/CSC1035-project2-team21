package csc1035.project2;

import csc1035.project2.interfaces.UInterface;
import org.hibernate.Session;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UI implements UInterface {

    Timetable t = new Timetable();
    RoomBooking r = new RoomBooking();
    InputCheck ic = new InputCheck();

    public void runMenu() {
        while (true) {
            printMenu();

            int choice = ic.get_int_input(1, 6);

            switch (choice) {
                case 1 -> listmenu();
                case 2 -> timetableVariables();
                case 3 -> timetableChoice();
                case 4 -> changeRoomMenu();
                case 5 -> {
                    System.out.println("\nQuitting...");
                    System.exit(420);
                }
                //Testing room cancelling
                case 6 -> roomCancel();
            }
        }
    }

    // Print Menu
    public void printMenu() {
        System.out.println("1 - view a list");
        System.out.println("2 - Create a Timetable");
        System.out.println("3 - Produce a Timetable");
        System.out.println("4 - Change Room Details");
        System.out.println("5 - Exit");
    }
    public void listmenu() {
        System.out.println("\nPlease enter an option [1-6]");
        System.out.println("1 - List of students taking a particular module");
        System.out.println("2 - List of staff teaching a particular module");
        System.out.println("3 - List of Module Requirements");
        System.out.println("4 - List of Rooms");
        System.out.println("5 - List of booked rooms");
        System.out.println("6 - List of available rooms");

        int choice = ic.get_int_input(1, 6);
        switch (choice) {
            case 1 -> listOfStudentsChoice();
            case 2 -> listOfStaffChoice();
            case 3 -> t.listOfModuleReq();
            case 4 -> listOfRooms();
            case 5 -> bookedRoomsList();
            case 6 -> availableRoomsListMenu();
        }
    }

    // Gets user's input for module choice for list of students in timetable by calling moduleOptions method
    public void listOfStudentsChoice() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        String moduleID = moduleOptions();

        t.listOfStudents(moduleID, se);
    }

    // Prints the list of students from timetable list of students
    public void listOfStudentsResult(List<Student> students) {
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
    public void listOfStaffChoice() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        String moduleID = moduleOptions();

        t.listOfStaff(moduleID, se);
    }

    // Prints all the staff for a particular module
    public void listOfStaffResult(List<Staff> staff) {
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

    public void listOfModuleReqResult(List<ModuleRequirements> moduleRequirements) {
        if (moduleRequirements.size() == 0) {
            System.out.println("\nNo ModuleRequirements were found in this Module");
        } else {
            String printPeopleFormat = "| %-3s | %-10s | %-15s | %-13s | %-14s | %-17s | %-16s | %n";
            System.out.println("Module Requirements");
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
    public String moduleOptions() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();

        List<Module> modules = se.createQuery("FROM Module").list();
        se.getTransaction().commit();

        System.out.println("Please select a module:\n");

        for (int i = 0; i < modules.size(); i++) {
            System.out.println(i + 1 + " - " + modules.get(i));
        }

        int choice = ic.get_int_input(1, modules.size());
        se.close();
        return modules.get(choice - 1).getModuleID();
    }

    // Prints out a list of all rooms
    public void listOfRooms() {
       r.listOfRooms();

        for (int i = 0; i < r.rooms.size(); i++) {
            System.out.println(i + 1 + " - " + r.rooms.get(i));
        }
    }

    // Asks the user which room they would like to book
    public void bookRoomsText() {
        System.out.println("\nWhich room would you like to book?");
    }

    // Informs the user that the room has been booked successfully
    public void roomBookingConfirmation(Room room) {
        System.out.println("\n" + room + " has been successfully booked.");
    }

    // Prints a list of rooms that are already booked
    public void bookedRoomsList() {
        r.listOfRooms();

        if (r.bookedRooms.size() == 0) {
            r.bookedRoomsCheck();
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
    public void roomCancel() {
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
    public void roomCancelConfirmation(Room room) {
        System.out.println("\n" + room + " booking has been successfully cancelled");
        roomCancelNext();
    }

    // Asks the user what they would like to do after cancelling a room
    public void roomCancelNext() {
        System.out.println("\nWhat would you like to do next?");
        System.out.println("1 - Cancel another room booking");
        System.out.println("2 - Return to main menu");
        int choice = ic.get_int_input(1,2);

        switch (choice) {
            case 1 -> roomCancel();
            case 2 -> runMenu();
        }
    }

    // A menu for viewing available rooms
    public void availableRoomsListMenu() {

        System.out.println("\nPlease pick an option:");
        System.out.println("1 - View all rooms that have not been booked");
        System.out.println("2 - View available rooms for a specific time");
        System.out.println("3 - Go back");

        int choice = ic.get_int_input(1, 3);

        switch (choice) {
            case 1 -> availableRoomsList();
            case 2 -> availableRoomsDTList();
            case 3 -> runMenu();
        }

        System.out.println("\nWhat would you like to do next?\n");
        System.out.println("1 - View another available rooms list");
        System.out.println("2 - Return to main menu");

        choice = ic.get_int_input(1, 2);

        switch (choice) {
            case 1 -> availableRoomsListMenu();
            case 2 -> runMenu();
        }
    }

    // Prints a list of rooms that have not been booked
    public void availableRoomsList() {
        r.availableRooms();

        if (r.availableRooms.size() == 0) {
            System.out.println("\nAll rooms are booked.\n");
        } else {
            System.out.println("\nUnbooked rooms:\n");

            for (int i = 0; i < r.availableRooms.size(); i++) {
                System.out.println(i + 1 + " - " + r.availableRooms.get(i));
            }
        }
    }

    // Prints a list of rooms that are available for a certain day and time
    public void availableRoomsDTList() {
        System.out.println("\nEnter Start Time: ");
        String timeStart = ic.get_time_input();
        String timeEnd = ic.get_end_time_input(timeStart);

        System.out.println("\nEnter the Day: ");
        String day = ic.get_day_input();

        r.availableRoomsDT(timeStart, timeEnd, day);

        System.out.println("\nAvailable Rooms from " + timeStart + " to " + timeEnd + " on " + day + ":\n");

        for (int i = 0; i < r.availableRooms.size(); i++) {
            System.out.println(i + 1 + " - " + r.availableRooms.get(i));
        }
    }

    public void timetableChoice () {
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

    public void timetableStudentsChoice() {
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

    public void timetableStudentsResult(List<Student> students, int choice, List<Time> time) {
        String name = students.get(choice-1).getFirstName() + " " +
                students.get(choice-1).getLastName() + " (ID: " + students.get(choice-1).getStudentID() + ")";

        if (!(time.size() == 0)) {
            System.out.println("\nTimetable for " + name);
        }
        timetableFormat(time, name);
        timetableNext();
    }

    public void timetableStaffChoice() {
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

    public void timetableStaffResult(List<Staff> staff, int choice, List<Time> time) {
        String name = staff.get(choice-1).getFirstName() + " " +
                staff.get(choice-1).getLastName() + " (ID: " + staff.get(choice-1).getStaffID() + ")";

        if (!(time.size() == 0)) {
            System.out.println("\nTimetable for " + name);
        }
        timetableFormat(time, name);
        timetableNext();
    }

    // Takes user input for a room choice
    public void timetableRoomsChoice() {
        listOfRooms();
        System.out.println("\nPlease pick a room whose timetable you want to produce:");
        int choice = ic.get_int_input(1, r.rooms.size());

        r.producingRoomTimetable(choice);
    }

    // Prints out the results
    public void timetableRoomsResult(Room room, List<Time> time) {
        if (!(time.size() == 0)) {
            System.out.println("\nTimetable for " + room);
        }
        timetableFormat(time, String.valueOf(room));
        timetableNext();
    }

    public void timetableFormat(List<Time> time, String info) {
        if (time.size() == 0) {
            System.out.println("\nThe timetable for " + info + " is empty.");
        } else {
            String printTimeFormat = "| %-3s | %-14s | %-9s | %-11s | %-8s | %-11s |%n";
            System.out.println("+-----+----------------+-----------+-------------+----------+-------------+");
            System.out.println("| Row | Timetable Name | Day       | Time        | ModuleID | Room Number |");
            System.out.println("+-----+----------------+-----------+-------------+----------+-------------+");

            for (int i = 0; i < time.size(); i++) {
                System.out.format(printTimeFormat, i + 1, time.get(i).getTimetableName(), time.get(i).getDay(),
                        time.get(i).getTimeStart() + "-" + time.get(i).getTimeEnd(), time.get(i).getModuleID(),
                        time.get(i).getRoomNumber());
            }
            System.out.println("+-----+----------------+-----------+-------------+----------+-------------+");
        }
    }

    public void timetableNext() {
        System.out.println("\nWhat would you like to do next?");
        System.out.println("1 - View a different timetable");
        System.out.println("2 - Return to main menu");

        int choice = ic.get_int_input(1, 2);

        switch (choice) {
            case 1 -> timetableChoice();
            case 2 -> runMenu();
        }
    }

    public void changeRoomMenu() {
        System.out.println("\nWhich room detail would you like to change?");
        System.out.println("1 - Room Type");
        System.out.println("2 - Maximum Room Capacity");
        System.out.println("3 - Social Distancing Capacity");
        System.out.println("4 - Go back");

        int choice = ic.get_int_input(1, 4);
        String detail = "";

        switch (choice) {
            case 1 -> detail = "type";
            case 2 -> detail = "maximum capacity";
            case 3 -> detail = "social distancing capacity";
            case 4 -> runMenu();
        }
        changeRoomChoice(detail);
    }

    public void changeRoomChoice(String detail) {
        listOfRooms();

        System.out.println("\nWhich room's " + detail + " would you like to change?");

        int choice = ic.get_int_input(1, r.rooms.size());
        Room room = r.rooms.get(choice - 1);

        switch (detail) {
            case "type" -> changeRoomTypeChoice(room);
            case "maximum capacity" -> changeRoomCapacityChoice(room);
            case "social distancing capacity" -> changeRoomSocDistCapacityChoice(room);
        }
    }

    public void changeRoomTypeChoice(Room room) {
        System.out.println("\nPlease enter a new Room Type for " + room.toString());

        String newType = ic.get_string_input();

        r.changeRoomType(room, newType);
    }

    public void changeRoomCapacityChoice(Room room) {
        System.out.println("\nPlease enter a new Room Maximum Capacity for " + room.toString());

        int newCapacity = ic.get_int_input(1, 300);

        r.changeRoomCapacity(room, newCapacity);
    }

    public void changeRoomSocDistCapacityChoice(Room room) {
        System.out.println("\nPlease enter a new Room Social Distancing Capacity for " + room.toString());

        int newCapacity = ic.get_int_input(1,50);

        r.changeRoomSocDistCapacity(room, newCapacity);
    }

    public void changeRoomResult(Room room) {
        System.out.println("\nSuccessfully changed. New room:\n" + room);
        System.out.println("\nWhat would you like to do next?");
        System.out.println("1 - Change another room detail");
        System.out.println("2 - Print a list of all rooms and go back to main menu");
        System.out.println("2 - Go back to main menu");

        int choice = ic.get_int_input(1, 2);

        switch (choice) {
            case 1 -> changeRoomMenu();
            case 2 -> listOfRooms();
            case 3 -> runMenu();
        }
    }

    public void timetableVariables() {
        InputCheck ic = new InputCheck();

        System.out.print("\nEnter a Day:\n");
        String day = ic.get_day_input();

        System.out.println("\nEnter Class name:");
        String timetableName = ic.get_string_input();

        String moduleID = moduleOptions();
        String timeStart;
        String timeEnd;

        System.out.println("\nEnter Module Start Time:");
        timeStart = ic.get_time_input();
        timeEnd = ic.get_end_time_input(timeStart);
        Room room = timetableAvailableRooms(timeStart, timeEnd, day);

        t.allowCreateTimetable(day, timetableName, moduleID, timeStart, timeEnd, room);
    }

    public Room timetableAvailableRooms(String timeStart, String timeEnd, String day) {
        r.availableRoomsDT(timeStart, timeEnd, day);
        Room room = null;

        if (r.availableRooms.size() != 0) {
            System.out.println("\nPlease pick a room:\n");
            System.out.println("\nAvailable Rooms from " + timeStart + " to " + timeEnd + " on " + day + ":\n");
            for (int i = 0; i < r.availableRooms.size(); i++) {
                System.out.println(i + 1 + " - " + r.availableRooms.get(i));
            }
            int choice = ic.get_int_input(1, r.availableRooms.size());
            room = r.availableRooms.get(choice - 1);
        } else {
            System.out.println("\nThere are no rooms available from " + timeStart + " to " + timeEnd + " on " + day + ".");
            System.out.println("Would you like to try again?");
            System.out.println("1 - Yes");
            System.out.println("2 - No");

            int choice = ic.get_int_input(1, 2);

            switch (choice) {
                case 1 -> timetableVariables();
                case 2 -> runMenu();
            }
        }
        return room;
    }
}
