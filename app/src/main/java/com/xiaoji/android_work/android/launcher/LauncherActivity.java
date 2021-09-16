package com.xiaoji.android_work.android.launcher;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;
import com.xiaoji.android_work.android.HomeBean;
import com.xiaoji.android_work.android.launcher.bean.PageData;
import com.xiaoji.android_work.android.launcher.views.DragViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LauncherActivity extends BaseActivity {

    private DragViewPager vp;
    Random r = new Random();
    private ArrayList<HomeBean> list;
    private PageData<HomeBean> pageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_main);
//        initData();
//
//        vp = (DragViewPager) this.findViewById(R.id.vp);
//
//        PageAdapter myAdapter = new PageAdapter(this, pageData);
//        //边界宽度定义
//        vp.setLeftOutZone(100);
//        vp.setRightOutZone(100);
//        vp.setViewPagerHelper(myAdapter);
//        vp.setAdapter(myAdapter);
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            list.add(new HomeBean(i, "APP:"+i));
        }
        pageData=new PageData<HomeBean>(2, 5, list) {
            @Override
            public boolean areItemsTheSame(HomeBean oldData, HomeBean newData) {
                return oldData.hashCode() == newData.hashCode();
            }

            @Override
            public boolean areContentsTheSame(HomeBean oldData, HomeBean newData) {
                return oldData.name.equals(oldData.name);
            }

            @Override
            public Object getChangePayload(HomeBean oldData, HomeBean newData) {
                return newData.name;
            }

            @Override
            public int getDataPosition(List<HomeBean> allData, HomeBean newData) {
                return allData.indexOf(newData);
            }
        };
    }

    public void insert(View view) {
        int i=r.nextInt();
        pageData.insertData(0, new HomeBean(i, "APP:"+i));

    }

    public void remove(View view) {
        List<HomeBean> allData = pageData.getAllData();
        HomeBean remove = allData.get(r.nextInt(allData.size()));
        pageData.removeData(remove);
    }

    public void update(View view) {
        HomeBean testBean = pageData.getAllData().get(1);
        pageData.updateData(testBean, testBean.name);
//        int dataIndex = 100 + r.nextInt(100);
//        testBean.dataIndex = dataIndex;
//        pageData.updateData(testBean, dataIndex);
    }
}
