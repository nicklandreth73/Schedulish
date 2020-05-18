package com.example.schedulish;

import android.content.Intent;
import android.os.Bundle;

import com.example.schedulish.Entities.CourseEntity;
import com.example.schedulish.ViewModel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private CourseViewModel mCourseViewModel;
    private List<CourseEntity> mCourses;
    private int numCurrent = 0;
    private int numPast = 0;
    private int numFuture = 0;
    private int numCourses = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        setSupportActionBar(toolbar);
        //Gets all courses and checks witch ones are current future or passed then increments int values
        mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable final List<CourseEntity> courses) {
                for(CourseEntity c:courses){
                    numCourses++;
                    switch (c.getStatus()){
                        case "Current":
                            numCurrent++;
                            break;
                        case "Past":
                            numPast++;
                            break;
                        case "Future":
                            numFuture++;
                            break;

                    }
                }
            }
            });
        TextView pastCourses = findViewById(R.id.pastCourses);
        TextView currentCourses = findViewById(R.id.currentCourses);
        TextView futureCourses = findViewById(R.id.futureCourses);
        pastCourses.setText(Integer.toString(numPast));
        currentCourses.setText(Integer.toString(numCurrent));
        futureCourses.setText(Integer.toString(numFuture));
        TextView courses = findViewById(R.id.courses);
        courses.setText(Integer.toString(numCourses));



    }



}
