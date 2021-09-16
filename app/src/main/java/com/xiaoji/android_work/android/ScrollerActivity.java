package com.xiaoji.android_work.android;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;

public class ScrollerActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);

        View view=findViewById(R.id.view);
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(view,"scaleX",1f,2f,3f);


    }
}
