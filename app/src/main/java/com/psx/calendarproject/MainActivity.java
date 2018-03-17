package com.psx.calendarproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.psx.calendarproject.CustomCalendar.CustomCalendarView;

public class MainActivity extends AppCompatActivity {

    CustomCalendarView customCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customCalendarView = findViewById(R.id.cal);
    }
}
