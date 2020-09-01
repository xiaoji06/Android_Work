package com.xiaoji.android_work.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class OuterOutScrollView extends ScrollView {
    public OuterOutScrollView(Context context) {
        super(context);
    }

    public OuterOutScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OuterOutScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("touch","oo:dispatchTouchEvent:"+ TouchUtils.getEventString(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (ev.getAction()==MotionEvent.ACTION_DOWN){
//            return false;
//        }
        Log.e("touch","oo:onInterceptTouchEvent:"+TouchUtils.getEventString(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("touch","oo:onTouchEvent:"+TouchUtils.getEventString(event));
        return super.onTouchEvent(event);
    }
}
