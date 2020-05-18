package com.example.schedulish.Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class CourseEntity {
    @PrimaryKey
    private int courseID;

    private String courseName;
    private String courseStartDate;
    private String courseEndDate;
    private String instructorName;
    private String status;
    private String courseNotes;
    private int termID;

    @Override
    public String toString(){
        return "CourseEntity{" +
                ", courseID = " + courseID +
                ", courseName = " + courseName +
                ", courseStartDate = " + courseStartDate +
                ", courseEndDate = " + courseEndDate +
                ", instructorName = " + instructorName +
                ", status = " + status +
                ", termID = " + termID +
                ", courseNotes" + courseNotes +
                " }";


    }
    public CourseEntity(int courseID, String courseName, String courseStartDate, String courseEndDate, String instructorName, String status, String courseNotes, int termID){
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.instructorName = instructorName;
        this.status = status;
        this.courseNotes = courseNotes;
        this.termID = termID;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }
}
