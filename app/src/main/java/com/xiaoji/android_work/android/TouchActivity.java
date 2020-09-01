package com.xiaoji.android_work.android;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;
import com.xiaoji.android_work.android.view.FrameLayout1;
import com.xiaoji.android_work.android.view.FrameLayout2;
import com.xiaoji.android_work.android.view.TouchUtils;
import com.xiaoji.android_work.android.view.View3;

public class TouchActivity extends BaseActivity {

    private FrameLayout1 frameLayout1;
    private FrameLayout2 frameLayout2;
    private View3 view3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        frameLayout1=findViewById(R.id.vg1);
        frameLayout2=findViewById(R.id.vg2);
        view3=findViewById(R.id.v3);

//        frameLayout1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("touch","frameLayout1:setOnClickListener:");
//            }
//        });
//
//        frameLayout1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.e("touch","frameLayout1:setOnTouchListener:");
//                return true;
//            }
//        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("touch","activity:dispatchTouchEvent:"+ TouchUtils.getEventString(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("touch","activity:onTouchEvent:"+TouchUtils.getEventString(event));
        return super.onTouchEvent(event);
    }
}
