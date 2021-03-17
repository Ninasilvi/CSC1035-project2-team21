package csc1035.project2;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Staff")
public class Staff {

    @Id
    @Column(updatable = false, nullable = false)
    private String staffID;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "StaffModule",
            joinColumns = {@JoinColumn(name = "staffID")},
            inverseJoinColumns = {@JoinColumn(name = "moduleID")})
    private Set<Module> modules = new HashSet<>();

    public Staff() {
    }

    public Staff(String staffID, String firstName, String lastName) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public Set<Module> getModules() {
        return modules;
    }

    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }
}