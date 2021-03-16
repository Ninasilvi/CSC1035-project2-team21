package csc1035.project2;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Timetable {
    /***
     * List of Students that take a specific Module (moduleID)
     * @param moduleID Which Module Students should be printed
     * @param se Session variable
     */
    public void listOfStudents(String moduleID, Session se) {
        se.beginTransaction();

        String hql = "FROM Student S " +
                "WHERE S.studentID IN (SELECT SM.studentID FROM StudentModule SM WHERE SM.moduleID = " + moduleID + ")";
        List<Student> students = se.createQuery(hql).list();
        se.getTransaction().commit();

        // Print results#
        for(int i = 0; i < students.size(); i++) {
            System.out.println(i+1 + " - ID: " + students.get(i).getStudentID() + " | First Name: " +
                    students.get(i).getFirstName() + " | Last Name: " + students.get(i).getLastName());
        }
        if(students.size() == 0) {
            System.out.println("No Students were found in this Module");
        }
        System.out.println("\n");
        se.close();
    }

    /**
     * List of Staff that teach a specific Module
     */
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
            System.out.println(i+1 + " - ID: " + staff.get(i).getStaffID() + " | First Name: " +
                    staff.get(i).getFirstName() + " | Last Name: " + staff.get(i).getLastName());
        }
        if(staff.size() == 0) {
            System.out.println("No Staff were found in this Module");
        }
        System.out.println("\n");
        se.close();
    }

    /**
     * List of Module Requirements
     */
    public void listOfModuleReq() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();

        List<ModuleRequirements> modulesReq = se.createQuery("FROM ModuleRequirements").list();

        se.getTransaction().commit();

        for (ModuleRequirements item : modulesReq) {
            System.out.println(item.toString());
        }
        se.close();
    }

    // Allows the admin to create a timetable (and book relevant rooms) for the school
    public void allowCreateTimetable() {

    }

    // A way of producing a timetable for a staff member or student
    public void producingTimetable() {

    }

}
