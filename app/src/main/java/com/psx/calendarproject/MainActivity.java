package com.psx.calendarproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.psx.enhancedcalendar.FlipperCalendar;

public class MainActivity extends AppCompatActivity {

    //CustomCalendarView customCalendarView;
    FlipperCalendar flipperCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flipperCalendar = findViewById(R.id.flip_cal);
    }
}
