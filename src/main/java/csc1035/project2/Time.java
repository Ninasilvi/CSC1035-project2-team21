package csc1035.project2;

import javax.persistence.*;

@Entity(name = "Time")
public class Time {

    @Column
    private String timeStart;

    @Column
    private String timeEnd;

    @Column
    private String day;

    @Column
    private String moduleID;

    @Column
    private float roomNumber;

    public Time() {}

    public Time(String timetableName, String timeStart, String timeEnd, String day, String moduleID, float roomNumber) {
        this.timetableName = timetableName;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.day = day;
        this.moduleID = moduleID;
        this.roomNumber = roomNumber;
    }

    public String getTimetableName() {
        return timetableName;
    }

    public void setTimetableName(String timetableName) {
        this.timetableName = timetableName;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public float getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(float roomNumber) {
        this.roomNumber = roomNumber;
    }

}