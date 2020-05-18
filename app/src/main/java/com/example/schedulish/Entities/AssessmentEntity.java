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
    private String assessmentNotes;

    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "assessmentID = " + assessmentID +
                ", assessmentName = " + assessmentName +
                ", assessmentDate = " + assessmentDate +
                ", assessmentNotes =" + assessmentNotes +
                ", courseID = " + courseID +
                "}";
    }

    public AssessmentEntity(int assessmentID, String assessmentName, String assessmentDate, String assessmentNotes , int courseID) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.assessmentDate = assessmentDate;
        this.courseID = courseID;
        this.assessmentNotes = assessmentNotes;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public void setAssessmentDate(String assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getAssessmentNotes() {
        return assessmentNotes;
    }

    public void setAssessmentNotes(String assessmentNotes) {
        this.assessmentNotes = assessmentNotes;
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
