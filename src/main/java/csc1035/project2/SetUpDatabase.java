package csc1035.project2;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SetUpDatabase {

    public static void main(String[] args) throws ParseException {
        SetUpRooms();
        SetUpModules();
        SetUpStudents();
        SetUpStaff();
        SetUpModReq();
    }

    public static void SetUpRooms(){
        Session se = null;

        List<Room> rooms = new ArrayList<>();
        InputStream stream = SetUpDatabase.class.getClassLoader().getResourceAsStream("rooms.csv");
        Scanner sc = new Scanner(stream);

        sc.nextLine();
        while (sc.hasNextLine()) {

            String[] line = sc.nextLine().split(",");
            rooms.add(new Room(Float.parseFloat(line[0]), line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3])));
        }

        try {
            se = HibernateUtil.getSessionFactory().openSession();
            se.beginTransaction();

            for (Room r: rooms) {
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

    public static void SetUpModules(){
        Session se = null;

        List<Module> modules = new ArrayList<>();
        InputStream stream = SetUpDatabase.class.getClassLoader().getResourceAsStream("modules.csv");
        Scanner sc = new Scanner(stream);

        sc.nextLine();
        while (sc.hasNextLine()) {

            String[] line = sc.nextLine().split(",");
            modules.add(new Module(line[0], line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3])));
        }

        try {
            se = HibernateUtil.getSessionFactory().openSession();
            se.beginTransaction();

            for (Module m: modules) {
                se.persist(m);
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

    public static void SetUpStudents(){
        Session se = null;

        List<Student> students = new ArrayList<>();
        InputStream stream = SetUpDatabase.class.getClassLoader().getResourceAsStream("students.csv");
        Scanner sc = new Scanner(stream);

        sc.nextLine();
        while (sc.hasNextLine()) {

            String[] line = sc.nextLine().split(",");
            students.add(new Student(Integer.parseInt(line[0]), line[1], line[2]));
        }

        try {
            se = HibernateUtil.getSessionFactory().openSession();
            se.beginTransaction();

            for (Student s: students) {
                se.persist(s);
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

    public static void SetUpStaff(){
        Session se = null;

        List<Staff> staff = new ArrayList<>();
        InputStream stream = SetUpDatabase.class.getClassLoader().getResourceAsStream("staff.csv");
        Scanner sc = new Scanner(stream);

        sc.nextLine();
        while (sc.hasNextLine()) {

            String[] line = sc.nextLine().split(",");
            staff.add(new Staff(line[0], line[1], line[2]));
        }

        try {
            se = HibernateUtil.getSessionFactory().openSession();
            se.beginTransaction();

            for (Staff s: staff) {
                se.persist(s);
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

    public static void SetUpModReq() throws ParseException {
        Session se = null;

        List<ModuleRequirements> moduleRequirements = new ArrayList<>();
        InputStream stream = SetUpDatabase.class.getClassLoader().getResourceAsStream("module_requirements.csv");
        Scanner sc = new Scanner(stream);

        sc.nextLine();
        while (sc.hasNextLine()) {

            String[] line = sc.nextLine().split(",");

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");

            String dateInString = line[1];

            Date date = format.parse(dateInString);

            moduleRequirements.add(new ModuleRequirements(line[0], date, Integer.parseInt(line[2]),
                    Integer.parseInt(line[3]), Integer.parseInt(line[4]), Integer.parseInt(line[5])));
        }

        try {
            se = HibernateUtil.getSessionFactory().openSession();
            se.beginTransaction();

            for (ModuleRequirements m: moduleRequirements) {
                se.persist(m);
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
