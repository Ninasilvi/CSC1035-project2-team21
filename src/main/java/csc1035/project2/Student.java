package csc1035.project2;
import net.bytebuddy.build.ToStringPlugin;

import javax.persistence.*;

@Table(name = "Student")
@javax.persistence.Entity(name = "Student")
public class Student {

    @Id
    @Column(updatable = false, nullable = false)
    private int studentID;

    @Column
    private String firstName;

    @Column
    private String lastName;


    public Student(int studentID, String firstName, String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student() {}

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return studentID + " " + firstName + " " + lastName;
    }
}
