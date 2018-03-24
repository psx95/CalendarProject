package com.psx.calendarproject.CustomCalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.psx.calendarproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by Pranav Sharma on 18-03-2018.
 */
//TODO : Setup callback listeners
public class FlipperCalendar extends LinearLayout {

    private static final String TAG = FlipperCalendar.class.getSimpleName();
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private LayoutInflater layoutInflater;
    private View inflatedView;
    private AdapterViewFlipper adapterViewFlipper;
    private GestureDetectorCompat gestureDetectorCompat;
    Calendar currentMonthCalendarInstance, previousMonthCalendarInstance, nextMonthCalendarInstance;
    private static UserInputCallback callbackListener;

    //attributes from XML
    private boolean animate = true;
    private int right_in = R.animator.right_in;
    private int right_out = R.animator.right_out;
    private int left_in = R.animator.left_in;
    private int left_out = R.animator.left_out;

    public FlipperCalendar(Context context) {
        super(context);
        Log.d(TAG, "SIngle param constructor");
        initCalendarInstances();
        initView(context, null);
    }

    public FlipperCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "multi param constructor");
        initCalendarInstances();
        initView(context, attrs);
        setupCallbackListeners();
    }

    public FlipperCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "multi 3 param constructor");
        initCalendarInstances();
        initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FlipperCalendar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.d(TAG, "multi 4 param constructor");
        initCalendarInstances();
        initView(context, attrs);
    }

    public void initView(Context context, @Nullable AttributeSet attributeSet) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null)
            inflatedView = layoutInflater.inflate(R.layout.flipper_calendar, this);
        adapterViewFlipper = inflatedView.findViewById(R.id.calendarFlipper);
        ArrayList<CustomCalendarView> customCalendarViews = prepareArrayListOfCalendars(attributeSet);
        CalendarFlipAdapter calendarFlipAdapter = new CalendarFlipAdapter(context, customCalendarViews);
        adapterViewFlipper.setAdapter(calendarFlipAdapter);
        gestureDetectorCompat = new GestureDetectorCompat(context, new SwipeGestureDetector());
        extractPreferencesForFlipperCalendar(attributeSet);
        adapterViewFlipper.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorCompat.onTouchEvent(event);
                return true;
            }
        });
    }

    private void extractPreferencesForFlipperCalendar(AttributeSet attributeSet) {
        if (attributeSet!=null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet,R.styleable.FlipperCalendar);
            try {
                animate = typedArray.getBoolean(R.styleable.FlipperCalendar_animateChange, true);
                left_in = typedArray.getResourceId(R.styleable.FlipperCalendar_animLeftIn, left_in);
                left_out = typedArray.getResourceId(R.styleable.FlipperCalendar_animLeftOut, left_out);
                right_in = typedArray.getResourceId(R.styleable.FlipperCalendar_animRightIn, right_in);
                right_out = typedArray.getResourceId(R.styleable.FlipperCalendar_animRightOut, right_out);
            } finally {
                typedArray.recycle();
            }
        }
    }

    private void initCalendarInstances() {
        currentMonthCalendarInstance = Calendar.getInstance();
        previousMonthCalendarInstance = (Calendar) currentMonthCalendarInstance.clone();
        previousMonthCalendarInstance.add(Calendar.MONTH, -1);
        nextMonthCalendarInstance = (Calendar) currentMonthCalendarInstance.clone();
        nextMonthCalendarInstance.add(Calendar.MONTH, 1);
        Log.d(TAG, " Month instances prepared are previous month " + previousMonthCalendarInstance.get(Calendar.MONTH) + " Current Month " + currentMonthCalendarInstance.get(Calendar.MONTH) + " Next Month " + nextMonthCalendarInstance.get(Calendar.MONTH));
    }

    private ArrayList<CustomCalendarView> prepareArrayListOfCalendars(AttributeSet attributeSet) {
        ArrayList<CustomCalendarView> customCalendarViews = new ArrayList<>();
        Log.d(TAG, " Preparing current month instance. Month sent is " + currentMonthCalendarInstance.get(Calendar.MONTH));
        CustomCalendarView currentMonth = new CustomCalendarView(getContext(), attributeSet, currentMonthCalendarInstance);
        Log.d(TAG, " Preparing previous motnh instance. Month sent is " + previousMonthCalendarInstance.get(Calendar.MONTH));
        CustomCalendarView previousMonth = new CustomCalendarView(getContext(), attributeSet, previousMonthCalendarInstance);
        Log.d(TAG, " Preparing next Month instance. Month sent is " + nextMonthCalendarInstance.get(Calendar.MONTH));
        CustomCalendarView nextMonth = new CustomCalendarView(getContext(), attributeSet, nextMonthCalendarInstance);
        customCalendarViews.add(previousMonth);
        customCalendarViews.add(currentMonth);
        customCalendarViews.add(nextMonth);
        return customCalendarViews;
    }
    
    private void setupCallbackListeners() {
        CustomCalendarView.setCallbackListener(new UserInputCallback() {
            @Override
            public void onMonthForward() {
                if (animate) {
                    adapterViewFlipper.setInAnimation(getContext(), right_in);
                    adapterViewFlipper.setOutAnimation(getContext(), right_out);
                }
                adapterViewFlipper.showNext();
            }

            @Override
            public void onMonthBackward() {
                if (animate) {
                    adapterViewFlipper.setInAnimation(getContext(), left_in);
                    adapterViewFlipper.setOutAnimation(getContext(), left_out);
                }
                adapterViewFlipper.showPrevious();
            }
        });
    }

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    if (animate) {
                        adapterViewFlipper.setInAnimation(getContext(), left_in);
                        adapterViewFlipper.setOutAnimation(getContext(), left_out);
                    }
                    adapterViewFlipper.showNext();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    if (animate) {
                        adapterViewFlipper.setInAnimation(getContext(), right_in);
                        adapterViewFlipper.setOutAnimation(getContext(), right_out);
                    }
                    adapterViewFlipper.showPrevious();
                    return true;
                }
                Toast.makeText(adapterViewFlipper.getContext(), " Position " + adapterViewFlipper.indexOfChild(adapterViewFlipper.getCurrentView()), Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
