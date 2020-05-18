package com.example.schedulish.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "term_table")
public class TermEntity {
    @PrimaryKey
    private int termID;

    private String termName;
    private String termStartDate;
    private String termEndDate;
    private String termNotes;

    @Override
    public String toString(){
        return "TermEntity{" +
                "termID = " + termID +
                ",termName=" + termName +
                ", termStartDate=" + termStartDate +
                ",termEndDate=" + termEndDate +
                 ",termNotes=" + termNotes +
                "}";
    }

    public TermEntity(int termID, String termName, String termStartDate, String termEndDate, String termNotes) {
        this.termID = termID;
        this.termName = termName;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
        this.termNotes = termNotes;
    }

    public String getTermNotes() {
        return termNotes;
    }

    public void setTermNotes(String termNotes) {
        this.termNotes = termNotes;
    }

    public int getTermID() {
        return termID;
    }

    public String getTermName() {
        return termName;
    }

    public String getTermStartDate() {
        return termStartDate;
    }

    public String getTermEndDate() {
        return termEndDate;
    }


    public void setTermID(int termID) {
        this.termID = termID;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public void setTermStartDate(String termStartDate) {
        this.termStartDate = termStartDate;
    }

    public void setTermEndDate(String termEndDate) {
        this.termEndDate = termEndDate;
    }

}
