package csc1035.project2;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Rooms")
public class Rooms {

    @Id
    @Column(updatable = false, nullable = false)
    private String roomNo;

    @Column
    private String roomType;

    @Column
    private int maxCapacity;

    @Column
    private int socialDistancingCapacity;

    public Rooms(String roomNo, String roomType, int maxCapacity, int socialDistancingCapacity) {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.maxCapacity = maxCapacity;
        this.socialDistancingCapacity = socialDistancingCapacity;
    }

    public Rooms() {}

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getSocialDistancingCapacity() {
        return socialDistancingCapacity;
    }

    public void setSocialDistancingCapacity(int socialDistancingCapacity) {
        this.socialDistancingCapacity = socialDistancingCapacity;
    }
}
