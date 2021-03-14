package csc1035.project2;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Timetable {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        Timetable t = new Timetable();

        while(true) {
            t.printMenu();
            int choice = s.nextInt();

            switch (choice) {
                case 1:
                    t.listOfStudents();
                    break;
                case 2:
                    t.listOfStaff();
                    break;
                case 3:
                    t.listOfModuleReq();
                    break;
                case 4:
                    t.allowCreateTimetable();
                    break;
                case 5:
                    t.producingTimetable();
                    break;
                case 6:
                    return;
            }
        }
    }

    // It has a list of Students that take a Module
    public void listOfStudents() {
        Scanner s = new Scanner(System.in);
        Session se = HibernateUtil.getSessionFactory().openSession();

        se.beginTransaction();

        List<Module> modules = se.createQuery("FROM Module").list();

        se.getTransaction().commit();

        // Print results
        System.out.println("Select one Module:");
        int i = 1;
        for (Module item : modules) {
            System.out.println(i + " - " + item);
            i += 1;
        }

        int choice = s.nextInt();

        String ModuleID = "'" + modules.get(choice-1).getModuleID() + "'";

        se.beginTransaction();

        String hql = "FROM Student S " +
                "WHERE S.studentID IN (SELECT SM.studentID FROM StudentModule SM WHERE SM.moduleID = " + ModuleID + ")";
        List<Student> students = se.createQuery(hql).list();
        se.getTransaction().commit();

        // Print results
        for(i = 0; i < students.size(); i++) {
            System.out.println(i+1 + " - ID: " + students.get(i).getStudentID() + " | First Name: " + students.get(i).getFirstName() + " | Last Name: " + students.get(i).getLastName());
        }
        System.out.println("");
        se.close();
    }

    // It has a list of staff that teach a module
    public void listOfStaff() {
        Scanner s = new Scanner(System.in);
        Session se = HibernateUtil.getSessionFactory().openSession();

        se.beginTransaction();

        List<Module> modules = se.createQuery("FROM Module").list();

        se.getTransaction().commit();

        // Print results
        System.out.println("Select one Module:");
        int i = 1;
        for (Module item : modules) {
            System.out.println(i + " - " + item);
            i += 1;
        }

        int choice = s.nextInt();

        String ModuleID = "'" + modules.get(choice-1).getModuleID() + "'";

        se.beginTransaction();

        String hql = "FROM Staff S " +
                "WHERE S.staffID IN (SELECT SM.staffID FROM StaffModule SM WHERE SM.moduleID = " + ModuleID + ")";
        List<Staff> staff = se.createQuery(hql).list();
        se.getTransaction().commit();

        // Print results
        for(i = 0; i < staff.size(); i++) {
            System.out.println(i+1 + " - ID: " + staff.get(i).getStaffID() + " | First Name: " + staff.get(i).getFirstName() + " | Last Name: " + staff.get(i).getLastName());
        }
        System.out.println("");
        se.close();
    }

    // It has a list of module requirements
    public void listOfModuleReq() {

    }

    // Allows the admin to create a timetable (and book relevant rooms) for the school
    public void allowCreateTimetable() {

    }

    // A way of producing a timetable for a staff member or student
    public void producingTimetable() {

    }

    // Print Menu
    public void printMenu() {
        System.out.println("\n Enter an Option [1-6]");
        System.out.println("1 - List Of Students");
        System.out.println("2 - List of Staff");
        System.out.println("3 - List of Module Requirements");
        System.out.println("4 - Allow Create Timetable");
        System.out.println("5 - Producing Timetable");
        System.out.println("6 - Exit \n");
    }
}
