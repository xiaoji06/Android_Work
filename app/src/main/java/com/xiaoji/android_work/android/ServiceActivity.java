package com.xiaoji.android_work.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;

import java.lang.reflect.Field;

public class ServiceActivity extends BaseActivity {

    private ServiceConnection connection;
    private MyService.MyBinder myService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ServiceActivity.this,MyService.class);
                startService(intent);
            }
        });

        findViewById(R.id.bind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ServiceActivity.this,MyService.class);
                connection=new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        myService= (MyService.MyBinder) service;
                        Log.e("service","onServiceConnected");
                        ((TextView)findViewById(R.id.progress)).setText("当前进度为："+myService.getProgess()+"%");
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        myService= null;
                        Log.e("service","onServiceDisconnected");
                    }
                };
                bindService(intent,connection ,BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.unbind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=connection){
                    unbindService(connection);
                }
            }
        });

        findViewById(R.id.destory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ServiceActivity.this,MyService.class);
                stopService(intent);
            }
        });

        findViewById(R.id.progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=myService){
                    ((TextView)findViewById(R.id.progress)).setText("当前进度为："+myService.getProgess()+"%");
                }
            }
        });
    }

}
