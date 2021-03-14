package csc1035.project2;

import org.hibernate.Session;

import java.util.List;

public class RoomBooking {
    public void listOfRooms() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();

        List<Rooms> rooms = se.createQuery("FROM Rooms").list();

        se.getTransaction().commit();

        for (Rooms item : rooms) {
            System.out.println(item.toString());
        }
    }
}
