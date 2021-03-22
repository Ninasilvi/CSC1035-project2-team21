package csc1035.project2;

import csc1035.project2.interfaces.TimetableInterface;
import org.hibernate.Session;

import java.util.*;

public class Timetable implements TimetableInterface {

    static UI UI = new UI();

    /***
     * Creates a list of students that take a specific module.
     * @param moduleID The moduleID of the module the students are taking
     * @param se Session created in UI.listOfStudentsChoice
     */
    public void listOfStudents(String moduleID, Session se) {
        se.beginTransaction();

        List<Student> result = new ArrayList<>();

        String hql = "SELECT moduleName FROM Module WHERE moduleID = '" + moduleID + "'";
        List<Module> moduleName = se.createQuery(hql).list();
        List<Student> students = se.createQuery("FROM Student").list();

        //Creates a list of Students taking a Module
        for (Student student : students) {
            List<Module> temp = new ArrayList<>(student.getModules());
            for (Module module : temp)
                if (module.getModuleID().equals(moduleID)) {
                    result.add(student);
                }
        }
        if (result.size() != 0) {
            System.out.println("\nStudents taking '" + moduleName.get(0) + "' (" + moduleID + ") Module:\n");

        }
        se.getTransaction().commit();
        se.close();
        UI.listOfStudentsResult(result);
    }

    /**
     * Creates a list of staff members that teach a specific module.
     * @param moduleID The moduleID of the module the staff members are teaching
     * @param se Session created in UI.listOfStaffChoice
     */
    public void listOfStaff(String moduleID, Session se) {
        se.beginTransaction();

        List<Staff> result = new ArrayList<>();

        String hql = "SELECT moduleName FROM Module WHERE moduleID = '" + moduleID + "'";
        List<Module> moduleName = se.createQuery(hql).list();
        List<Staff> staff = se.createQuery("FROM Staff").list();

        //Creates a list of Staff Members taking a Module
        for (Staff staffMember : staff) {
            List<Module> temp = new ArrayList<>(staffMember.getModules());
            for (Module module :temp)
                if (module.getModuleID().equals(moduleID)) {
                    result.add(staffMember);
                }
        }
        if (result.size() != 0) {
            System.out.println("\nStaff members teaching '" + moduleName.get(0) + "' (" + moduleID + ") Module:\n");
        }
        se.getTransaction().commit();
        se.close();
        UI.listOfStaffResult(result);
    }

