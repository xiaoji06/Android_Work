package com.xiaoji.android_work;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoji.android_work.android.ActivityAnimActivity;
import com.xiaoji.android_work.android.BannerActivity;
import com.xiaoji.android_work.android.DragRecycleviewActivity;
import com.xiaoji.android_work.android.PhotoActivity;
import com.xiaoji.android_work.android.ScanActivity;
import com.xiaoji.android_work.android.TouchConflictActivity;
import com.xiaoji.android_work.android.VLayoutActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {
    Intent intent=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        intent=new Intent(MainActivity.this, ActivityAnimActivity.class);

        findViewById(R.id.anim1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MainActivity.this, DragRecycleviewActivity.class);
                intent.putExtra("type",1);
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });

        findViewById(R.id.anim2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MainActivity.this, ScanActivity.class);
                intent.putExtra("type",2);
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
        findViewById(R.id.anim3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MainActivity.this, BannerActivity.class);
                intent.putExtra("type",3);
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
        findViewById(R.id.anim4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MainActivity.this, VLayoutActivity.class);
                ActivityOptionsCompat optionsCompat =ActivityOptionsCompat.makeSceneTransitionAnimation(
                        MainActivity.this,
                        findViewById(R.id.img),
                        "img"
                );
                intent.putExtra("type",4);
                startActivity(intent, optionsCompat.toBundle());
            }
        });

        findViewById(R.id.anim5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MainActivity.this, TouchConflictActivity.class);
                intent.putExtra("type",5);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_bottom_in, android.R.anim.fade_out);
            }
        });

        findViewById(R.id.anim6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MainActivity.this, PhotoActivity.class);
                intent.putExtra("type",5);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_bottom_in, android.R.anim.fade_out);
            }
        });

        findViewById(R.id.anim7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_bottom_in, android.R.anim.fade_out);
            }
        });


        ((TextView)findViewById(R.id.anim10)).setText(getApplicationContext().getResources().getString(R.string.list4));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeLanguage(String msg){
        if (!TextUtils.isEmpty(msg) && msg.equals("chongqi")){
            recreate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 防止重叠
     * 当应用的内存紧张的时候，系统会回收掉Fragment对象
     * 再一次进入的时候会重新创建Fragment
     * 非原来对象，我们无法控制，导致重叠
     *
     * @param fragment
     */
    @Override
    public void onAttachFragment(Fragment fragment) {
//        if (mStarFragment != null && fragment instanceof StarFragment) {
//            mStarFragment = (StarFragment) fragment;
//        }
//        if (mSquareFragment != null && fragment instanceof SquareFragment) {
//            mSquareFragment = (SquareFragment) fragment;
//        }
//        if (mChatFragment != null && fragment instanceof ChatFragment) {
//            mChatFragment = (ChatFragment) fragment;
//        }
//        if (mMeFragment != null && fragment instanceof MeFragment) {
//            mMeFragment = (MeFragment) fragment;
//        }
    }


    //第一次按下时间
    private long firstClick;

    @Override
    public void onBackPressed() {
        AppExit();
    }

    /**
     * 再按一次退出
     */
    public void AppExit() {
        if (System.currentTimeMillis() - this.firstClick > 2000L) {
            this.firstClick = System.currentTimeMillis();
            Toast.makeText(this, "再按一次推出应用", Toast.LENGTH_LONG).show();
            return;
        }
        finish();
    }
}
