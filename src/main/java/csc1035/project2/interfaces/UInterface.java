package csc1035.project2.interfaces;

import csc1035.project2.*;
import csc1035.project2.Module;
import org.hibernate.Session;

import java.util.List;

public interface UInterface {

    void runMenu();

    void printMenu();

    void listOfStudentsChoice();

    void listOfStudentsResult(List<Student> students);

    void listOfStaffChoice();

    void listOfStaffResult(List<Staff> staff);

    void listOfModuleReqResult(List<ModuleRequirements> moduleRequirements);

    List<Time> producingTimetableForModule(List<Module> modules, Session se);

    String moduleOptions();

    void listOfRooms();

    void roomBookingConfirmation(Room room);

    void bookedRoomsList();

    void roomCancel();

    void roomCancelConfirmation(Room room);

    void roomCancelNext();

    void availableRoomsListMenu();

    void availableRoomsList();

    void availableRoomsDTList();

    void availableRoomsPrint();

    void timetableChoice();

    void timetableStudentsChoice();

    void timetableStudentsResult(List<Student> students, int choice, List<Time> time);

    void timetableStaffChoice();

    void timetableStaffResult(List<Staff> staff, int choice, List<Time> time);

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
