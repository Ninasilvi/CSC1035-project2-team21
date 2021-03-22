package csc1035.project2;

import javax.persistence.*;
import java.util.List;

/**
 * This is a class for representing all Rooms in University
 */
@Entity(name = "Rooms")
public class Room {

    @Id
    @Column(updatable = false, nullable = false)
    private double roomNumber;

    @Column
    private String type;

    @Column
    private int maxCapacity;

    @Column
    private int socialDistCapacity;

    @OneToMany(mappedBy = "room")
    private List<Time> times;

    public Room (float roomNumber, String type, int maxCapacity, int socialDistCapacity, List<Time> times) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.maxCapacity = maxCapacity;
        this.socialDistCapacity = socialDistCapacity;
        this.times = times;
    }

    public Room() {}

    public double getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(double roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getSocialDistCapacity() {
        return socialDistCapacity;
    }

    public void setSocialDistCapacity(int socialDistCapacity) {
        this.socialDistCapacity = socialDistCapacity;
    }

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", type='" + type + '\'' +
                ", maxCapacity=" + maxCapacity +
                ", socialDistCapacity=" + socialDistCapacity +
                '}';
    }

    // Checks for logical equivalence
    public boolean compare(Room room) {
        if (this == room) {
            return true;
        }
        if (this.roomNumber == room.roomNumber) {
            return true;
        }
        return this.equals(room);
    }

    public boolean isIn(List<Room> rooms) {
        for (Room room : rooms) {
            if (this.compare(room)) {
                return true;
            }
        }
        return false;
    }
}
