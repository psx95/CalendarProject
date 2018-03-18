package com.psx.calendarproject.CustomCalendar;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.psx.calendarproject.R;

/**
 * Created by Pranav Sharma on 18-03-2018.
 */

public class CustomCalendarFlipper extends LinearLayout {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());
    private LayoutInflater layoutInflater;
    private ViewFlipper viewFlipper;
    private Context context;

    public CustomCalendarFlipper(Context context) {
        super(context);
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initView(context);
    }

    private void initView (Context context){
        View view = layoutInflater.inflate(R.layout.custom_calendar_flipper, this);
        viewFlipper = view.findViewById(R.id.calendar_flipper);
        viewFlipper.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });
    }

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.left_in));
                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.left_out));
                    viewFlipper.showNext();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.right_in));
                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(context,R.anim.right_out));
                    viewFlipper.showPrevious();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }
}
