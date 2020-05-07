package com.example.schedulish.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.schedulish.Entities.TermEntity;

import java.util.List;

@Dao
public interface TermDAO {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(TermEntity term);

    @Delete
    void delete(TermEntity term);

    @Query("DELETE FROM term_table")
    void deleteAllTerms();

    @Query("SELECT * FROM term_table ORDER BY termID ASC")
    LiveData<List<TermEntity>> getAllTerms();

}
