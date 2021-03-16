package csc1035.project2;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Timetable {
    /***
     * Creates a List of Students that take a specific Module (determined by moduleID)
     * @param moduleID Which Module Students should be printed
     * @param se Session created in UI.listOfStudentsChoice
     */
    public void listOfStudents(String moduleID, Session se) {
        se.beginTransaction();

        String hql = "FROM Student S " +
                "WHERE S.studentID IN (SELECT SM.studentID FROM StudentModule SM WHERE SM.moduleID = " + moduleID + ")";
        List<Student> students = se.createQuery(hql).list();
        se.getTransaction().commit();
        se.close();
        UI.listOfStudentsResult(students);
    }

    /**
     * List of Staff that teach a specific Module
     */
    public void listOfStaff(String moduleID, Session se) {
        se.beginTransaction();

        String hql = "FROM Staff S " +
                "WHERE S.staffID IN (SELECT SM.staffID FROM StaffModule SM WHERE SM.moduleID = " + moduleID + ")";
        List<Staff> staff = se.createQuery(hql).list();
        se.getTransaction().commit();
        se.close();
        UI.listOfStaffResult(staff);
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
