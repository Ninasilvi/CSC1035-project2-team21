package csc1035.project2;

import java.util.*;

public interface RoomBookingInterface {

    List<Room> room = null;
    List<Room> bookedRooms = new ArrayList<>();
    List<Room> availableRooms = new ArrayList<>();

    void listOfRooms();

    void bookRooms();

    void writeToBookedRooms(Room room);

    void bookedRoomsFile();

    void cancelRooms();

    void cancelRoomsFile(Room room);

    void availableRooms();

    void producingRoomTimetable(int choice);
}
