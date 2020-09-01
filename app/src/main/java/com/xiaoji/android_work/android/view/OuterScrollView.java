package com.xiaoji.android_work.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class OuterScrollView extends ScrollView {
    public OuterScrollView(Context context) {
        super(context);
    }

    public OuterScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OuterScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float x=0;
    float y=0;

    float mLastX=0;
    float mLastY=0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        x=ev.getX();
        y=ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                float deltaX = ev.getX() - mLastX;
                float deltaY = ev.getY() - mLastY;
                if (Math.abs(deltaY)>Math.abs(deltaX)){
                    return true;
                }
        }
        mLastX = x;
        mLastY = y;
        return super.onInterceptTouchEvent(ev);
    }
}
