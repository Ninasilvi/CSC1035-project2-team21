package csc1035.project2;

import org.hibernate.Session;

import javax.persistence.Query;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Timetable {
    /***
     * Creates a List of Students that take a specific Module (determined by moduleID)
     * @param moduleID Which Module Students should be printed
     * @param se Session created in UI.listOfStudentsChoice
     */
    public void listOfStudents(String moduleID, Session se) {
        se.beginTransaction();
        List<Student> result = new ArrayList<Student>();

        String hql = "SELECT moduleName FROM Module WHERE moduleID = '" + moduleID + "'";
        List<Module> ModuleName = se.createQuery(hql).list();

        List<Student> students = se.createQuery("FROM Student").list();

        System.out.println('\n' + "Students taking '" + ModuleName.get(0) + "' (" + moduleID + ") Module");
        for(int i = 0; i < students.size(); i++) {
            List<Module> temp = new ArrayList<>(students.get(i).getModules());

            for(int j = 0; j < temp.size(); j++)
                if(temp.get(j).getModuleID() == moduleID)
                {
                    result.add(students.get(i));
                }
        }

        se.getTransaction().commit();
        se.close();
        UI.listOfStudentsResult(result);
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
    public void producingStudentTimetable(int choice, Session se, List<Student> students) {
        List<Module> modules = new ArrayList<>(students.get(choice-1).getModules());
        List<Time> time = new ArrayList<>();

        for (Module module : modules) {
            String hql = "FROM Time t WHERE t.moduleID = '" + module.getModuleID() + "'";
            List<Time> temp = se.createQuery(hql).list();
            time.addAll(temp);
        }

        se.close();
        UI.timetableStudentsResult(students, choice, time);
    }

    public void producingStaffTimetable(int choice, Session se, List<Staff> staff) {
        List<Module> modules = new ArrayList<>(staff.get(choice-1).getModules());
        List<Time> time = new ArrayList<>();

        for (Module module : modules) {
            String hql = "FROM Time t WHERE t.moduleID = '" + module.getModuleID() + "'";
            List<Time> temp = se.createQuery(hql).list();
            time.addAll(temp);
        }

        se.close();
        UI.timetableStaffResult(staff, choice, time);
    }
}
