package com.psx.calendarproject.CustomCalendar;

import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Pranav Sharma on 17-03-2018.
 * This class contains the Utility functions used to implement the logic for Displaying Calendar
 */

public class CalendarUtilities {

    private static final String TAG = CalendarUtilities.class.getSimpleName();
    public static HashMap MAP_DATE_PATTERN_TO_INTEGER = new HashMap<Integer,String>() {{
        put(1, "MMM, yyyy");
        put(2, "dd/MM/yyyy");
    }};

    public static HashMap MAP_NUMBER_OF_DAYS_TO_MONTH = new HashMap<Integer,Integer> () {{
        put(Calendar.JANUARY,31);
        put(Calendar.FEBRUARY,29);
        put(Calendar.MARCH,31);
        put(Calendar.APRIL,30);
        put(Calendar.MAY,31);
        put(Calendar.JUNE,30);
        put(Calendar.JULY,31);
        put(Calendar.AUGUST,31);
        put(Calendar.SEPTEMBER,30);
        put(Calendar.OCTOBER,31);
        put(Calendar.NOVEMBER,30);
        put(Calendar.DECEMBER,31);
    }};

    public static boolean isLeapYear (int year) {
        return  year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    public static boolean areDatesSame (Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        if (calendar1.get(Calendar.DATE) != calendar2.get(Calendar.DATE))
            return false;
        if (calendar1.get(Calendar.MONTH) != calendar2.get(Calendar.MONTH))
            return false;
        if (calendar1.get(Calendar.YEAR) != calendar2.get(Calendar.YEAR))
            return false;
        return true;
    }

    public static boolean dateBelongsToCurrentMonthAndYear (Date date, Calendar calendarCurrentMonthAndYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Log.d(TAG,"Calendars Compared Are "+calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.MONTH)+ " And "+calendarCurrentMonthAndYear.get(Calendar.DATE) + "/"+ calendarCurrentMonthAndYear.get(Calendar.MONTH));
        if (calendarCurrentMonthAndYear.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && calendarCurrentMonthAndYear.get(Calendar.YEAR) == calendar.get(Calendar.YEAR))
            return true;
        else
            return false;
    }

    public static ArrayList<Date> generateCellsForCalendarGrid (Calendar calendar, int numberOfDaysToShow) {
        ArrayList<Date> cells = new ArrayList<>();
        calendar.set(Calendar.DAY_OF_MONTH,1);
        // TODO : Use this to allow user to change the Beginning of week from sunday to monday
        int beginningMonthCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -beginningMonthCell);
        while (cells.size() < numberOfDaysToShow) {
            cells.add(calendar.getTime());
            Log.d(TAG, "Added date "+calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TAG,"Returned Cells");
        return cells;
    }
}
