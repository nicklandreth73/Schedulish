package com.example.schedulish;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.schedulish.Entities.CourseEntity;
import com.example.schedulish.ViewModel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddCourse extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {
    private String startOrEnd = "";
    private String state = "Current";
    private EditText courseName;
    private EditText courseStartDate;
    private EditText courseEndDate;
    private EditText instructorName;
    private EditText instructorPhoneNumber;
    private EditText instructorEmail;
    private AutoCompleteTextView courseNotes;
    private RadioGroup courseState;
    private CourseViewModel mCourseViewModel;
    private long startTimeInMills;
    private long endTimeInMills;
    private int r = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getIntent().getStringExtra("courseName") != null){
            getSupportActionBar().setTitle("Edit Course");
        }else getSupportActionBar().setTitle("Add Course");

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //Sets value fields if intents exist
         courseName = findViewById(R.id.courseNameField);
         courseStartDate = findViewById(R.id.courseStartDateText);
         courseEndDate = findViewById(R.id.courseEndDateText);
         instructorName = findViewById(R.id.instructorNameField);
         courseNotes = findViewById(R.id.courseNotes);
         courseState = findViewById(R.id.courseStateGroup);
         instructorPhoneNumber = findViewById(R.id.phoneField);
         instructorEmail = findViewById(R.id.emailField);


        if(getIntent().getStringExtra("courseName")!=null){
            courseName.setText(getIntent().getStringExtra("courseName"));
            courseStartDate.setText(getIntent().getStringExtra("courseStartDate"));
            courseEndDate.setText(getIntent().getStringExtra("courseEndDate"));
            instructorName.setText(getIntent().getStringExtra("instructorName"));
            courseNotes.setText(getIntent().getStringExtra("courseNotes"));
            state = getIntent().getStringExtra("status");
            instructorPhoneNumber.setText(getIntent().getStringExtra("phoneNumber"));
            instructorEmail.setText(getIntent().getStringExtra("email"));

            switch (state){
                case"Current":
                    courseState.check(R.id.Current);
                    break;
                case "Past":
                    courseState.check(R.id.Past);
                    break;
                case "Future":
                    courseState.check(R.id.Future);
                    break;
            }
        }

        //Sets alarms to the text in the fields if null sends warning toast
        FloatingActionButton startCourseAlert = findViewById(R.id.courseStartAlert);
        startCourseAlert.setOnClickListener((view) -> {
            if(!courseStartDate.getText().toString().matches("")) {
                Intent intent = new Intent(AddCourse.this, MyReceiver.class);
                intent.putExtra("text", "You have a course starting on " + courseStartDate.getText());
                intent.putExtra("title","Course Start Alert");
                PendingIntent sender = PendingIntent.getBroadcast(AddCourse.this,r,intent,0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startTimeInMills, sender);
            }else Toast.makeText(this, "Please set date before adding an alert", Toast.LENGTH_SHORT).show();
        });
        FloatingActionButton endCourseAlert = findViewById(R.id.CourseEndAlert);
        endCourseAlert.setOnClickListener((view) -> {
            if(!courseEndDate.getText().toString().matches("")) {
                Intent intent = new Intent(AddCourse.this, MyReceiver.class);
                intent.putExtra("text", "You have a course ending on " + courseEndDate.getText());
                intent.putExtra("title","Course End Alert");
                PendingIntent sender = PendingIntent.getBroadcast(AddCourse.this,r,intent,0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, endTimeInMills, sender);
            }else Toast.makeText(this, "Please set date before adding an alert", Toast.LENGTH_SHORT).show();
        });






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
        EditText startDateField =  findViewById(R.id.courseStartDateText);
        startDateField.setOnTouchListener((view, event) -> {
            startOrEnd = "start";
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
            return true;
        });

        EditText endDateField =  findViewById(R.id.courseEndDateText);
        endDateField.setOnTouchListener((view, event) -> {
            startOrEnd = "end";
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
            return true;
        });
    }
    //Displays a menu with a save and delete icon if this is an existing course and only a save button if it is a new course
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
         getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }
    //Allows you to use the save and delete buttons to save and delete courses
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save_button) {
            if(!courseName.getText().toString().matches("")) {
                Intent replyIntent = new Intent();
                String name = courseName.getText().toString();
                String startDate = courseStartDate.getText().toString();
                String endDate = courseEndDate.getText().toString();
                String instructor = instructorName.getText().toString();
                String status = checkedButton(courseState.getCheckedRadioButtonId());
                String notes = courseNotes.getText().toString();
                String phone = instructorPhoneNumber.getText().toString();
                String email = instructorEmail.getText().toString();
                replyIntent.putExtra("courseName", name);
                replyIntent.putExtra("courseStartDate", startDate);
                replyIntent.putExtra("courseEndDate", endDate);
                replyIntent.putExtra("instructorName", instructor);
                replyIntent.putExtra("status", status);
                replyIntent.putExtra("courseNotes", notes);
                replyIntent.putExtra("phoneNumber", phone);
                replyIntent.putExtra("email", email);
                replyIntent.putExtra("termID", getIntent().getIntExtra("termID", 0));
                if (getIntent().getStringExtra("courseName") != null) {
                    int courseID = getIntent().getIntExtra("courseID", 0);
                    int termID = getIntent().getIntExtra("termID", 0);
                    CourseEntity course = new CourseEntity(courseID, name, startDate, endDate, instructor, status, notes, termID, phone, email);
                    mCourseViewModel.insert(course);
                }

                setResult(RESULT_OK, replyIntent);
                finish();
                return true;
            }else Toast.makeText(this, "Please add name before saving course", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.share_button) {
            if (!courseNotes.getText().toString().matches("")) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, courseNotes.getText());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Course Notes for " + courseName.getText());
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
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());


        if (startOrEnd == "start") {
            EditText dateText = (EditText) findViewById(R.id.courseStartDateText);
            dateText.setText(currentDateString);
            startTimeInMills = c.getTimeInMillis();
        }
        if (startOrEnd == "end") {
            EditText dateText = (EditText) findViewById(R.id.courseEndDateText);
            dateText.setText(currentDateString);
            endTimeInMills = c.getTimeInMillis();
        }

    }
    //Takes the radio button ID and returns a string for the database
    private String checkedButton(int radioButtonID){
        String idString = "Current";
        switch (radioButtonID){
            case R.id.Current :
                idString = "Current";
                break;
            case R.id.Past:
                idString =  "Past";
                break;
            case R.id.Future:
                idString = "Future";
                break;
        }
        return idString;
    }
}
