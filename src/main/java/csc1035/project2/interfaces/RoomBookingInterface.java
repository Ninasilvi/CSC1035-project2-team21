package csc1035.project2.interfaces;

import csc1035.project2.Room;
import csc1035.project2.Time;

import java.util.*;

public interface RoomBookingInterface {

    List<Room> room = null;
    List<Room> bookedRooms = new ArrayList<>();
    List<Room> availableRooms = new ArrayList<>();

    void listOfRooms();

    void bookRooms(Room room, Time time);

    void cancelRooms();

    void bookedRoomsCheck();

    void availableRooms();

    void availableRoomsDT(String timeStart, String timeEnd, String day);

    void availableRoomsSocDist(String timeStart, String timeEnd, String day, int people);

    void producingRoomTimetable(int choice);

    void changeRoomType(Room room, String newType);

    void changeRoomCapacity(Room room, int newCapacity);

    void changeRoomSocDistCapacity(Room room, int newCapacity);
}
