package com.example.schedulish;

import android.app.DatePickerDialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.DateFormat;
import java.util.Calendar;

public class AddCourse extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {
    private String startOrEnd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageButton startDateButton = (ImageButton) findViewById(R.id.courseStartDateButton);
        startDateButton.setOnClickListener((view) -> {
            startOrEnd = "start";
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        ImageButton endDateButton = (ImageButton) findViewById(R.id.courseEndDateButton);
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

        if (startOrEnd == "start") {
            EditText dateText = (EditText) findViewById(R.id.courseStartDateText);
            dateText.setText(currentDateString);
        }
        if (startOrEnd == "end") {
            EditText dateText = (EditText) findViewById(R.id.courseEndDateText);
            dateText.setText(currentDateString);
        }

    }
}
