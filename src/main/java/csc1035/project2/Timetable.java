package csc1035.project2;

import csc1035.project2.interfaces.TimetableInterface;
import org.hibernate.Session;

import java.util.*;

public class Timetable implements TimetableInterface {

    static UI UI = new UI();

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
     * @param se Session passed from calling method
     */
    public void allowCreateTimetable(String day, String timetableName, String moduleID, String timeStart, String timeEnd, Session se) {

        String hql = "FROM Time T WHERE T.day = '" + day + "' AND T.timetableName = '" + timetableName + "' AND T.moduleID = '" + moduleID + "'";
        List<Time> time = se.createQuery(hql).list();

        UI.timetableFormat(time, "Your current timetable creation");

        /*System.out.print("\nEnter a Room Number: "); // 0.365
        String roomNumber = ic.get_double_input();*/

        //Get Table with a day and roomNumber that are taken
        /*
        String hql = "FROM Time T WHERE T.day LIKE '" + day + "' AND T.roomNumber LIKE " + roomNumber;
        List<Time> freeTime = se.createQuery(hql).list();*/


        /*
        boolean empty = true;
        //If Timetable is empty, don't show anything
        if (freeTime.size() != 0)
            empty = false;

        System.out.println("\nReserved Time:");
        if (!empty) {
            String printTimeFormat = "| %-3s | %-14s | %-9s | %-11s | %-8s | %-11s |%n";
            System.out.println("+-----+----------------+-----------+-------------+----------+-------------+");
            System.out.println("| Row | Timetable Name | Day       | Time        | ModuleID | Room Number |");
            System.out.println("+-----+----------------+-----------+-------------+----------+-------------+");
            for (int i = 0; i < freeTime.size(); i++) {
                System.out.format(printTimeFormat, i + 1, freeTime.get(i).getTimetableName(), freeTime.get(i).getDay(),
                        freeTime.get(i).getTimeStart() + "-" + freeTime.get(i).getTimeEnd(), freeTime.get(i).getModuleID(),
                        freeTime.get(i).getRoomNumber());
            }
            System.out.println("+-----+----------------+-----------+-------------+----------+-------------+");
            se.getTransaction().commit();
        } else {
            System.out.println("\nThere are NO booked rooms!");
        }

        */


        /*
        while (true) {
            boolean end = true;
            System.out.println("\nEnter Module Start Time:");
            timeStart = ic.get_time_input();
            int sHour = Integer.parseInt(timeStart.substring(0,2));
            int sMinute = Integer.parseInt(timeStart.substring(3));
            boolean validTime = false;

            while (!validTime) {
                System.out.println("\nEnter Module End Time:");
                timeEnd = ic.get_time_input();
                int eHour = Integer.parseInt(timeEnd.substring(0,2));
                int eMinute = Integer.parseInt(timeEnd.substring(3));

                if (sHour > eHour || (sHour == eHour && sMinute > eMinute)) {
                    validTime = true;
                } else {
                    System.out.println("\nIt cannot end before it started.\n");
                }
            }

            if (!empty) {
                for (int i = 0; i < freeTime.size(); i++)
                    if (timeOverlap(timeStart, timeEnd, freeTime.get(i).getTimeStart(), freeTime.get(i).getTimeStart()))
                        end = false;

                if (end || freeTime.size() == 0) {
                    break;
                } else {
                    System.out.println("Try again. This Time period is taken!");
                }
            }*/
        se.close();
    }

    /**
     * Getting Timetable of Students for a Module and sending Timetable to print out in "timetableStudentsResult"
     * @param choice User choice from which Module get Students List
     * @param se Session passed from calling method
     * @param students List of Students for a Module
     */
    public void producingStudentTimetable(int choice, Session se, List<Student> students) {
        List<Module> modules = new ArrayList<>(students.get(choice-1).getModules());
        List<Time> time = new ArrayList<>();

        //Creates a Timetable for a specific ModuleID
        for (Module module : modules) {
            String hql = "FROM Time t WHERE t.moduleID = '" + module.getModuleID() + "'";
            List<Time> temp = se.createQuery(hql).list();
            time.addAll(temp);
        }
        List<Time> sortedTime = sortByDateTime(time);

        se.close();
        UI.timetableStudentsResult(students, choice, sortedTime);
    }

    /**
     * Getting Timetable of Staff members for a Module and sending Timetable to print out in "timetableStaffResult"
     * @param choice User choice from which Module get Students List
     * @param se Session passed from calling method
     * @param staff List of Staff Members for a Module
     */
    public void producingStaffTimetable(int choice, Session se, List<Staff> staff) {
        List<Module> modules = new ArrayList<>(staff.get(choice-1).getModules());
        List<Time> time = new ArrayList<>();

        //Creates a Timetable for a specific ModuleID
        for (Module module : modules) {
            String hql = "FROM Time t WHERE t.moduleID = '" + module.getModuleID() + "'";
            List<Time> temp = se.createQuery(hql).list();
            time.addAll(temp);
        }

        List<Time> sortedTime = sortByDateTime(time);

        se.close();
        UI.timetableStaffResult(staff, choice, sortedTime);
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
