package com.psx.enhancedcalendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.psx.enhancedcalendar.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static android.graphics.Typeface.BOLD;
import static android.view.View.GONE;

/**
 * Created by Pranav Sharma on 17-03-2018.
 */

public class CalendarAdapter extends ArrayAdapter<Date> {

    private HashSet<Date> specialDateList = new HashSet<>();
    private ArrayList<Date> allDates = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Calendar calendar;
    private UserInputCallback eventHandler;

    CalendarAdapter(Context context, ArrayList<Date> allDates, HashSet<Date> specialDateList, Calendar calendar) {
        super(context, R.layout.cutsom_calendar_day, allDates);
        this.allDates = allDates;
        this.specialDateList = specialDateList;
        this.calendar = calendar;
        Log.d("ADAPTER-CALANDER","Calendar object "+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR));
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
        final Date date = getItem(position);
        // used for highlighting the current date
        Date today = Calendar.getInstance().getTime();
        TextView textViewDate = null;
        LinearLayout linearLayout_container;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.cutsom_calendar_day, parent, false);
            textViewDate = convertView.findViewById(R.id.date_display);
            linearLayout_container = convertView.findViewById(R.id.date_display_container);
            linearLayout_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getEventHandler() == null){
                        Toast.makeText(getContext(),"NULL",Toast.LENGTH_SHORT).show();
                    } else {
                        getEventHandler().onDatePress(date);
                    }
                }
            });
            linearLayout_container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (getEventHandler() == null){
                        Toast.makeText(getContext(),"NULL",Toast.LENGTH_SHORT).show();
                    } else {
                        getEventHandler().onDateLongPress(date);
                    }
                    return true;
                }
            });
        }
        if (specialDateList != null) {
            for (Date d : specialDateList) {
                if (CalendarUtilities.areDatesSame(d,date)) {
                    addEffectsToDate(convertView);
                    break;
                }
            }
        }
        // highlight current date
        if (textViewDate!=null && CalendarUtilities.areDatesSame(date,today)) {
            textViewDate.setTextColor(convertView.getContext().getResources().getColor(R.color.colorPrimaryDark));
        }
        Calendar calendarDate = (Calendar) calendar.clone();
        calendarDate.setTime(date);
        grayOutDateIfNotOfThisMonth (convertView, date, calendar);
        if (textViewDate!=null)
            textViewDate.setText(String.valueOf(calendarDate.get(Calendar.DATE)));
        return convertView;
    }

    private void grayOutDateIfNotOfThisMonth(View convertView, Date date, Calendar calendar) {
        Calendar calendar1 = (Calendar) calendar.clone();
        calendar1.setTime(date);
        if (!CalendarUtilities.dateBelongsToCurrentMonthAndYear(date,calendar)) {
            TextView textView_date = convertView.findViewById(R.id.date_display);
            if (CustomCalendarView.fillUpAllDays) {
                textView_date.setTextColor(getContext().getResources().getColor(R.color.greyed_out));
            } else {
                convertView.setVisibility(GONE);
            }
        }
    }

    private void addEffectsToDate(View convertView) {
        convertView.setBackgroundResource(R.drawable.ic_autorenew_pink_24dp);
    }

    public void setEventHandler (UserInputCallback eventHandler) {
        this.eventHandler = eventHandler;
    }

    public UserInputCallback getEventHandler () {
        return eventHandler;
    }
}
