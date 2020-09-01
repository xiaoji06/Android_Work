package com.xiaoji.android_work.android.view;

import android.view.MotionEvent;

public class TouchUtils {

    public static String getEventString(MotionEvent ev){
        String name="null";
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                name="ACTION_DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                name="ACTION_MOVE";
                break;
            case MotionEvent.ACTION_UP:
                name="ACTION_UP";
                break;
            case MotionEvent.ACTION_CANCEL:
                name="ACTION_CANCEL";
                break;
            case MotionEvent.ACTION_OUTSIDE:
                name="ACTION_OUTSIDE";
                break;
        }
        return name;
    }

}
