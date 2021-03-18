package csc1035.project2;
import org.hibernate.Session;

import java.util.*;

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
        se.close();
        UI.listOfModuleReqResult(modulesReq);
    }

    // Allows the admin to create a timetable (and book relevant rooms) for the school
    public void allowCreateTimetable() {

    }

    // A way of producing a timetable for a staff member or student
    public void producingTimetable() {
        Session se = HibernateUtil.getSessionFactory().openSession();

        InputCheck ic = new InputCheck();
        System.out.println("Select one of the options:");
        System.out.println("1 - Timetable for Students");
        System.out.println("2 - Timetable for Staff Members");
        System.out.println("3 - Go back");
        int choice = ic.get_int_input(1,3);

        switch(choice) {
            case 1:
                // Get Students Timetable
                se.beginTransaction();
                List<Student> students = se.createQuery("FROM Student").list();
                for(int i = 0; i < students.size(); i++) {
                    System.out.println(i+1 + " - " + students.get(i).getStudentID() + " " + students.get(i).getFirstName() + " " + students.get(i).getLastName());
                }
                choice = ic.get_int_input(1, students.size());

                List<Module> module = new ArrayList<>(students.get(choice-1).getModules());
                List<Time> time = new ArrayList<Time>();

                for(int i = 0; i < module.size(); i++) {
                    String hql = "FROM Time t WHERE t.moduleID = '" + module.get(i).getModuleID() + "'";
                    List<Time> temp = se.createQuery(hql).list();
                    time.addAll(temp);
                }

                System.out.println('\n' + "Timetable for " + students.get(choice-1).getFirstName() + " " + students.get(choice-1).getLastName() + " (ID: " + students.get(choice-1).getStudentID() + ")");
                String printTimeFormat = "| %-3s | %-14s | %-8s | %-11s | %-8s | %-11s |%n";
                System.out.println("+-----+----------------+----------+-------------+----------+-------------+");
                System.out.println("| Row | Timetable Name | Day      | Time        | ModuleID | Room Number |");
                System.out.println("+-----+----------------+----------+-------------+----------+-------------+");

                for(int i = 0; i < time.size(); i++) {
                    System.out.format(printTimeFormat, i+1, time.get(i).getTimetableName(), time.get(i).getDay(), time.get(i).getTimeStart() + "-" + time.get(i).getTimeEnd(), time.get(i).getModuleID(), time.get(i).getRoomNumber());
                }
                System.out.println("+-----+----------------+----------+-------------+----------+-------------+");

            case 2:
                // Get Staff Timetable

            case 3:
                // Go Back
                return;
        }
        //Session se = HibernateUtil.getSessionFactory().openSession();
        //se.beginTransaction();

        se.close();
    }


}
