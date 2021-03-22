package csc1035.project2;

import csc1035.project2.interfaces.RoomBookingInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.*;

public class RoomBooking implements RoomBookingInterface {

    List<Room> rooms;
    List<Room> availableRooms = new ArrayList<>();
    static UI UI = new UI();
    static Timetable t = new Timetable();

    /**
     * Creates a list of all rooms.
     */
    public void listOfRooms() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();

        rooms = se.createQuery("FROM Rooms").list();

        se.getTransaction().commit();
        se.close();
    }

    /**
     * Books a room and saves it to the database.
     * @param roomNumber Room number of the room to be booked
     * @param time Timetable entry for which the room will be booked
     */
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

    /**
     * Cancels a room booking for a timetable entry and updates the database.
     * @param id The id of the timetable entry
     */
    public void cancelRooms(int id) {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();

        Time time = se.get(Time.class, id);
        Room room = time.getRoom();

        room.getTimes().remove(time);
        time.setRoom(null);
        se.update(time);
        se.update(room);
        se.getTransaction().commit();
        se.close();

        List<Time> temp = new ArrayList<>();
        temp.add(time);

        UI.roomCancelConfirmation(room, temp);
    }

    /**
     * Creates a list of timetable entries with booked rooms.
     * @return The list of timetable entries
     */
    public List<Time> bookedRoomsCheck() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();

        String hql = "FROM Time WHERE room != null";
        List<Time> times = se.createQuery(hql).list();
        se.close();

        return times;
    }

    /**
     * Determines rooms that have not been booked.
     */
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

    /**
     * Determines available rooms for a specific time period (day and time).
     * @param timeStart Starting time of the specific time period
     * @param timeEnd Ending time of the specific time period
     * @param day The day of the specific time period
     */
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

    /**
     * Shows available rooms for a specific period of time that can maintain social distancing
     * conditions with the amount of people who are going to be in the room.
     * @param timeStart Starting time of the specific time period
     * @param timeEnd Ending time of the specific time period
     * @param day The day of the specific time period
     * @param people Amount of people who are going to be in the room
     */
    public void availableRoomsSocDist(String timeStart, String timeEnd, String day, int people) {
        availableRoomsDT(timeStart, timeEnd, day);

        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();

        String hql = "FROM Rooms r WHERE socialDistCapacity >= " + people;
        List<Room> roomsSocDist = se.createQuery(hql).list();

        availableRooms.removeIf(room -> !(room.isIn(roomsSocDist)));
        se.close();
    }

    /**
     * Creates a timetable for a specific room.
     * @param choice User's room choice
     */
    public void producingRoomTimetable(int choice) {
        Session se = HibernateUtil.getSessionFactory().openSession();
        Room room = rooms.get(choice - 1);
        double roomNumber = room.getRoomNumber();

        se.beginTransaction();
        String hql = "FROM Time WHERE room LIKE '" + roomNumber + "'";
        List<Time> timetables = se.createQuery(hql).list();
        se.close();
        UI.timetableRoomsResult(room, timetables);
    }

    /**
     * Changes the type of a room.
     * @param room Room whose type will be changed
     * @param newType New type for the room
     */
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

    /**
     * Changes the maximum capacity of a room.
     * @param room Room whose maximum capacity will be changed
     * @param newCapacity New maximum capacity for the room
     */
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

    /**
     * Changes the social distancing capacity of a room.
     * @param room Room whose social distancing capacity will be changed
     * @param newCapacity New social distancing capacity for the room
     */
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
