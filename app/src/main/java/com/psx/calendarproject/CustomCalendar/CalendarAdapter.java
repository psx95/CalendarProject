package com.psx.calendarproject.CustomCalendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.Date;
import java.util.List;

/**
 * Created by Pranav Sharma on 17-03-2018.
 */

public class CalendarAdapter extends ArrayAdapter<Date> {

    public CalendarAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public CalendarAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public CalendarAdapter(@NonNull Context context, int resource, @NonNull Date[] objects) {
        super(context, resource, objects);
    }

    public CalendarAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Date[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public CalendarAdapter(@NonNull Context context, int resource, @NonNull List<Date> objects) {
        super(context, resource, objects);
    }

    public CalendarAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Date> objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
