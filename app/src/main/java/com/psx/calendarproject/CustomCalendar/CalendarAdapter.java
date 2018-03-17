package com.psx.calendarproject.CustomCalendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.psx.calendarproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static android.graphics.Typeface.BOLD;

/**
 * Created by Pranav Sharma on 17-03-2018.
 */

public class CalendarAdapter extends ArrayAdapter<Date> {

    private HashSet<Date> specialDateList = new HashSet<>();
    private ArrayList<Date> allDates = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public CalendarAdapter (Context context,ArrayList<Date> allDates, HashSet<Date> specialDateList) {
        super(context, R.layout.cutsom_calendar_day, allDates);
        this.allDates = allDates;
        this.specialDateList = specialDateList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CalendarAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public CalendarAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
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

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date date = getItem(position);
        Date today = Calendar.getInstance().getTime();
        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.cutsom_calendar_day, parent, false);
        if (specialDateList != null) {
            for (Date d : specialDateList) {
                if (CalendarUtilities.areDatesSame(d,date)) {
                    addEffectsToDate(convertView);
                    break;
                }
            }
        }
        if (CalendarUtilities.areDatesSame(date,today)) {
            TextView textView = convertView.findViewById(R.id.date_display);
            textView.setTextColor(convertView.getContext().getResources().getColor(R.color.colorPrimaryDark));
        }
        return convertView;
    }

    private void addEffectsToDate(View convertView) {
        convertView.setBackgroundResource(R.drawable.ic_autorenew_pink_24dp);
    }
}
