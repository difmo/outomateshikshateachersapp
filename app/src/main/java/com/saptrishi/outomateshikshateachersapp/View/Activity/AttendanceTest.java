package com.saptrishi.outomateshikshateachersapp.View.Activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.saptrishi.outomateshikshateachersapp.Connectivity;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.adapter.CustomCalendarAdapter;
import com.saptrishi.outomateshikshateachersapp.adapter.WeekDaysAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AttendanceTest extends AppCompatActivity {

    private GridView calendarGrid, weekGrid;
    private TextView tvMonthYear;
    private Button btnPrevMonth, btnNextMonth;
    private Calendar currentCalendar;
    private List<String> dates = new ArrayList<>();
    private List<String> statusList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // Set status bar color
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.statusbarcolor));

        // Hide ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Check internet connectivity
        if (!Connectivity.isConnected(getApplicationContext())) {
            Snackbar.make(findViewById(android.R.id.content), "Your Attendance is not Sync...", Snackbar.LENGTH_SHORT).show();
        }

        // Initialize UI components
        calendarGrid = findViewById(R.id.calendarGrid);
        weekGrid = findViewById(R.id.weekGrid);
        tvMonthYear = findViewById(R.id.tvMonthYear);
        btnPrevMonth = findViewById(R.id.btnPrevMonth);
        btnNextMonth = findViewById(R.id.btnNextMonth);

        currentCalendar = Calendar.getInstance();

        setupWeekDays();
        generateCalendarDates();

        btnPrevMonth.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, -1);
            generateCalendarDates();
        });

        btnNextMonth.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, 1);
            generateCalendarDates();
        });
    }

    private void setupWeekDays() {
        List<String> weekDays = new ArrayList<>();
        weekDays.add("Sun");
        weekDays.add("Mon");
        weekDays.add("Tue");
        weekDays.add("Wed");
        weekDays.add("Thu");
        weekDays.add("Fri");
        weekDays.add("Sat");

        weekGrid.setAdapter(new WeekDaysAdapter(this, weekDays));
    }


    private void generateCalendarDates() {
        dates.clear();
        statusList.clear();

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        tvMonthYear.setText(sdf.format(currentCalendar.getTime()));

        Calendar tempCal = (Calendar) currentCalendar.clone();
        tempCal.set(Calendar.DAY_OF_MONTH, 1);
        int startDay = tempCal.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        Random random = new Random();

        // Fill empty spaces before the first day of the month
        for (int i = 0; i < startDay; i++) {
            dates.add("");   // Empty cells for spacing
            statusList.add("");
        }

        // Add actual days with dummy status data
        for (int i = 1; i <= daysInMonth; i++) {
            dates.add(String.valueOf(i));

            // Set calendar date
            tempCal.set(Calendar.DAY_OF_MONTH, i);
            int dayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK);

            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                statusList.add("Holiday"); // Weekends as holidays
            } else {
                int rand = random.nextInt(100);
                if (rand < 60) {
                    statusList.add("Present"); // 60% chance of being Present
                } else if (rand < 80) {
                    statusList.add("Absent"); // 20% chance of being Absent
                } else if (rand < 90) {
                    statusList.add("HalfDay"); // 10% chance of being HalfDay
                } else {
                    statusList.add("Holiday"); // 10% chance of being Holiday
                }
            }
        }

        // Set the adapter
        calendarGrid.setAdapter(new CustomCalendarAdapter(this, dates, statusList));
    }
}
