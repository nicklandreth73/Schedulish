package com.example.schedulish;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.schedulish.Entities.AssessmentEntity;
import com.example.schedulish.ViewModel.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddAssessment extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {
    private AssessmentViewModel mAssessmentViewModel;
    private EditText assessmentName;
    private EditText assessmentDate;
    private AutoCompleteTextView assessmentNotes;
    private long timeInMills;
    private String currentDateString = "";
    private int r = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        setContentView(R.layout.activity_add_assessment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getIntent().getStringExtra("assessmentName") != null){
            getSupportActionBar().setTitle("Edit Assessment");
        }else getSupportActionBar().setTitle("Add Assessment");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Sets value fields if intents exist
        assessmentName = findViewById(R.id.assessmentNameField);
        assessmentDate = findViewById(R.id.assessmentDateText);
        assessmentNotes = findViewById(R.id.assessmentNotes);
        if(assessmentName != null){
            assessmentName.setText(getIntent().getStringExtra("assessmentName"));
            assessmentDate.setText(getIntent().getStringExtra("assessmentDate"));
            assessmentNotes.setText(getIntent().getStringExtra("assessmentNotes"));
        }

        //Sets alarms to the text in the fields if null sends warning toast
        FloatingActionButton assessmentAlert = findViewById(R.id.assessmentAlert);
        assessmentAlert.setOnClickListener((view) -> {
            if(currentDateString != "") {
                Intent intent = new Intent(AddAssessment.this, MyReceiver.class);
                intent.putExtra("text", "You have an assessment on " + currentDateString);
                intent.putExtra("title","Assessment Alert");
                PendingIntent sender = PendingIntent.getBroadcast(AddAssessment.this,r,intent,0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMills, sender);
            }else Toast.makeText(this, "Please set date before adding an alert", Toast.LENGTH_SHORT).show();
        });

        // Sets the date button and field to open a calendar fragment
        ImageButton assessmentDateButton = (ImageButton) findViewById(R.id.assessmentDateButton);
        assessmentDateButton.setOnClickListener((view) -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
       });
        EditText assessmentDateField =  findViewById(R.id.assessmentDateText);
        assessmentDateField.setOnTouchListener((view, event) -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
            return true;
        });

    }
    //Displays a menu with a save and delete icon if this is an existing assessment and only a save button if it is a new assessment
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
         getMenuInflater().inflate(R.menu.menu_save_and_delete, menu);
        return true;
    }
    //Allows you to use the save and delete buttons to save and delete assessments
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save_button) {
            Intent replyIntent = new Intent();
            String name = assessmentName.getText().toString();
            String date = assessmentDate.getText().toString();
            String notes = assessmentNotes.getText().toString();
            replyIntent.putExtra("assessmentName",name);
            replyIntent.putExtra("assessmentDate",date);
            replyIntent.putExtra("assessmentNotes",notes);
        if(getIntent().getStringExtra("assessmentName") !=null){
            int assessmentID = getIntent().getIntExtra("assessmentID", 0);
            int courseID = getIntent().getIntExtra("courseID",0);
            AssessmentEntity assessment = new AssessmentEntity(assessmentID,name,date,notes,courseID);
            mAssessmentViewModel.insert(assessment);
        }
        setResult(RESULT_OK,replyIntent);
        finish();
            return true;
        }
        if (id == R.id.delete_button) {
                mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<AssessmentEntity> assessments) {
                        List<AssessmentEntity> filteredAssessments = new ArrayList<>();
                        for (AssessmentEntity a : assessments) {
                            if (a.getAssessmentID() == getIntent().getIntExtra("assessmentID", 0)) {
                                mAssessmentViewModel.delete(a);
                                Toast.makeText(getApplicationContext(), "Assessment Deleted", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            });
            return true;
        }
        if (id == R.id.share_button) {
            if (!assessmentNotes.getText().toString().matches("")) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, assessmentNotes.getText());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Assessment Notes for " + assessmentName.getText());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            } else Toast.makeText(this, "Please add notes before sharing", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        timeInMills = c.getTimeInMillis();


            EditText dateText = findViewById(R.id.assessmentDateText);
            dateText.setText(currentDateString);
        }
}
