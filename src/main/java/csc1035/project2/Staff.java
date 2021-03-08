package csc1035.project2;
import javax.persistence.*;

@Table(name = "Staff")
public class Staff {

    @Id
    @Column(updatable = false, nullable = false)
    private int staffID;

    @Column
    private String firstName;

    @Column
    private String lastName;


    public Staff(int staffID, String firstName, String lastName) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Staff() {}

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int studentID) {
        this.staffID = studentID;
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
}
