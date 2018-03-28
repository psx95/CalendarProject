package com.psx.calendarproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.psx.enhancedcalendar.FlipperCalendar;
import com.psx.enhancedcalendar.MonthUpdateCallback;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    //CustomCalendarView customCalendarView;
    private static final String TAG = MainActivity.class.getSimpleName();
    FlipperCalendar flipperCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flipperCalendar = findViewById(R.id.flip_cal);
        HashSet<Date> dates = new HashSet<>();
        dates.add(Calendar.getInstance().getTime());
        Log.d(TAG,"On Create Current month Calendr is for month "+flipperCalendar.getCurrentMonthCalendar().get(Calendar.MONTH));
        flipperCalendar.setSpecialDays(dates);
        flipperCalendar.setMonthUpdateListner(new MonthUpdateCallback() {
            @Override
            public void onMonthUpdate() {
                Log.d(TAG,"Month Update listener called");
                Calendar calendar = (Calendar) flipperCalendar.getCurrentMonthCalendar().clone();
                calendar.set(Calendar.DATE,Calendar.getInstance().get(Calendar.DATE));
                calendar.set(Calendar.MONTH,flipperCalendar.getCurrentMonthCalendar().get(Calendar.MONTH));
                Date date = calendar.getTime();
                HashSet<Date> dates1 = flipperCalendar.getSpecialDays();
                dates1.add(date);
                flipperCalendar.setSpecialDays(dates1);
                Log.d(TAG,"Current month Calendr is for month "+flipperCalendar.getCurrentMonthCalendar().get(Calendar.MONTH));
                Log.d(TAG,"Special days are "+dates1);
                flipperCalendar.getCalendarFlipAdapter().notifyDataSetChanged();
            }
        });
        flipperCalendar.displayCalendars();
    }
}
