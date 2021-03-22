package csc1035.project2;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * This is a class for representing all Students in University
 */

@Entity(name = "Student")
public class Student {

    @Id
    @Column(updatable = false, nullable = false)
    private int studentID;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "StudentModule",
            joinColumns = {@JoinColumn(name = "studentID")},
            inverseJoinColumns = {@JoinColumn(name = "moduleID")})
    private Set<Module> modules = new HashSet<>();

    public Student() {}

    public Student(int studentID, String firstName, String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

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

    public Set<Module> getModules() {
        return modules;
    }

    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }
}
