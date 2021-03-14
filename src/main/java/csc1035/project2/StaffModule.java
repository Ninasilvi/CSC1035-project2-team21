package csc1035.project2;

import javax.persistence.*;

@Entity(name = "StaffModule")
public class StaffModule {

    @Id
    @Column(updatable = false, nullable = false)
    private String staffID;

    @Column
    private String moduleID;

    public StaffModule(String staffID, String moduleID) {
        this.staffID = staffID;
        this.moduleID = moduleID;
    }

    public StaffModule() {}

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    @Override
    public String toString() {
        return staffID + " " + moduleID;
    }
}