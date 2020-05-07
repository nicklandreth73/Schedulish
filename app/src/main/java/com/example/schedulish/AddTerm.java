package com.example.schedulish;

import android.app.DatePickerDialog;
import android.os.Bundle;

import com.example.schedulish.ViewModel.AssessmentViewModel;
import com.example.schedulish.ViewModel.CourseViewModel;
import com.example.schedulish.ViewModel.TermsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.schedulish.R;

import java.text.DateFormat;
import java.util.Calendar;

public class AddTerm extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private String startOrEnd = "startDateText";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        //Start and End date pickers set dates to be displayed in edit text fields
        ImageButton startDateButton = (ImageButton) findViewById(R.id.termStartDateButton);
        startDateButton.setOnClickListener((view) -> {
            startOrEnd = "start";
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        ImageButton endDateButton = (ImageButton) findViewById(R.id.termEndDateButton);
        endDateButton.setOnClickListener((view) -> {
            startOrEnd = "end";
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        if(startOrEnd == "start") {
            EditText dateText = (EditText) findViewById(R.id.termStartDateText);
            dateText.setText(currentDateString);
        }
        if(startOrEnd == "end") {
            EditText dateText = (EditText) findViewById(R.id.termEndDateText);
            dateText.setText(currentDateString);
        }
    }

}
