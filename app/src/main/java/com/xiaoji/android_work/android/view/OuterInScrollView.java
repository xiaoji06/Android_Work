package com.xiaoji.android_work.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class OuterInScrollView extends ScrollView {
    public OuterInScrollView(Context context) {
        super(context);
    }

    public OuterInScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OuterInScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float y=0;

    float mLastY=0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        y=ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                Log.e("touch","oi-down:dispatchTouchEvent:"+ TouchUtils.getEventString(ev));
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaY = ev.getY() - mLastY;
                if (deltaY>=0){
                    if (getScrollY()==0){
                        getParent().requestDisallowInterceptTouchEvent(false);
                        Log.e("touch-move-false","oi:dispatchTouchEvent:"+ TouchUtils.getEventString(ev));
                    }else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        Log.e("touch-move-true","oi:dispatchTouchEvent:"+ TouchUtils.getEventString(ev));
                    }
                }else if (deltaY<0){
                    int sy=getScrollY();
                    int sh= getMeasuredHeight();
                    int ch=getChildAt(0).getMeasuredHeight();
                    if ((sy+sh)==ch){
                        getParent().requestDisallowInterceptTouchEvent(false);
                        Log.e("touch-move-false","oi:dispatchTouchEvent:"+ TouchUtils.getEventString(ev));
                    }else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        Log.e("touch-move-true","oi:dispatchTouchEvent:"+ TouchUtils.getEventString(ev));
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e("touch-cancel","oi:dispatchTouchEvent:"+ TouchUtils.getEventString(ev));
                break;
        }
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (ev.getAction()==MotionEvent.ACTION_DOWN){
//            return false;
//        }
        Log.e("touch","oi:onInterceptTouchEvent:"+TouchUtils.getEventString(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("touch","oi:onTouchEvent:"+TouchUtils.getEventString(event));
        return super.onTouchEvent(event);
    }
}
