package com.example.schedulish;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.schedulish.Entities.CourseEntity;
import com.example.schedulish.Entities.TermEntity;
import com.example.schedulish.UI.CourseAdapter;
import com.example.schedulish.ViewModel.CourseViewModel;
import com.example.schedulish.ViewModel.TermsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        mTermViewModel = new ViewModelProvider(this).get(TermsViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.termDetailsView);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable final List<CourseEntity> courses) {

                List<CourseEntity> filteredCourses=new ArrayList<>();
                for(CourseEntity c:courses)if(c.getTermID()==getIntent().getIntExtra("termID",0))filteredCourses.add(c);
                adapter.setCourses(filteredCourses);
                numCourses=filteredCourses.size();
            }


        });

        FloatingActionButton addCourse = findViewById(R.id.addCourse);
        addCourse.setOnClickListener((view) -> {
            Intent intent = new Intent(TermDetails.this, AddCourse.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            CourseEntity course = new CourseEntity(mCourseViewModel.lastID()+1, data.getStringExtra("courseName"), data.getStringExtra("courseStartDate"), data.getStringExtra("courseEndDate"),
                     data.getStringExtra("instructorName"), data.getStringExtra("status"), data.getStringExtra("courseNotes"),  getIntent().getIntExtra("termID",0));
            mCourseViewModel.insert(course);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
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
            if(numCourses == 0){
                mTermViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<TermEntity> terms) {
                        List<TermEntity> filteredTerms = new ArrayList<>();
                        for(TermEntity t: terms){
                            if(t.getTermID() == getIntent().getIntExtra("termID",0)){
                                mTermViewModel.delete(t);
                                Toast.makeText(getApplicationContext(), "Term Deleted",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }else Toast.makeText(getApplicationContext(), "Terms cannot be deleted if they have courses",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
