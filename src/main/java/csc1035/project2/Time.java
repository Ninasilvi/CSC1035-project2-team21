package csc1035.project2;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Time")
public class Time {

    @Column
    private Time timeStart;

    @Column
    private Time timeEnd;

    @Column
    private Date day;

    @Column
    private String moduleID;

    @Column
    private float roomNumber;

    public Time(Time timeStart, Time timeEnd, Date day, String moduleID, float roomNumber) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.day = day;
        this.moduleID = moduleID;
        this.roomNumber = roomNumber;
    }

    public Time getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    public Time getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
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