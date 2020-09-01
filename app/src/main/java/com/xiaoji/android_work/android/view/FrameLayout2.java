package com.xiaoji.android_work.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FrameLayout2 extends FrameLayout {
    public FrameLayout2(@NonNull Context context) {
        super(context);
    }

    public FrameLayout2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FrameLayout2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private  int i=0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("touch","vg2:dispatchTouchEvent:"+ TouchUtils.getEventString(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("touch","vg2:onInterceptTouchEvent:"+TouchUtils.getEventString(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("touch","vg2:onTouchEvent:"+TouchUtils.getEventString(event));
        return super.onTouchEvent(event);
        //return true;
    }

}
