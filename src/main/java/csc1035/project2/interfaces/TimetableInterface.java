package csc1035.project2.interfaces;

import csc1035.project2.Staff;
import csc1035.project2.Student;
import csc1035.project2.Time;
import org.hibernate.Session;

import java.util.*;

public interface TimetableInterface {

    void listOfStudents(String moduleID, Session se);

    void listOfStaff(String moduleID, Session se);

    void listOfModuleReq();

    void allowCreateTimetable(String day, String timetableName, String moduleID, String timeStart, String timeEnd, Session se);

    void producingStudentTimetable(int choice, Session se, List<Student> students);

    void producingStaffTimetable(int choice, Session se, List<Staff> staff);

    List<Time> sortByDateTime(List<Time> time);
}
