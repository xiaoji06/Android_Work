package com.xiaoji.android_work.android;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;

import java.io.File;

public class Task1Activity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task1_activity);
        ((TextView)findViewById(R.id.title)).setText("Task1Activity");


        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("aaa","aaa Environment.getRootDirectory().getAbsolutePath():" + getExternalFilesDirs(Environment.DIRECTORY_MUSIC));
                //startActivity(new Intent(Task1Activity.this,Task1Activity.class));
                Runtime rt = Runtime.getRuntime();
                long maxMemory = rt.maxMemory();
                Log.e("MaxMemory:", Long.toString(maxMemory/(1024*1024)));
                ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                Log.e("MemoryClass:", Long.toString(activityManager.getMemoryClass()));
                Log.e("LargeMemoryClass:", Long.toString(activityManager.getLargeMemoryClass()));

                ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
                activityManager.getMemoryInfo(info);
                Log.e("Memory","系统总内存:"+(info.totalMem / (1024*1024))+"M");
                Log.e("Memory","系统剩余内存:"+(info.availMem / (1024*1024))+"M");
                Log.e("Memory","系统是否处于低内存运行："+info.lowMemory );
                Log.e("Memory","系统剩余内存低于"+( info.threshold  / (1024*1024))+"M时为低内存运行");
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Task1Activity.this,Task2Activity.class));
            }
        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Task1Activity.this,Task3Activity.class));
            }
        });
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }
}
