package csc1035.project2;
import javax.persistence.*;

@Table(name = "Staff")
@javax.persistence.Entity(name = "Staff")
public class Staff {

    @Id
    @Column(updatable = false, nullable = false)
    private String staffID;

    @Column
    private String firstName;

    @Column
    private String lastName;


    public Staff(String staffID, String firstName, String lastName) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Staff() {}

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
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
