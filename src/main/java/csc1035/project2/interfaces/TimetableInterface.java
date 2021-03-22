package csc1035.project2.interfaces;

import csc1035.project2.*;
import csc1035.project2.Module;
import org.hibernate.Session;

import java.util.*;

public interface TimetableInterface {

    void listOfStudents(String moduleID, Session se);

    void listOfStaff(String moduleID, Session se);

    void listOfModuleReq();

    Time createTimetable(String day, String timetableName, String moduleID, String timeStart, String timeEnd);

    void producingStudentTimetable(Student student, Session se);

    void producingStaffTimetable(Staff staff, Session se);

    List<Time> producingTimetableForModule(List<Module> modules, Session se);

    List<Time> sortByDateTime(List<Time> time);

    boolean timeOverlap(String startTime1, String endTime1, String startTime2, String endTime2, String day1, String day2);

    List<Time> timetableNoRoom();
}
