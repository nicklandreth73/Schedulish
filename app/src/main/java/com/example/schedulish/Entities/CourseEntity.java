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
    private int termID;
    private String instructorName;
    private String status;


    @Override
    public String toString(){
        return "CourseEntity{" +
        "courseID = " + courseID +
        ",courseName = '" + courseName +  '\'' +
        ",courseStartDate = '" + courseStartDate +  '\'' +
        ",courseEndDate = '" + courseEndDate +  '\'' +
                ",status = '" + status +   '\'' +
        ",instructorName = '" + instructorName +  '\'' +
                ",termID = " + termID +
        "}";
    }

    public CourseEntity(int courseID, String courseName, String courseStartDate, String courseEndDate, String status, String instructorName, int termID) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.termID = termID;
        this.instructorName = instructorName;
        this.status = status;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getInstructorName() {return instructorName;}

    public String getStatus() {return status;}

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public int getTermID() {
        return termID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
