package com.xiaoji.android_work.android;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Window;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;

import java.util.regex.Pattern;

public class ActivityAnimActivity extends BaseActivity {

    private ActivityResultLauncher<Intent> myActivityLauncher;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int i= getIntent().getIntExtra("type",1);
        //允许使用transitions
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        myActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

            }
        });
        switch (i){
            case 1:
                myActivityLauncher.launch(new Intent(this,BannerActivity.class));
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

    public class MyResultContract extends ActivityResultContract<Void,String>{

        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Void input) {
            return new Intent(context,BannerActivity.class);
        }

        @Override
        public String parseResult(int resultCode, @Nullable Intent intent) {
            String name=null;
            if (null != intent){
                name=intent.getStringExtra("name");
            }
            return name;
        }
    }
}
