package csc1035.project2;

import javax.persistence.*;

@Table(name = "ModuleRequirements")
public class ModuleRequirements {

    @Id
    @Column
    private String moduleID;

    @Column
    private String weekCommencing;

    @Column
    private int lecturesPerWeek;

    @Column
    private int lectureLength;

    @Column
    private int practicalsPerWeek;

    @Column
    private int practicalLength;


    public ModuleRequirements(String moduleID, String weekCommencing, int lecturesPerWeek, int lectureLength, int practicalsPerWeek, int practicalLength) {
        this.moduleID = moduleID;
        this.weekCommencing = weekCommencing;
        this.lecturesPerWeek = lecturesPerWeek;
        this.lectureLength = lectureLength;
        this.practicalsPerWeek = practicalsPerWeek;
        this.practicalLength = practicalLength;


    }

public ModuleRequirements(){}
    public String getModuleID(){
    return moduleID;
    }
public void setModuleID(String moduleID){
    this.moduleID = moduleID;
}
public String getWeekCommencing(){
    return weekCommencing;
}
public void setWeekCommencing(String weekCommencing){
    this.weekCommencing = weekCommencing;
}
public int getLecturesPerWeek(){
    return lecturesPerWeek;
}
public void setLecturesPerWeek(int lecturesPerWeek){
    this.lecturesPerWeek = lecturesPerWeek;
}
public int getLectureLength(){
    return lectureLength;
}
public void setLectureLength(int lectureLength){
    this.lectureLength = lectureLength;
}
public int getPracticalsPerWeek(){
    return practicalsPerWeek;
}
public void setPracticalsPerWeek(int practicalsPerWeek){
    this.practicalsPerWeek = practicalsPerWeek;
}
public int getPracticalLength(){
    return practicalLength;
}
public void setPracticalLength(int practicalLength){
    this.practicalLength = practicalLength;
}
}