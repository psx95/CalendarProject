package com.psx.calendarproject.CustomCalendar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.psx.calendarproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import static com.psx.calendarproject.CustomCalendar.CalendarUtilities.generateCellsForCalendarGrid;

/**
 * Created by Pranav Sharma on 17-03-2018.
 */

public class CustomCalendarView extends LinearLayout {

    private static final String TAG = CustomCalendarView.class.getSimpleName();
    private Calendar calendarToday = Calendar.getInstance();
    private int numberOfDaysToShow = 42;
    private HashSet<Date> eventDates = new HashSet<>();
    private Date currentDateTop;

    // attribute values
    public static boolean fillUpAllDays = true;
    private String dateDisplayFormat = "MMM yyyy";
    private boolean showSeasonalColorsOnMonths = false;
    private int nextMonthImage, prevMonthImage;
    private ColorStateList currDateColor;

    //fields in the custom View
    private LinearLayout calendarTopBar, weekDaysContainer, gridViewContainer;
    private ImageView imageViewNextMonth, imageViewPrevMonth;
    private TextView currentDate;
    private View inflatedView;

    private GridView calendarGrid;

    public CustomCalendarView(Context context) {
        super(context);
        initView(context, null);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public CustomCalendarView (Context context, AttributeSet attributeSet, Calendar calendar) {
        this (context,attributeSet);
        this.calendarToday = calendar;
        initView(context,attributeSet);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomCalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, @Nullable AttributeSet attributeSet) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            inflatedView = layoutInflater.inflate(R.layout.custom_calendar, this);
        } else {
            Toast.makeText(context, "There seems to be some problem. Unable to inflate View.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Cannot get a reference to Layout Inflater");
            inflatedView = null;
        }
        findAllViews(inflatedView);
        if (attributeSet != null) {
            loadPreferencesFromAttributes(attributeSet);
            applyLoadedPreferences();
        }
        currentDateTop = calendarToday.getTime();
        Log.d(TAG,"Time for this month is "+calendarToday.getTime());
        setCurrentDate(currentDateTop);
        fillCalendarGrid();
    }

    private void findAllViews(View view) {
        calendarTopBar = view.findViewById(R.id.calendar_top_bar);
        weekDaysContainer = view.findViewById(R.id.week_days_container);
        gridViewContainer = view.findViewById(R.id.gridViewContainer);
        imageViewNextMonth = view.findViewById(R.id.image_next_month);
        imageViewPrevMonth = view.findViewById(R.id.image_prev_month);
        currentDate = view.findViewById(R.id.text_curr_date);
        calendarGrid = view.findViewById(R.id.calendar_grid);
    }

    private void loadPreferencesFromAttributes(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.FlipperCalendar);
        try {
            fillUpAllDays = typedArray.getBoolean(R.styleable.FlipperCalendar_fillUpAllDays, true);
            dateDisplayFormat = (String) CalendarUtilities.MAP_DATE_PATTERN_TO_INTEGER.get(Integer.parseInt(typedArray.getString(R.styleable.FlipperCalendar_dateDisplayFormt)));
            showSeasonalColorsOnMonths = typedArray.getBoolean(R.styleable.FlipperCalendar_showSeasonalColorsOnMonths, false);
            nextMonthImage = typedArray.getInt(R.styleable.FlipperCalendar_nextMonthImage, R.drawable.ic_arrow_right_black_30dp);
            prevMonthImage = typedArray.getInt(R.styleable.FlipperCalendar_prevMonthImage, R.drawable.ic_arrow_left_black_30dp);
            currDateColor = typedArray.getColorStateList(R.styleable.FlipperCalendar_currDateColor);
        } finally {
            typedArray.recycle();
        }
    }

    private void applyLoadedPreferences() {
        changeMonthControlImages();
        changeCurrentDateColor(currDateColor);
    }

    private void changeNumberOfDaysToShow(boolean fillUpAllDays) {
        numberOfDaysToShow = fillUpAllDays ? 42 : 0;
    }

    public void setCurrentDate(Date currentDateTop) {
        setCurrentDateTop(currentDateTop);
        currentDate.setText(new SimpleDateFormat(dateDisplayFormat, Locale.getDefault()).format(currentDateTop));
    }

    private void changeCurrentDateColor(ColorStateList color) {
        currentDate.setTextColor(color);
    }

    private void changeMonthControlImages() {
        imageViewNextMonth.setImageDrawable(getContext().getResources().getDrawable(nextMonthImage));
        imageViewPrevMonth.setImageDrawable(getContext().getResources().getDrawable(prevMonthImage));
    }

    private void fillCalendarGrid() {
        ArrayList<Date> cells = generateCellsForCalendarGrid((Calendar) calendarToday.clone(),numberOfDaysToShow);
        CalendarAdapter calendarAdapter = new CalendarAdapter(getContext(), cells, eventDates);
        setCalendarGridAdapter(calendarAdapter);
    }

    public void setCalendarGridAdapter (CalendarAdapter calendarGridAdapter) {
        calendarGrid.setAdapter(calendarGridAdapter);
    }

    public Date getCurrentDateTop() {
        return currentDateTop;
    }

    public void setCurrentDateTop(Date currentDateTop) {
        this.currentDateTop = currentDateTop;
    }
}
