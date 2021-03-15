package csc1035.project2;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class SetUpDatabase {

    public static void main(String[] args) {
        SetUpRooms();
    }

    public static void SetUpRooms(){
        Session se = null;

        List<Rooms> rooms = new ArrayList<>();
        InputStream stream = SetUpDatabase.class.getClassLoader().getResourceAsStream("rooms.csv");
        Scanner sc = new Scanner(stream);

        sc.nextLine();
        while (sc.hasNextLine()) {

            String[] line = sc.nextLine().split(",");
            rooms.add(new Rooms(line[0], line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3])));
        }

        try {
            se = HibernateUtil.getSessionFactory().openSession();
            se.beginTransaction();

            for (Rooms r: rooms) {
                se.persist(r);
            }

            se.getTransaction().commit();

        } catch (HibernateException e) {
            if (se != null) se.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            assert se != null;
            se.close();
        }
    }
}
