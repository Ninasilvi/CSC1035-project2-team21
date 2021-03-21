package csc1035.project2.interfaces;

import csc1035.project2.*;
import org.hibernate.Session;

import java.util.*;

public interface TimetableInterface {

    void listOfStudents(String moduleID, Session se);

    void listOfStaff(String moduleID, Session se);

    void listOfModuleReq();

    Time allowCreateTimetable(String day, String timetableName, String moduleID, String timeStart, String timeEnd);

    void producingStudentTimetable(int choice, Session se, List<Student> students);

    void producingStaffTimetable(int choice, Session se, List<Staff> staff);

    List<Time> sortByDateTime(List<Time> time);

    boolean timeOverlap(String startTime1, String endTime1, String startTime2, String endTime2, String day1, String day2);
}
