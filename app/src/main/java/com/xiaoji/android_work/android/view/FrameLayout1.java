package com.xiaoji.android_work.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FrameLayout1 extends FrameLayout {
    public FrameLayout1(@NonNull Context context) {
        super(context);
    }

    public FrameLayout1(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FrameLayout1(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("touch","vg1:dispatchTouchEvent:"+ TouchUtils.getEventString(ev));
        return super.dispatchTouchEvent(ev);
        //return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("touch","vg1:onInterceptTouchEvent:"+TouchUtils.getEventString(ev));
        return super.onInterceptTouchEvent(ev);
        //return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("touch","vg1:onTouchEvent:"+TouchUtils.getEventString(event));
        return super.onTouchEvent(event);
        //return true;
    }
}
