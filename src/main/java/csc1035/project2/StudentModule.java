package csc1035.project2;

import javax.persistence.*;

@Table(name = "StudentModule")
@javax.persistence.Entity(name = "StudentModule")
public class StudentModule {

    @Id
    @Column(updatable = false, nullable = false)
    private int studentID;

    @Column
    private String moduleID;

    public StudentModule(int studentID, String moduleID) {
        this.studentID = studentID;
        this.moduleID = moduleID;
    }

    public StudentModule() {}

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    @Override
    public String toString() {
        return studentID + " " + moduleID;
    }
}
