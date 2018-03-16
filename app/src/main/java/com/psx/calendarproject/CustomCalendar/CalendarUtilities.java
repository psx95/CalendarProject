package com.psx.calendarproject.CustomCalendar;

/**
 * Created by Pranav Sharma on 17-03-2018.
 * This class contains the Utility functions used to implement the logic for Displaying Calendar
 */

public class CalendarUtilities {

    public static boolean isLeapYear (int year) {
        return  year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }
}
