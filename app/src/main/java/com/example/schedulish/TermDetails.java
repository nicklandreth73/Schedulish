package com.example.schedulish;

import android.content.Intent;
import android.os.Bundle;

import com.example.schedulish.Entities.CourseEntity;
import com.example.schedulish.UI.CourseAdapter;
import com.example.schedulish.ViewModel.CourseViewModel;
import com.example.schedulish.ViewModel.TermsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TermDetails extends AppCompatActivity {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private TermsViewModel mTermViewModel;
    private CourseViewModel mCourseViewModel;
    public static int numCourses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        RecyclerView recyclerView = findViewById(R.id.courses_view);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable final List<CourseEntity> courses) {
                // Update the cached copy of the words in the adapter.
                List<CourseEntity> filteredCourses=new ArrayList<>();
                for(CourseEntity c:courses)if(c.getTermID()==getIntent().getIntExtra("termID",0))filteredCourses.add(c);
                adapter.setCourses(filteredCourses);
                numCourses=filteredCourses.size();
                //adapter.setWords(words);
            }
        });




//
//        mCourseViewModel.getAllCourses().observe(this,  {
//            List<CourseEntity> filteredCourses = new ArrayList<>();
//            for(CourseEntity c:courses)if(c.getTermID()==getIntent().getIntExtra("termID", 0))filteredCourses.add(c);
//            adapter.setCourses(filteredCourses);
//            numCourses=filteredCourses.size();
//        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            CourseEntity course = new CourseEntity(mCourseViewModel.lastID()+1, data.getStringExtra("courseName"), data.getStringExtra("courseStartDate"), data.getStringExtra("courseEndDat"),
                    data.getStringExtra("status"), data.getStringExtra("instructorName"), 1);
            mCourseViewModel.insert(course);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
