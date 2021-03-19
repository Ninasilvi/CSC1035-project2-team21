package csc1035.project2;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.io.*;
import java.util.*;

public class RoomBooking implements RoomBookingInterface{

    List<Room> rooms;
    List<Room> bookedRooms = new ArrayList<>();
    List<Room> availableRooms = new ArrayList<>();
    static InputCheck ic = new InputCheck();
    File roomFile = new File("src\\main\\resources\\bookedRooms.cvs");
    static UI UI = new UI();

    public void listOfRooms() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();

        rooms = se.createQuery("FROM Rooms").list();

        if (availableRooms.size() == 0) {
            availableRooms = rooms;
        }

        se.getTransaction().commit();
        se.close();
    }

    public void bookRooms() {
        UI.availableRoomsList();
        UI.bookRoomsText();
        bookedRoomsCheck();

        int choice = ic.get_int_input(1, availableRooms.size());
        Room room = availableRooms.get(choice - 1);

        bookedRooms.add(room);
        availableRooms.remove(room);

        UI.roomBookingConfirmation(room);
    }

    // Distinguishes the room to cancel, removes it from bookedRooms and adds it to availableRooms
    public void cancelRooms() {
        int choice = ic.get_int_input(1, bookedRooms.size());
        Room room = bookedRooms.get(choice - 1);

        bookedRooms.remove(room);
        availableRooms.add(room);

        UI.roomCancelConfirmation(room);
    }

    public void bookedRoomsCheck() {
        // TODO: add a method that checks booked rooms based on timetable
    }

    // Determines available rooms
    public void availableRooms() {
        listOfRooms();

        bookedRoomsCheck();

        availableRooms.removeIf(room -> room.isIn(bookedRooms));
    }

    // Creates a timetable list for the chosen room
    public void producingRoomTimetable(int choice) {
        Session se = HibernateUtil.getSessionFactory().openSession();
        Room room = rooms.get(choice - 1);
        double roomNumber = room.getRoomNumber();

        se.beginTransaction();
        String hql = "FROM Time WHERE roomNumber LIKE '" + roomNumber + "'";
        List<Time> timetables = se.createQuery(hql).list();
        se.close();
        UI.timetableRoomsResult(room, timetables);
    }

    public void changeRoomType(Room room, String newType) {
        Session se = HibernateUtil.getSessionFactory().openSession();
        double roomNumber = room.getRoomNumber();

        room.setType(newType);
        Transaction t = se.beginTransaction();
        String hql = "UPDATE Rooms SET type = '" + newType +
                "' WHERE roomNumber LIKE '" + roomNumber + "'";
        Query query = se.createQuery(hql);
        query.executeUpdate();
        t.commit();
        se.close();
        UI.changeRoomResult(room);
    }

    public void changeRoomCapacity(Room room, int newCapacity) {
        Session se = HibernateUtil.getSessionFactory().openSession();
        double roomNumber = room.getRoomNumber();

        room.setMaxCapacity(newCapacity);
        Transaction t = se.beginTransaction();
        String hql = "UPDATE Rooms SET maxCapacity = " + newCapacity +
                " WHERE roomNumber LIKE '" + roomNumber + "'";
        Query query = se.createQuery(hql);
        query.executeUpdate();
        t.commit();
        se.close();
        UI.changeRoomResult(room);
    }

    public void changeRoomSocDistCapacity(Room room, int newCapacity) {
        Session se = HibernateUtil.getSessionFactory().openSession();
        double roomNumber = room.getRoomNumber();

        room.setSocialDistCapacity(newCapacity);
        Transaction t = se.beginTransaction();
        String hql = "UPDATE Rooms SET socialDistCapacity = " + newCapacity +
                " WHERE roomNumber LIKE '" + roomNumber + "'";
        Query query = se.createQuery(hql);
        query.executeUpdate();
        t.commit();
        se.close();
        UI.changeRoomResult(room);
    }
}
