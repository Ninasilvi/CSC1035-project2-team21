package csc1035.project2.interfaces;

import csc1035.project2.*;

import java.util.List;

public interface UInterface {

    void runMenu();

    void printMenu();

    void listMenu();

    void listOfStudentsChoice();

    void listOfStudentsResult(List<Student> students);

    void listOfStaffChoice();

    void listOfStaffResult(List<Staff> staff);

    void listOfModuleReqResult(List<ModuleRequirements> moduleRequirements);

    String moduleOptions();

    void listOfRooms();

    void roomBookingConfirmation(Room room);

    void roomBookingNext();

    void bookedRoomsList();

    void roomCancel();

    void roomCancelConfirmation(Room room, List<Time> times);

    void roomCancelNext();

    void availableRoomsListMenu();

    void availableRoomsList();

    void availableRoomsDTList();

    void availableRoomsPrint();

    void timetableChoice();

    void timetableStudentsChoice();

    void timetableStudentsResult(Student student, List<Time> time);

    void timetableStaffChoice();

    void timetableStaffResult(Staff staff, List<Time> time);

    void timetableRoomsChoice();

    void timetableRoomsResult(Room room, List<Time> time);

    void timetableFormat(List<Time> time, String info);

    void timetableNext();

    void changeRoomMenu();

    void changeRoomChoice(String detail);

    void changeRoomTypeChoice(Room room);

    void changeRoomCapacityChoice(Room room);

    void changeRoomSocDistCapacityChoice(Room room);

    void changeRoomResult(Room room);

    void timetableVariables();

    Room timetableRoomChoiceText(String timeStart, String timeEnd, String day);

    void timetableRoomChoice(String timeStart, String timeEnd, String day, String timetableName, String moduleID);

    Room timetableAvailableRooms(String timeStart, String timeEnd, String day);

    Room timetableAvailableSocDistRooms(String timeStart, String timeEnd, String day);

    void availableRoomTryAgain();

    void bookRoom();

    void timetableBookRoomChoice(String timeStart, String timeEnd, String day, Time time);
}
