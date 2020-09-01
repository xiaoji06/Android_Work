package com.xiaoji.android_work.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class View3 extends View {
    public View3(Context context) {
        super(context);
    }

    public View3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public View3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("touch","view3:dispatchTouchEvent:"+ TouchUtils.getEventString(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("touch","view3:onTouchEvent:"+TouchUtils.getEventString(event));
        //return super.onTouchEvent(event);
        return true;
    }
}
