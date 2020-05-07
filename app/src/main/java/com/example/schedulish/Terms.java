package com.example.schedulish;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.schedulish.Entities.TermEntity;
import com.example.schedulish.UI.TermsAdapter;
import com.example.schedulish.ViewModel.AssessmentViewModel;
import com.example.schedulish.ViewModel.CourseViewModel;
import com.example.schedulish.ViewModel.TermsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class Terms extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private String startOrEnd = "";
    private TermsViewModel mTermViewModel;
    public static int numTerms;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        mTermViewModel = new ViewModelProvider(this).get(TermsViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.termsView);
        final TermsAdapter adapter = new TermsAdapter(this);
        recyclerView.setAdapter(adapter);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Update the cached copy of the words in the adapter.
        mTermViewModel.getAllTerms().observe(this, adapter::setTerms);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((view) -> {
            Intent intent = new Intent(Terms.this, AddTerm.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            TermEntity term = new TermEntity(mTermViewModel.lastID()+1, data.getStringExtra("termName"), data.getStringExtra("termStartDate"), data.getStringExtra("termEndDate"));
            mTermViewModel.insert(term);
        }
    }
}


