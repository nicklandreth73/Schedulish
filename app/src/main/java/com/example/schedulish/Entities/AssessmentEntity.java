package com.example.schedulish.Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessment_table")
public class AssessmentEntity {
    @PrimaryKey
    private int assessmentID;

    private String assessmentName;
    private String assessmentDate;
    private int courseID;

    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "assessmentID = " + assessmentID +
                ", assessmentName = " + assessmentName +
                ", assessmentDate = " + assessmentDate +
                ", courseID = " + courseID +
                "}";
    }

    public AssessmentEntity(int assessmentID, String assessmentName, String assessmentDate, int courseID) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.assessmentDate = assessmentDate;
        this.courseID = courseID;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public String getAssessmentDate() {
        return assessmentDate;
    }

    public int getCourseID() {
        return courseID;
    }

}
