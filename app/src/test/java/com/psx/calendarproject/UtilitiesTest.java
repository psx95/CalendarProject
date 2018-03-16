package com.psx.calendarproject;

import com.psx.calendarproject.CustomCalendar.CalendarUtilities;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Pranav Sharma on 17-03-2018.
 */

public class UtilitiesTest {
    @Test
    public void leapYearTest () {
        assertEquals (CalendarUtilities.isLeapYear(1992),true);
        assertEquals (CalendarUtilities.isLeapYear(2000),true);
        assertEquals (CalendarUtilities.isLeapYear(1900),false);
    }
}
