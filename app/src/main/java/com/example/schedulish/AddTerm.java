package com.example.schedulish;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
import android.widget.Toast;

import com.example.schedulish.Entities.TermEntity;
import com.example.schedulish.ViewModel.TermsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTerm extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private String startOrEnd = "startDateText";
    private EditText termName;
    private EditText termStartDate;
    private EditText termEndDate;
    private AutoCompleteTextView termNotes;
    private TermsViewModel mTermViewModel;
    private long timeInMills;
    private long startTimeInMills;
    private long endTimeInMills;
    private int r = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        mTermViewModel = new ViewModelProvider(this).get(TermsViewModel.class);
        setContentView(R.layout.activity_add_term);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Sets title differently for existing terms or new terms
        if(getIntent().getStringExtra("termName") != null){
            getSupportActionBar().setTitle("Edit Term");
        }else getSupportActionBar().setTitle("Add Term");

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Sets value fields if intents exist
        termName = findViewById(R.id.termNameField);
        termStartDate = findViewById(R.id.termStartDateText);
        termEndDate = findViewById(R.id.termEndDateText);
        termNotes = findViewById(R.id.termNotes);
        if(termName != null){
            termName.setText(getIntent().getStringExtra("termName"));
            termStartDate.setText(getIntent().getStringExtra("termStartDate"));
            termEndDate.setText(getIntent().getStringExtra("termEndDate"));
            termNotes.setText(getIntent().getStringExtra("termNotes"));
        }


        //Sets alarms to the text in the fields if null sends warning toast
        FloatingActionButton startTermAlert = findViewById(R.id.termStartAlert);
        startTermAlert.setOnClickListener((view) -> {
            if(!termStartDate.getText().toString().matches("")) {
                Intent intent = new Intent(AddTerm.this, MyReceiver.class);
                intent.putExtra("text", "You have a term starting on " + termStartDate.getText());
                intent.putExtra("title","Term Start Alert");
                PendingIntent sender = PendingIntent.getBroadcast(AddTerm.this,r,intent,0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startTimeInMills, sender);
            }else Toast.makeText(this, "Please set date before adding an alert", Toast.LENGTH_SHORT).show();
        });
        FloatingActionButton endTermAlert = findViewById(R.id.termEndAlert);
        endTermAlert.setOnClickListener((view) -> {
            if(!termEndDate.getText().toString().matches("")) {
                Intent intent = new Intent(AddTerm.this, MyReceiver.class);
                intent.putExtra("text", "You have a term ending on " + termEndDate.getText());
                intent.putExtra("title","Term End Alert");
                PendingIntent sender = PendingIntent.getBroadcast(AddTerm.this,r,intent,0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, endTimeInMills, sender);
            }else Toast.makeText(this, "Please set date before adding an alert", Toast.LENGTH_SHORT).show();
        });

        //Start and End date picker buttons and fields display calendar view
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
        EditText startDateField = findViewById(R.id.termStartDateText);
        startDateField.setOnTouchListener((view,event) -> {
            startOrEnd = "start";
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
            return true;
        });

        EditText endDateField = findViewById(R.id.termEndDateText);
        endDateField.setOnTouchListener((view, event) -> {
            startOrEnd = "end";
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
            return true;
        });
    }
    //Displays a menu with a save and delete icon if this is an existing term and only a save button if it is a new term
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_save, menu);
    return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save_button) {
            if(!termName.getText().toString().matches("")) {
                Intent replyIntent = new Intent();
                String name = termName.getText().toString();
                String startDate = termStartDate.getText().toString();
                String endDate = termEndDate.getText().toString();
                String notes = termNotes.getText().toString();
                replyIntent.putExtra("termName", name);
                replyIntent.putExtra("termStartDate", startDate);
                replyIntent.putExtra("termEndDate", endDate);
                replyIntent.putExtra("termNotes", notes);
                if (getIntent().getStringExtra("termName") != null) {
                    int termID = getIntent().getIntExtra("termID", 0);
                    TermEntity term = new TermEntity(termID, name, startDate, endDate, notes);
                    mTermViewModel.insert(term);
                }
                setResult(RESULT_OK, replyIntent);
                finish();
                return true;
            }else Toast.makeText(this, "Please add term name before saving", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.share_button) {
            if(!termNotes.getText().toString().matches("")){
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, termNotes.getText());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Term Notes for " + termName.getText());
                sendIntent.setType("text/plain");

                startActivity(sendIntent);
            }else Toast.makeText(this, "Please add notes before sharing", Toast.LENGTH_SHORT).show();

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
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());


        timeInMills =c.getTimeInMillis();

        if(startOrEnd == "start") {
            EditText dateText = (EditText) findViewById(R.id.termStartDateText);
            dateText.setText(currentDateString);
            startTimeInMills = timeInMills;
        }
        if(startOrEnd == "end") {
            EditText dateText = (EditText) findViewById(R.id.termEndDateText);
            dateText.setText(currentDateString);
            endTimeInMills = timeInMills;
        }
    }

}
