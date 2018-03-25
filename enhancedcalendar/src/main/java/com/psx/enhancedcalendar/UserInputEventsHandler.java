package com.psx.enhancedcalendar;

import java.util.Date;

/**
 * Created by Pranav Sharma on 17-03-2018.
 */

public interface UserInputEventsHandler {
    void onDateLongPress(Date date);
    void onDatePress (Date date);
    void onMonthForward ();
    void onMonthBackward ();
}
