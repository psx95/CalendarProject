package com.psx.calendarproject.CustomCalendar;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Pranav Sharma on 18-03-2018.
 */

public class CalendarFlipAdapter extends BaseAdapter {

    private static final String TAG = CalendarFlipAdapter.class.getSimpleName();
    private ArrayList<Calendar> calendars;
    private ArrayList<CustomCalendarView> customCalendarViews;
    private static int currPosition = 1;

    public CalendarFlipAdapter(ArrayList<Calendar> calendars, ArrayList<CustomCalendarView> calendarViews) {
        this.calendars = calendars;
        this.customCalendarViews = calendarViews;
    }

    @Override
    public int getCount() {
        return calendars.size();
    }

    @Override
    public Object getItem(int position) {
        currPosition = position;
        return calendars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomCalendarView customCalendarView = customCalendarViews.get(position);
        customCalendarView.setCalendars(calendars.get(position));
        customCalendarView.fillCalendarGrid(calendars.get(position));
        customCalendarView.setCurrentDate(calendars.get(position).getTime());
        return customCalendarView;
    }

    public int getCurrentViewPos () {
        return currPosition;
    }
}
