package csc1035.project2;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class RoomBooking {

    List<Rooms> rooms;
    List<Rooms> bookedRooms = new ArrayList<>();
    static InputCheck ic = new InputCheck();

    public void listOfRooms() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();

        rooms = se.createQuery("FROM Rooms").list();

        se.getTransaction().commit();

        for (int i = 0; i < rooms.size(); i++) {
            System.out.println(i + 1 + " - " + rooms.get(i));
        }
        se.close();
    }

    // Does not save to memory yet
    public void bookRooms() {
        listOfRooms();
        UI.bookRoomsUI();

        int choice = ic.get_int_input(1, rooms.size());
        Rooms room = rooms.get(choice);
        boolean contains = false;

        for (Rooms r : bookedRooms) {
            if (room.compare(r)) {
                contains = true;
                break;
            }
        }

        if (contains) {
            UI.roomAlreadyBooked();
        } else {
            bookedRooms.add(room);
            UI.roomBookingConfirmation(room);
        }
    }
}
