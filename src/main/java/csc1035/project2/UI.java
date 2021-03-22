package csc1035.project2;

import csc1035.project2.interfaces.UInterface;
import org.hibernate.Session;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UI implements UInterface {

    Timetable t = new Timetable();
    RoomBooking r = new RoomBooking();
    InputCheck ic = new InputCheck();

    /**
     * Runs main menu.
     */
    public void runMenu() {
        while (true) {
            printMenu();

            int choice = ic.get_int_input(1, 7);

            switch (choice) {
                case 1 -> listMenu();
                case 2 -> timetableVariables();
                case 3 -> timetableChoice();
                case 4 -> changeRoomMenu();
                case 5 -> bookRoom();
                case 6 -> roomCancel();
                case 7 -> {
                    System.out.println("\nQuitting...");
                    System.exit(420);
                }
            }
        }
    }

    /**
     * Prints out menu options.
     */
    public void printMenu() {
        System.out.println("\nPlease enter an option [1-5]:");
        System.out.println("[1] - View a list");
        System.out.println("[2] - Create a Timetable");
        System.out.println("[3] - Produce a Timetable");
        System.out.println("[4] - Change Room Details");
        System.out.println("[5] - Book a Room");
        System.out.println("[6] - Cancel a Room Booking");
        System.out.println("[7] - Exit");
    }

    /**
     * Prints out a menu for viewing different lists.
     */
    public void listMenu() {
        System.out.println("\nPlease enter an option [1-7]:");
        System.out.println("[1] - List of Students taking a module");
        System.out.println("[2] - List of Staff teaching a module");
        System.out.println("[3] - List of Module Requirements");
        System.out.println("[4] - List of Rooms");
        System.out.println("[5] - List of Booked Rooms");
        System.out.println("[6] - List of Available Rooms");
        System.out.println("[7] - Go Back");


        int choice = ic.get_int_input(1, 7);
        switch (choice) {
            case 1 -> listOfStudentsChoice();
            case 2 -> listOfStaffChoice();
            case 3 -> t.listOfModuleReq();
            case 4 -> listOfRooms();
            case 5 -> bookedRoomsList();
            case 6 -> availableRoomsListMenu();
        }
    }

    /**
     * Gets user's input for a module choice (moduleOptions) and passes the
     * chosen module to create a list of students taking it (listOfStudents).
     */
    public void listOfStudentsChoice() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        String moduleID = moduleOptions();

        t.listOfStudents(moduleID, se);
    }

    /**
     * Prints out a list of students taking a module.
     * @param students List of students to be printed
     */
    public void listOfStudentsResult(List<Student> students) {
        if (students.size() == 0) {
            System.out.println("\nNo students were found for this module.");
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

    /**
     * Gets user's input for a module choice (moduleOptions) and passes the
     * chosen module to create a list of staff teaching it (listOfStaff).
     */
    public void listOfStaffChoice() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        String moduleID = moduleOptions();

        t.listOfStaff(moduleID, se);
    }

    /**
     * Prints out a list of staff members teaching a module.
     * @param staff List of staff members to be printed
     */
    public void listOfStaffResult(List<Staff> staff) {
        if (staff.size() == 0) {
            System.out.println("\nNo staff were found for this module.");
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

    /**
     * Prints out all module requirements.
     * @param moduleRequirements List of module requirements
     */
    public void listOfModuleReqResult(List<ModuleRequirements> moduleRequirements) {
        if (moduleRequirements.size() == 0) {
            System.out.println("\nNo module requirements were found for this module.\n");
        } else {
            String printPeopleFormat = "| %-3s | %-10s | %-15s | %-13s | %-14s | %-17s | %-16s | %n";
            System.out.println("\nModule Requirements:\n");
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

    /**
     * Prints module options and takes user's input
     * @return Module ID of user's chosen module
     */
    public String moduleOptions() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();

        List<Module> modules = se.createQuery("FROM Module").list();
        se.getTransaction().commit();

        System.out.println("\nPlease select a module:\n");

        for (int i = 0; i < modules.size(); i++) {
            System.out.println(i + 1 + " - " + modules.get(i));
        }

        int choice = ic.get_int_input(1, modules.size());
        se.close();
        return modules.get(choice - 1).getModuleID();
    }

    /**
     * Prints out a list of all rooms.
     */
    public void listOfRooms() {
       r.listOfRooms();

       if (r.rooms.size() == 0) {
           System.out.println("\nThere are no rooms currently recorded.\n");
       } else {
           System.out.println("\nAll rooms currently recorded:\n");
           for (int i = 0; i < r.rooms.size(); i++) {
               System.out.println(i + 1 + " - " + r.rooms.get(i));
           }
       }
    }

    /**
     * Informs the user that the room has been booked successfully
     * @param room Room that was booked
     */
    public void roomBookingConfirmation(Room room) {
        System.out.println("\n" + room + " has been successfully booked.");
    }

    /**
     * Asks the user what would they like to do after booking a room and
     * redirects them based on their choice.
     */
    public void roomBookingNext() {
        System.out.println("\nWhat would you like to do next?");
        System.out.println("1 - Book another room");
        System.out.println("2 - Return to main menu");
        int choice = ic.get_int_input(1,2);

        switch (choice) {
            case 1 -> bookRoom();
            case 2 -> runMenu();
        }
    }

    /**
     * Prints a list of booked rooms.
     */
    public void bookedRoomsList() {
        List<Time> times = r.bookedRoomsCheck();
        if (times.size() == 0) {
            System.out.println("\nThere are no booked rooms.");
        } else {
            System.out.println("\nBooked Rooms:\n");
            timetableFormat(times, "booked rooms");
        }
    }

    /**
     * Asks the user which room booking they would like to cancel.
     */
    public void roomCancel() {
        bookedRoomsList();
        List<Time> times = r.bookedRoomsCheck();

        if (times.size() == 0) {
            System.out.println("\nYou cannot cancel room bookings without any rooms booked.");
            runMenu();
        } else {
            System.out.println("\nWhich room booking would you like to cancel?\n");
        }
        int choice = ic.get_int_input(1, times.size());
        Time time = times.get(choice - 1);
        r.cancelRooms(time.getId());
    }

    /**
     * Informs the user that their room booking was successfully cancelled.
     * @param room Room that was cancelled
     * @param times A list containing 1 timetable entry which had the room that was cancelled
     */
    public void roomCancelConfirmation(Room room, List<Time> times) {
        System.out.println("\n" + room + " booking has been successfully cancelled");
        timetableFormat(times, "Your room cancellation");
        roomCancelNext();
    }

    /**
     * Asks the user what they would like to do after cancelling a room.
     */
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

    /**
     * A menu for viewing available rooms.
     */
    public void availableRoomsListMenu() {

        System.out.println("\nPlease enter an option [1-3]:");
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

    /**
     * Prints a list of rooms that have not been booked.
     */
    public void availableRoomsList() {
        r.availableRooms();

        if (r.availableRooms.size() == 0) {
            System.out.println("\nAll rooms are booked.\n");
        } else {
            System.out.println("\nUnbooked rooms:\n");
            availableRoomsPrint();
        }
    }

    /**
     * Prints a list of rooms that are available for a certain day and time.
     */
    public void availableRoomsDTList() {
        System.out.println("\nEnter Start Time: ");
        String timeStart = ic.get_time_input();
        String timeEnd = ic.get_end_time_input(timeStart);

        System.out.println("\nEnter the Day: ");
        String day = ic.get_day_input();

        r.availableRoomsDT(timeStart, timeEnd, day);

        System.out.println("\nAvailable Rooms from " + timeStart + " to " + timeEnd + " on " + day + ":\n");
        availableRoomsPrint();
    }

    /**
     * Prints a list of available rooms for availableRoomsList() and availableRoomsDTList().
     */
    public void availableRoomsPrint() {
        for (int i = 0; i < r.availableRooms.size(); i++) {
            System.out.println(i + 1 + " - " + r.availableRooms.get(i));
        }
    }

    /**
     * Prints options of which timetable the user wants to see, redirecting them based on their choice.
     */
    public void timetableChoice () {
        System.out.println("\nPlease enter an option [1-4]:");
        System.out.println("[1] - Timetable for Students");
        System.out.println("[2] - Timetable for Staff Members");
        System.out.println("[3] - Timetable for Rooms");
        System.out.println("[4] - Go back");

        int choice = ic.get_int_input(1,4);

        switch (choice) {
            case 1 -> timetableStudentsChoice();
            case 2 -> timetableStaffChoice();
            case 3 -> timetableRoomsChoice();
            case 4 -> runMenu();
        }
    }

    /**
     * Prints a list of student options, asking the user to choose
     * which student's timetable should be printed out.
     */
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

        t.producingStudentTimetable(students.get(choice - 1), se);
    }

    /**
     * Prints a timetable for a student.
     * @param student The student whose timetable is printed
     * @param time The timetable
     */
    public void timetableStudentsResult(Student student, List<Time> time) {
        String name = student.getFirstName() + " " +
                student.getLastName() + " (ID: " + student.getStudentID() + ")";

        if (!(time.size() == 0)) {
            System.out.println("\nTimetable for " + name);
        }
        timetableFormat(time, name);
        timetableNext();
    }

    /**
     * Prints a list of staff member options, asking the user to choose
     * which staff member's timetable should be printed out.
     */
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

        t.producingStaffTimetable(staff.get(choice - 1), se);
    }

    /**
     * Prints a timetable for a staff member.
     * @param staff The staff member whose timetable is printed
     * @param time The timetable
     */
    public void timetableStaffResult(Staff staff, List<Time> time) {
        String name = staff.getFirstName() + " " +
                staff.getLastName() + " (ID: " + staff.getStaffID() + ")";

        if (!(time.size() == 0)) {
            System.out.println("\nTimetable for " + name);
        }
        timetableFormat(time, name);
        timetableNext();
    }

    /**
     * Takes user input for a room choice.
     */
    public void timetableRoomsChoice() {
        listOfRooms();
        System.out.println("\nPlease pick a room whose timetable you want to produce:");
        int choice = ic.get_int_input(1, r.rooms.size());

        r.producingRoomTimetable(choice);
    }

    /**
     * Prints a timetable for a room.
     * @param room Room whose timetable is printed
     * @param time The timetable
     */
    public void timetableRoomsResult(Room room, List<Time> time) {
        if (!(time.size() == 0)) {
            System.out.println("\nTimetable for " + room);
        }
        timetableFormat(time, String.valueOf(room));
        timetableNext();
    }

    /**
     * Produces a timetable printing format.
     * @param time Timetable to be printed
     * @param info Information about the timetable
     */
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
                        (time.get(i).getRoom() == null) ? null : time.get(i).getRoom().getRoomNumber());
            }
            System.out.println("+-----+----------------+-----------+-------------+----------+-------------+");
        }
    }

    /**
     * Asks the user what they want to do after viewing a timetable, redirecting them based on their choice.
     */
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

    /**
     * Asks the user what room detail they want to change.
     */
    public void changeRoomMenu() {
        System.out.println("\nWhich room detail would you like to change?");
        System.out.println("[1] - Room Type");
        System.out.println("[2] - Maximum Room Capacity");
        System.out.println("[3] - Social Distancing Capacity");
        System.out.println("[4] - Go back");

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

    /**
     * Asks the user which room's detail they want to change.
     * @param detail Detail to be changed
     */
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

    /**
     * Asks the user for a new room type.
     * @param room Room whose type is to be changed
     */
    public void changeRoomTypeChoice(Room room) {
        System.out.println("\nPlease enter a new Room Type for " + room.toString());

        String newType = ic.get_string_input();

        r.changeRoomType(room, newType);
    }

    /**
     * Asks the user for a new maximum room capacity.
     * @param room Room whose maximum capacity is to be changed
     */
    public void changeRoomCapacityChoice(Room room) {
        System.out.println("\nPlease enter a new Room Maximum Capacity for " + room.toString());

        int newCapacity = ic.get_int_input(1, 300);

        r.changeRoomCapacity(room, newCapacity);
    }

    /**
     * Asks the user for a new social distancing capacity.
     * @param room Room whose social distancing capacity is to be changed
     */
    public void changeRoomSocDistCapacityChoice(Room room) {
        System.out.println("\nPlease enter a new Room Social Distancing Capacity for " + room.toString());

        int newCapacity = ic.get_int_input(1,50);

        r.changeRoomSocDistCapacity(room, newCapacity);
    }

    /**
     * Asks the user what they want to do after changing room details, redirecting them based on their choice.
     * @param room Room whose details have been changed
     */
    public void changeRoomResult(Room room) {
        System.out.println("\nSuccessfully changed. New room:\n" + room);
        System.out.println("\nWhat would you like to do next?");
        System.out.println("[1] - Change another room detail");
        System.out.println("[2] - Print a list of all rooms and go back to main menu");
        System.out.println("[3] - Go Back");

        int choice = ic.get_int_input(1, 3);

        switch (choice) {
            case 1 -> changeRoomMenu();
            case 2 -> listOfRooms();
            case 3 -> runMenu();
        }
    }

    /**
     * Asks for user inputs of timetable creation details (day, class name, ModuleID option,
     * start and end time), as well as whether they would like to book a room.
     */
    public void timetableVariables() {
        InputCheck ic = new InputCheck();

        System.out.print("\nEnter a Day:\n");
        String day = ic.get_day_input();

        System.out.println("\nEnter Class name:");
        String timetableName = ic.get_string_input();

        String moduleID = moduleOptions();

        System.out.println("\nEnter Module Start Time:");
        String timeStart = ic.get_time_input();
        String timeEnd = ic.get_end_time_input(timeStart);

        System.out.println("\nWould you like to book a room?");
        System.out.println("[1] - Yes");
        System.out.println("[2] - No");

        int choice = ic.get_int_input(1, 2);

        if (choice == 1) {
            timetableRoomChoice(timeStart, timeEnd, day, timetableName, moduleID);
        } else if (choice == 2) {
                Time time = t.createTimetable(day, timetableName, moduleID, timeStart, timeEnd);
                List<Time> temp = new ArrayList<>();
                temp.add(time);
                timetableFormat(temp, "Your current timetable creation");
        }
    }

    /**
     * Asks the user whether they would like to book a room with or without Social Distancing requirements.
     * @param timeStart Timetable entry's starting time
     * @param timeEnd Timetable entry's ending time
     * @param day Timetable entry's day
     */
    public Room timetableRoomChoiceText(String timeStart, String timeEnd, String day) {
        System.out.println("\nWould you like to book a room with or without social distancing conditions?");
        System.out.println("[1] - With social distancing conditions");
        System.out.println("[2] - Without social distancing conditions");

        Room room = null;
        int choice = ic.get_int_input(1, 2);

        switch (choice) {
            case 1 -> room = timetableAvailableSocDistRooms(timeStart, timeEnd, day);
            case 2 -> room = timetableAvailableRooms(timeStart, timeEnd, day);
        }
        return room;
    }

    /**
     * Books a room when creating a timetable.
     * @param timeStart Timetable entry's starting time
     * @param timeEnd Timetable entry's ending time
     * @param day Timetable entry's day
     * @param timetableName Timetable entry's name
     * @param moduleID ModuleID of the timetable entry
     */
    public void timetableRoomChoice(String timeStart, String timeEnd, String day, String timetableName, String moduleID) {
        Room room = timetableRoomChoiceText(timeStart, timeEnd, day);

        if (room == null) {
            room = availableRoomTryAgain(timeStart, timeEnd, day, null);
        }
        Time time = t.createTimetable(day, timetableName, moduleID, timeStart, timeEnd);

        r.bookRooms(room.getRoomNumber(), time);
        List<Time> temp = new ArrayList<>();
        temp.add(time);
        timetableFormat(temp, "Your current timetable creation");
    }

    /**
     * Shows available rooms to be booked based on the timetable entry's day and time
     * and asks the user to choose a room they want to book.
     * @param timeStart Timetable entry's starting time
     * @param timeEnd Timetable entry's ending time
     * @param day Timetable entry's day
     * @return The room to be booked
     */
    public Room timetableAvailableRooms(String timeStart, String timeEnd, String day) {
        r.availableRoomsDT(timeStart, timeEnd, day);
        Room room = null;

        if (r.availableRooms.size() != 0) {
            System.out.println("\nPlease pick a room:");
            System.out.println("\nAvailable Rooms from " + timeStart + " to " + timeEnd + " on " + day + ":\n");
            availableRoomsPrint();

            int choice = ic.get_int_input(1, r.availableRooms.size());
            room = r.availableRooms.get(choice - 1);
        } else {
            System.out.println("\nThere are no available rooms from " + timeStart + " to " + timeEnd + " on " + day + ":\n");
        }
        return room;
    }

    /**
     * Asks the user how many people are going to be in the room that is being booked,
     * shows available rooms based on the timetable entry's day and time and social
     * distancing conditions, and asks the user to choose a room to be booked.
     * @param timeStart Timetable entry's starting time
     * @param timeEnd Timetable entry's ending time
     * @param day Timetable entry's day
     * @return The room to be booked
     */
    public Room timetableAvailableSocDistRooms(String timeStart, String timeEnd, String day) {
        System.out.println("\nHow many people are going to be in the room?");
        int people = ic.get_int_input(1, 500);
        Room room = null;

        r.availableRoomsSocDist(timeStart, timeEnd, day, people);

        if (r.availableRooms.size() != 0) {
            System.out.println("\nPlease pick a room:");
            System.out.println("\nAvailable Rooms from " + timeStart + " to " + timeEnd + " on " + day +
                    " that can fit " + people + " people under social distancing conditions:\n");
            availableRoomsPrint();

            int choice = ic.get_int_input(1, r.availableRooms.size());
            room = r.availableRooms.get(choice - 1);
        } else {
            System.out.println("\nThere are no rooms available from " + timeStart + " to " + timeEnd + " on " + day +
                    " that can fit " + people + " people under social distancing conditions.");
        }
        return room;
    }

    /**
     * Asks the user whether they would like to try choosing a room again if
     * there were no available rooms to be booked and room assigned for room booking was null.
     * @param timeStart Timetable entry's starting time
     * @param timeEnd Timetable entry's ending time
     * @param day Timetable entry's day
     * @param room Null room
     * @return Chosen room once it's not null
     */
    public Room availableRoomTryAgain(String timeStart, String timeEnd, String day, Room room) {
        while (room == null) {
            System.out.println("\nWould you like to try again?");
            System.out.println("[1] - Yes");
            System.out.println("[2] - No");

            int choice = ic.get_int_input(1, 2);

            if (choice == 1) {
                room = timetableRoomChoiceText(timeStart, timeEnd, day);
            } else if (choice == 2) {
                runMenu();
            }
        }
        return room;
    }

    /**
     * Books a room for an existing timetable entry with a null room.
     */
    public void bookRoom() {
        List<Time> emptyTimes = t.timetableNoRoom();

        if (emptyTimes.size() == 0) {
            System.out.println("There are no timetable entries without booked rooms.");
            runMenu();
        } else {
            System.out.println("\nWhich timetable entry would you like to book a room for?");
            timetableFormat(emptyTimes, "empty timetables");
            int choice = ic.get_int_input(1, emptyTimes.size());
            Time time = emptyTimes.get(choice - 1);

            timetableBookRoomChoice(time.getTimeStart(), time.getTimeEnd(), time.getDay(), time);
        }
    }

    /**
     * Books a room for an existing timetable entry.
     * @param timeStart Timetable entry's starting time
     * @param timeEnd Timetable entry's ending time
     * @param day Timetable entry's day
     * @param time The timetable entry
     */
    public void timetableBookRoomChoice(String timeStart, String timeEnd, String day, Time time) {
        Room room = timetableRoomChoiceText(timeStart, timeEnd, day);

        if (room == null) {
            room = availableRoomTryAgain(timeStart, timeEnd, day, null);
        }

        List<Time> temp = new ArrayList<>();

        r.bookRooms(room.getRoomNumber(), time);
        temp.add(time);
        timetableFormat(temp, "Your room booking");

        roomBookingNext();
    }
}
