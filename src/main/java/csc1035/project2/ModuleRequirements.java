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

    public ModuleRequirements() {}

}
