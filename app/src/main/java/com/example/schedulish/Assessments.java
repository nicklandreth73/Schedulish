package com.example.schedulish;

import android.content.Intent;
import android.os.Bundle;

import com.example.schedulish.Entities.AssessmentEntity;
import com.example.schedulish.UI.AssessmentsAdapter;
import com.example.schedulish.ViewModel.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class Assessments extends AppCompatActivity {

    private final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private AssessmentViewModel mAssessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        RecyclerView recyclerView = findViewById(R.id.assessmentView);
        final AssessmentsAdapter adapter = new AssessmentsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAssessmentViewModel.getAllAssessments().observe(this, adapter::setAssessments);



    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            AssessmentEntity assessment = new AssessmentEntity(mAssessmentViewModel.lastID()+1, data.getStringExtra("assessmentName"), data.getStringExtra("assessmentDate"),
                  data.getStringExtra("assessmentNotes"),  getIntent().getIntExtra("courseID", 0), getIntent().getBooleanExtra("objective", true));
            mAssessmentViewModel.insert(assessment);
        }
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
            Toast.makeText(getApplicationContext(), "Select an Assessment to delete",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
