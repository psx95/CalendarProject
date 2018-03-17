package com.psx.calendarproject;

import com.psx.calendarproject.CustomCalendar.CalendarUtilities;

import org.junit.Test;

import java.util.Calendar;

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

    @Test
    public void isIntegerMappingToDatePatternCorrect () {
        assertEquals(CalendarUtilities.MAP_DATE_PATTERN_TO_INTEGER.get(1),"MMM, yyyy");
        assertEquals(CalendarUtilities.MAP_DATE_PATTERN_TO_INTEGER.get(2),"dd/MM/yyyy");
    }

    @Test
    public void dateSameLogic () {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar.set(Calendar.DATE, 21);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.YEAR, 2001);
        calendar1.set(Calendar.DATE, 21);
        calendar1.set(Calendar.MONTH, Calendar.JANUARY);
        calendar1.set(Calendar.YEAR, 2001);
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 30);
        calendar1.set(Calendar.HOUR_OF_DAY, 11);
        calendar1.set(Calendar.MINUTE, 55);
        assertEquals(CalendarUtilities.areDatesSame(calendar1.getTime(),calendar.getTime()),true);
    }
}