package csc1035.project2;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Staff")
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

    public Staff() {
    }

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


    public static void listOfStaffResult(List<Staff> staff) {
        if (staff.size() == 0) {
            System.out.println("\nNo Staff were found in this Module");
        } else {
            String printPeopleFormat = "| %-3s | %-10s | %-20s | %-25s |%n";
            System.out.println("+-----+------------+----------------------+---------------------------+");
            System.out.println("| Row | StaffID  | First Name           | Last Name                 |");
            System.out.println("+-----+------------+----------------------+---------------------------+");
            for (int i = 0; i < staff.size(); i++) {
                System.out.format(printPeopleFormat, i + 1, staff.get(i).getStaffID(), staff.get(i).getFirstName(), staff.get(i).getLastName());
            }
            System.out.println("+-----+------------+----------------------+---------------------------+");
        }
    }
}