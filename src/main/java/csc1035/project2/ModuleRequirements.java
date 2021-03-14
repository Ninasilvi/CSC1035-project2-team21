package csc1035.project2;

import javax.persistence.*;

@Table(name = "ModuleRequirements")
@javax.persistence.Entity(name = "ModuleRequirements")
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

    public ModuleRequirements() {}


    public ModuleRequirements(String moduleID, String weekCommencing, int lecturesPerWeek, int practicalsPerWeek, int lectureLength, int practicalLength){
        this.moduleID = moduleID;
        this.weekCommencing = weekCommencing;
        this.lecturesPerWeek = lecturesPerWeek;
        this.practicalsPerWeek = practicalsPerWeek;
        this.lectureLength = lectureLength;
        this.practicalLength = practicalLength;
    }

    public void ModuleRequirement(){}

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
    public int getPracticalsPerWeek(){
        return practicalsPerWeek;
    }
    public void setPracticalsPerWeek(int practicalsPerWeek){
        this.practicalsPerWeek = practicalsPerWeek;
    }
    public int getLectureLength(){
        return lectureLength;
    }
    public void setLectureLength(int lectureLength){
        this.practicalsPerWeek = lectureLength;
    }
    public int getPracticalLength(){
        return practicalLength;
    }
    public void setPracticalLength(){
        this.practicalLength = practicalLength;
    }

    @Override
    public String toString() {
        return moduleID + " " + weekCommencing  + " " + lecturesPerWeek + " " + practicalsPerWeek + " " + practicalsPerWeek + " " + practicalLength;
    }
}