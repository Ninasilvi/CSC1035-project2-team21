package csc1035.project2;

import csc1035.project2.interfaces.RoomBookingInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.io.*;
import java.util.*;

public class RoomBooking implements RoomBookingInterface {

    List<Room> rooms;
    List<Room> availableRooms = new ArrayList<>();
    static InputCheck ic = new InputCheck();
    static UI UI = new UI();
    static Timetable t = new Timetable();

    public void listOfRooms() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();

        rooms = se.createQuery("FROM Rooms").list();

        se.getTransaction().commit();
        se.close();
    }

    public void bookRooms(double roomNumber, Time time) {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();

        Room room = se.get(Room.class, roomNumber);

        time.setRoom(room);

        if (room.getTimes() == null) {
            List<Time> temp = new ArrayList<>();
            temp.add(time);
            room.setTimes(temp);
        } else {
            room.getTimes().add(time);
        }
        se.update(time);
        se.update(room);
        se.getTransaction().commit();
        se.close();
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

    // Determines rooms that have not been booked
    public void availableRooms() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();

        if (availableRooms.size() != 0) {
            List<Room> temp = availableRooms;
            availableRooms.removeAll(temp);
        }

        String hql = "SELECT r FROM Rooms r WHERE r NOT IN (SELECT t.room FROM Time t)";
        List<Room> temp = se.createQuery(hql).list();

        availableRooms.addAll(temp);
        se.close();
    }

    // Determines available rooms for a specific date and time
    public void availableRoomsDT(String timeStart, String timeEnd, String day) {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();
        availableRooms();

        String hql = "FROM Time";
        List<Time> times = se.createQuery(hql).list();
        List<Room> unavailableRooms = new ArrayList<>();

        for (Time time : times) {
            String startTime = time.getTimeStart();
            String endTime = time.getTimeEnd();
            String timeDay = time.getDay();
            if (time.getRoom() == null) {
                break;
            }
            List<Room> temp = se.createQuery("FROM Rooms WHERE roomNumber LIKE '" + time.getRoom().getRoomNumber() + "'").list();

            if (!(t.timeOverlap(timeStart, timeEnd, startTime, endTime, day, timeDay))) {
                if (!(temp.get(0).isIn(availableRooms))) {
                    availableRooms.addAll(temp);
                }
            } else {
                unavailableRooms.addAll(temp);
            }
        }
        for (Room room : unavailableRooms) {
            if (room.isIn(availableRooms)) {
                availableRooms.remove(room);
            }
        }
        se.close();
    }

    public void availableRoomsSocDist(String timeStart, String timeEnd, String day, int people) {
        availableRoomsDT(timeStart, timeEnd, day);

        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();

        String hql = "FROM Rooms r WHERE socialDistCapacity >= " + people;
        List<Room> roomsSocDist = se.createQuery(hql).list();

        availableRooms.removeIf(room -> !(room.isIn(roomsSocDist)));
        se.close();
    }

    // Creates a timetable list for the chosen room
    public void producingRoomTimetable(int choice) {
        Session se = HibernateUtil.getSessionFactory().openSession();
        Room room = rooms.get(choice - 1);
        double roomNumber = room.getRoomNumber();

        se.beginTransaction();
        String hql = "FROM Time WHERE room LIKE '" + room.getRoomNumber() + "'";
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
