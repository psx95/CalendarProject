package com.psx.calendarproject.CustomCalendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Pranav Sharma on 18-03-2018.
 */

public class CalendarFlipAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CustomCalendarView> customCalendarViews;
    private static int currPosition = 1;

    public CalendarFlipAdapter(Context context, ArrayList<CustomCalendarView> customCalendarViews) {
        this.context = context;
        this.customCalendarViews = customCalendarViews;
    }

    @Override
    public int getCount() {
        return customCalendarViews.size();
    }

    @Override
    public Object getItem(int position) {
        currPosition = position;
        return customCalendarViews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return customCalendarViews.get(position);
    }

    public int getCurrentViewPos () {
        return currPosition;
    }
}
