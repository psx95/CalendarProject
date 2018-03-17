package com.psx.calendarproject.CustomCalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.psx.calendarproject.R;

import java.util.Calendar;

/**
 * Created by Pranav Sharma on 17-03-2018.
 */

public class CustomCalendarView extends LinearLayout {

    private static final String TAG = CustomCalendarView.class.getSimpleName();
    private Calendar calendarToday = Calendar.getInstance();

    // attribute values
    private boolean fillUpAllDays = true;
    private String dateDisplayFormat = "MMM YYYY";
    private boolean showSeasonalColorsOnMonths = false;
    private int nextMonthImage, prevMonthImage;

    //fields in the custom View
    private LinearLayout calendarTopBar, weekDaysContainer, gridViewContainer;
    private ImageView imageViewNextMonth, imageViewPrevMonth;
    private TextView currentDate;

    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomCalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView (Context context, AttributeSet attributeSet) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater!=null) {
            layoutInflater.inflate(R.layout.custom_calendar, this);
        } else {
            Toast.makeText(context,"There seems to be some problem. Unable to inflate View.",Toast.LENGTH_LONG).show();
            Log.e(TAG,"Cannot get a reference to Layout Inflater");
        }
        loadPreferencesFromAttributes (attributeSet);
        applyLoadedPreferences ();
    }

    private void loadPreferencesFromAttributes(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.CustomCalendarView);
        try {
            fillUpAllDays = typedArray.getBoolean(R.styleable.CustomCalendarView_fillUpAllDays,true);
            dateDisplayFormat = (String) CalendarUtilities.MAP_DATE_PATTERN_TO_INTEGER.get( Integer.parseInt(typedArray.getString(R.styleable.CustomCalendarView_dateDisplayFormt)));
            showSeasonalColorsOnMonths = typedArray.getBoolean(R.styleable.CustomCalendarView_showSeasonalColorsOnMonths,false);
            nextMonthImage = typedArray.getInt(R.styleable.CustomCalendarView_nextMonthImage, R.drawable.ic_arrow_right_black_30dp);
            prevMonthImage = typedArray.getInt(R.styleable.CustomCalendarView_prevMonthImage, R.drawable.ic_arrow_left_black_30dp);
        } finally {
            typedArray.recycle();
        }
    }

    private void applyLoadedPreferences() {
    }
}
