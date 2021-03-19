package csc1035.project2;

import org.hibernate.Session;

import java.util.*;

public interface TimetableInterface {

    void listOfStudents(String moduleID, Session se);

    void listOfStaff(String moduleID, Session se);

    void listOfModuleReq();

    void allowCreateTimetable();

    void producingStudentTimetable(int choice, Session se, List<Student> students);

    void producingStaffTimetable(int choice, Session se, List<Staff> staff);

    List<Time> sortByDateTime(List<Time> time);
}
