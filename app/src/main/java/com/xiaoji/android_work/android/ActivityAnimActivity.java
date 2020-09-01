package com.xiaoji.android_work.android;

import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;

public class ActivityAnimActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int i= getIntent().getIntExtra("type",1);
        //允许使用transitions
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        switch (i){
            case 1:
                //淡入动画
                getWindow().setEnterTransition(new Fade());
                break;
            case 2:
                //滑动动画
                getWindow().setEnterTransition(new Slide());
                break;
            case 3:
                //分解动画
                getWindow().setEnterTransition(new Explode());
                break;
            case 5:

                break;
        }

        setContentView(R.layout.activity_anim);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.slide_bottom_out);
    }
}
