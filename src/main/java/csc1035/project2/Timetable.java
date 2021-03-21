package csc1035.project2;

import csc1035.project2.interfaces.TimetableInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

public class Timetable implements TimetableInterface {

    static UI UI = new UI();
    static RoomBooking r = new RoomBooking();

    /***
     * Creates a List of Students that take a specific Module (determined by moduleID)
     * @param moduleID Which Module Students should be printed
     * @param se Session created in UI.listOfStudentsChoice
     */
    public void listOfStudents(String moduleID, Session se) {
        se.beginTransaction();

        List<Student> result = new ArrayList<>();

        String hql = "SELECT moduleName FROM Module WHERE moduleID = '" + moduleID + "'";
        List<Module> ModuleName = se.createQuery(hql).list();
        List<Student> students = se.createQuery("FROM Student").list();

        //Creates a list of Students taking a Module
        System.out.println('\n' + "Students taking '" + ModuleName.get(0) + "' (" + moduleID + ") Module");
        for (Student student : students) {
            List<Module> temp = new ArrayList<>(student.getModules());
            for (Module module : temp)
                if (module.getModuleID().equals(moduleID)) {
                    result.add(student);
                }
        }
        se.getTransaction().commit();
        se.close();
        UI.listOfStudentsResult(result);
    }

    /**
     * Gets listOfStaff from Database (with specific moduleID) and sends it forward to print it out
     * @param moduleID User chosen
     * @param se Session passed from method to avoid unnecessary sessions
     */
    public void listOfStaff(String moduleID, Session se) {
        se.beginTransaction();
        List<Staff> result = new ArrayList<>();
        String hql = "SELECT moduleName FROM Module WHERE moduleID = '" + moduleID + "'";
        List<Module> ModuleName = se.createQuery(hql).list();
        List<Staff> staff = se.createQuery("FROM Staff").list();

        //Creates a list of Staff Members taking a Module
        System.out.println('\n' + "Staff members teaching '" + ModuleName.get(0) + "' (" + moduleID + ") Module");
        for (Staff staffMember : staff) {
            List<Module> temp = new ArrayList<>(staffMember.getModules());
            for (Module module :temp)
                if (module.getModuleID().equals(moduleID)) {
                    result.add(staffMember);
                }
        }

        se.getTransaction().commit();
        se.close();
        UI.listOfStaffResult(result);
    }

    /**
     * Gets all ModuleRequirements from database,
     * sends it to print function (listOfModuleReqResult)
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
     * Allows Admin to create a Timetable (and book relevant rooms) for the Module
     * @param day Day that User wants to book a room
     * @param timetableName Class name that User wants to book a room
     * @param moduleID Module ID that User wants to book a room
     * @param timeStart Class Starting time that User wants to book a room
     * @param timeEnd Class Ending time that User wants to book a room
     */
    public Time allowCreateTimetable(String day, String timetableName, String moduleID, String timeStart, String timeEnd) {
        Time time = new Time();
        List<Time> temp = new ArrayList<>();
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

        temp.add(time);

        UI.timetableFormat(temp, "Your current timetable creation");
        se.close();

        return time;
    }

    /**
     * Getting Timetable of Students for a Module and sending Timetable to print out in "timetableStudentsResult"
     * @param choice User choice from which Module get Students List
     * @param se Session passed from calling method
     * @param students List of Students for a Module
     */
    public void producingStudentTimetable(int choice, Session se, List<Student> students) {
        List<Module> modules = new ArrayList<>(students.get(choice-1).getModules());

        // Creating a Timetable for a Module and Sorting it by Day and Time
        List<Time> time = UI.producingTimetableForModule(modules, se);

        se.close();
        UI.timetableStudentsResult(students, choice, time);
    }

    /**
     * Getting Timetable of Staff members for a Module and sending Timetable to print out in "timetableStaffResult"
     * @param choice User choice from which Module get Students List
     * @param se Session passed from calling method
     * @param staff List of Staff Members for a Module
     */
    public void producingStaffTimetable(int choice, Session se, List<Staff> staff) {
        List<Module> modules = new ArrayList<>(staff.get(choice-1).getModules());

        // Creating a Timetable for a Module and Sorting it by Day and Time
        List<Time> time = UI.producingTimetableForModule(modules, se);

        se.close();
        UI.timetableStaffResult(staff, choice, time);
    }



    /**
     * Sorts Timetable by Day of the Week and Time
     * @param time Time (Timetable) list that needs to get printed out
     * @return Sorted Time (Timetable) list
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

            for (Time t: temp) {
                sortedTime.add(t);
            }
        }
        return sortedTime;
    }

    /**
     * Checks if 2 given time periods are overlapping each other
     * @param startTime1 Start Time of User input
     * @param endTime1 End Time of User input
     * @param startTime2 Start Time from Database
     * @param endTime2 End Time from Database
     * @param day1 Day of User input
     * @param day2 Day from Database
     * @return Boolean if overlapping
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
}
