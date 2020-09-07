package com.example.schedulish;

import android.content.Intent;
import android.os.Bundle;

import com.example.schedulish.Entities.AssessmentEntity;
import com.example.schedulish.Entities.CourseEntity;
import com.example.schedulish.UI.AssessmentsAdapter;
import com.example.schedulish.ViewModel.AssessmentViewModel;
import com.example.schedulish.ViewModel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CourseDetails extends AppCompatActivity {

    private AssessmentViewModel mAssessmentViewModel;
    public static int numAssessments;
    private CourseViewModel mCourseViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        RecyclerView recyclerView = findViewById(R.id.courseDetailsView);
        final AssessmentsAdapter adapter = new AssessmentsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(@Nullable List<AssessmentEntity> assessments) {
                List<AssessmentEntity> filteredAssessments = new ArrayList<>();
                for(AssessmentEntity a:assessments)if(a.getCourseID()==getIntent().getIntExtra("courseID",0))filteredAssessments.add(a);
                adapter.setAssessments(filteredAssessments);
                numAssessments = filteredAssessments.size();
            }
        });
        FloatingActionButton addAssessment = findViewById(R.id.addAssessment);
        addAssessment.setOnClickListener((view) -> {
            Intent intent = new Intent(CourseDetails.this, AddAssessment.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete_button) {
            if(numAssessments == 0){
                mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<CourseEntity> courses) {
                        List<CourseEntity> filteredCourses = new ArrayList<>();
                        for(CourseEntity c: courses){
                            if(c.getCourseID() == getIntent().getIntExtra("courseID",0)){
                                mCourseViewModel.delete(c);
                                Toast.makeText(getApplicationContext(), "Course Deleted",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }else Toast.makeText(getApplicationContext(), "Courses cannot be deleted if they have assessments",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            AssessmentEntity assessment = new AssessmentEntity(mAssessmentViewModel.lastID()+1, data.getStringExtra("assessmentName"), data.getStringExtra("assessmentDate"),
                    data.getStringExtra("assessmentNotes"),  getIntent().getIntExtra("courseID", 0), getIntent().getBooleanExtra("objective", true));
            mAssessmentViewModel.insert(assessment);
        }
    }

}
