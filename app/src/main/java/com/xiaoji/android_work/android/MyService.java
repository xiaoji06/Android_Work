package com.xiaoji.android_work.android;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {

    public boolean isRunning;
    public int progess=0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("service","onCreate");
        isRunning=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning){
                    try {
                        Thread.sleep(1000);
                        progess++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("service","onBind");
        return new MyBinder();
    }

    public class MyBinder extends Binder{
        public int getProgess(){
          return  progess;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("service","unbindService");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning=false;
        progess=0;
        Log.e("service","onDestroy");
    }
}