    /**
     * Creates a list of all module requirements.
     */
    public void listOfModuleReq() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();
        List<ModuleRequirements> modulesReq = se.createQuery("FROM ModuleRequirements").list();
        se.getTransaction().commit();
        se.close();
        UI.listOfModuleReqResult(modulesReq);
    }

    /**
     * Creates a timetable entry and saves it to the database.
     * @param day Day for the timetable entry
     * @param timetableName Class name for the timetable entry
     * @param moduleID Module ID for the timetable entry
     * @param timeStart Class starting time for the timetable entry
     * @param timeEnd Class ending time for the timetable entry
     * @return The timetable entry
     */
    public Time createTimetable(String day, String timetableName, String moduleID, String timeStart, String timeEnd) {
        Time time = new Time();
        Session se = HibernateUtil.getSessionFactory().openSession();

        se.beginTransaction();
        time.setDay(day);
        time.setTimetableName(timetableName);
        time.setTimeStart(timeStart);
        time.setTimeEnd(timeEnd);
        time.setModuleID(moduleID);

        String hql = "FROM Time";
        List<Time> times = se.createQuery(hql).list();
        if (time.getId() > times.size()) {
            time.setId(times.size() + 1);
        }
        se.save(time);
        se.getTransaction().commit();
        se.close();

        return time;
    }

    /**
     * Creates a timetable for a student.
     * @param student The student
     * @param se Session passed from the calling method
     */
    public void producingStudentTimetable(Student student, Session se) {
        List<Module> modules = new ArrayList<>(student.getModules());

        // Creating a Timetable for a Module and Sorting it by Day and Time
        List<Time> time = producingTimetableForModule(modules, se);

        se.close();
        UI.timetableStudentsResult(student, time);
    }

    /**
     * Creates a timetable for a staff member.
     * @param staff The staff member
     * @param se Session passed from the calling method
     */
    public void producingStaffTimetable(Staff staff, Session se) {
        List<Module> modules = new ArrayList<>(staff.getModules());

        // Creating a Timetable for a Module and Sorting it by Day and Time
        List<Time> time = producingTimetableForModule(modules, se);

        se.close();
        UI.timetableStaffResult(staff, time);
    }

    /**
     * Produces a timetable for a specific module and sorts it by day and time.
     * @param modules A list of modules to get ModuleID for all classes
     * @param se Session passed from the calling method
     * @return Timetable with sorted time
     */
    public List<Time> producingTimetableForModule(List<Module> modules, Session se) {
        List<Time> time = new ArrayList<>();

        //Creates a Timetable for a specific ModuleID
        for (Module module : modules) {
            String hql = "FROM Time t WHERE t.moduleID = '" + module.getModuleID() + "'";
            List<Time> temp = se.createQuery(hql).list();
            time.addAll(temp);
        }

        return sortByDateTime(time);
    }

    /**
     * Sorts a timetable by day of the week and time.
     * @param time Timetable that needs to be printed out
     * @return Sorted timetable
     */
    public List<Time> sortByDateTime(List<Time> time){
        List<Time> sortedTime = new ArrayList<>();
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        for (String day: days) {
            List<Time> temp = new ArrayList<>();

            for (Time t : time) {
                if (t.getDay().equals(day)) {
                    temp.add(t);
                }
            }
            temp.sort(Comparator.comparing(Time::getTimeStart));

            sortedTime.addAll(temp);
        }
        return sortedTime;
    }

    /**
     * Checks if 2 given time periods are overlapping with each other.
     * @param startTime1 Start time of 1st time period
     * @param endTime1 End time of 1st time period
     * @param startTime2 Start time of 2nd time period
     * @param endTime2 End time of 2nd time period
     * @param day1 Day of 1st time period
     * @param day2 Day of 2nd time period
     * @return Boolean indicating whether the time periods overlap
     */
    public boolean timeOverlap(String startTime1, String endTime1, String startTime2, String endTime2, String day1, String day2) {
        boolean overlap = false;

        int startHour1 = Integer.parseInt(startTime1.substring(0,2));
        int startMinute1 = Integer.parseInt(startTime1.substring(3));
        int endHour1 = Integer.parseInt(endTime1.substring(0,2));
        int endMinute1 = Integer.parseInt(endTime1.substring(3));

        int startHour2 = Integer.parseInt(startTime2.substring(0,2));
        int startMinute2 = Integer.parseInt(startTime2.substring(3));
        int endHour2 = Integer.parseInt(endTime2.substring(0,2));
        int endMinute2 = Integer.parseInt(endTime2.substring(3));

        if (day1.equals(day2)) {
            if (startHour2 > startHour1 && startHour2 < endHour1) {
                overlap = true;
            } else if (startHour1 > startHour2 && startHour1 < endHour2) {
                overlap = true;
            } else if (startHour2 == endHour1 && (startMinute2 < endMinute1)) {
                overlap = true;
            } else if (startHour1 == endHour2 && (startMinute1 < endMinute2)) {
                overlap = true;
            } else if (startHour1 == startHour2 && startMinute1 == startMinute2) {
                overlap = true;
            } else if (endHour1 == endHour2 && endMinute1 == endMinute2) {
                overlap = true;
            }
        }
        return overlap;
    }

    /**
     * Determines timetable entries with null rooms.
     * @return List of timetables that don't have booked rooms
     */
    public List<Time> timetableNoRoom() {
        Session se = HibernateUtil.getSessionFactory().openSession();
        se.beginTransaction();

        String hql = "FROM Time WHERE room = null";
        List<Time> emptyRoomTimes = se.createQuery(hql).list();
        se.close();

        return emptyRoomTimes;
    }
}
