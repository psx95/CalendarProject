package com.psx.enhancedcalendar;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by Pranav Sharma on 18-03-2018.
 */

public class CalendarFlipAdapter extends BaseAdapter {

    private static final String TAG = CalendarFlipAdapter.class.getSimpleName();
    private ArrayList<Calendar> calendars;
    private ArrayList<CustomCalendarView> customCalendarViews;
    private static int currPosition = 1;
    private HashSet<Date> eventDays;

    CalendarFlipAdapter(ArrayList<Calendar> calendars, ArrayList<CustomCalendarView> calendarViews, HashSet<Date> eventDates) {
        this.calendars = calendars;
        this.customCalendarViews = calendarViews;
        this.eventDays = eventDates;
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
        customCalendarView.fillCalendarGrid(calendars.get(position),eventDays);
        customCalendarView.setCurrentDate(calendars.get(position).getTime());
        return customCalendarView;
    }

    public int getCurrentViewPos () {
        return currPosition;
    }

    public CalendarFlipAdapter getCurrentCalendarFlipAdapter() {
        return this;
    }

    public void updateSpecialDaysList (HashSet<Date> updatedList) {
        Log.d(TAG,"Inside update function");
        eventDays.addAll(updatedList);
        notifyDataSetChanged();
    }
}
