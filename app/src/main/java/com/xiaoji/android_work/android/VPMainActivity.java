package com.xiaoji.android_work.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.BaseFragment;
import com.xiaoji.android_work.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;
import static androidx.viewpager.widget.PagerAdapter.POSITION_UNCHANGED;

public class VPMainActivity extends BaseActivity {

    ViewPager viewPager;
    Button add,remove;
    TextView page;
    HomePageAdapter adapter;
    List<BaseFragment> fragments=new ArrayList<>();
    VP2Fragment homeFirstFragment=null;
    List<HomeBean> allList=new ArrayList<>();
    LinkedHashMap<Integer,ArrayList<HomeBean>> pageList=new LinkedHashMap<>();
    int pageSize=8;
    HashMap<Integer,Integer> isNew=new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vp_main_activity);
        page=findViewById(R.id.page);
        viewPager=findViewById(R.id.vp);

        initData();
        viewPager.setOffscreenPageLimit(fragments.size());
        adapter=new HomePageAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page.setText("第  "+(position+1)+"  页");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<6;i++){
                    allList.add(new HomeBean(i, "APP"+ (1+allList.size())));
                }
                initPages(false);
                if (null!=adapter){
                    adapter.notifyDataSetChanged();
                    viewPager.setOffscreenPageLimit(fragments.size());
                }
                EventBus.getDefault().post(new MessageEvent());
            }
        });

        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index=allList.size()-1;
                for (int i=0;i<6;i++){
                    allList.remove(index);
                    index--;
                }
                initPages(false);
                if (null!=adapter){
                    adapter.notifyDataSetChanged();
                    viewPager.setOffscreenPageLimit(fragments.size());
                }
                EventBus.getDefault().post(new MessageEvent());
            }
        });

        findViewById(R.id.sort).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.shuffle(allList,new Random(900));
                initPages(false);
                if (null!=adapter){
                    adapter.notifyDataSetChanged();
                    viewPager.setOffscreenPageLimit(fragments.size());
                }
                EventBus.getDefault().post(new MessageEvent());
            }
        });
    }

    private void initData() {
        allList.clear();
        for (int i=0;i<40;i++){
            allList.add(new HomeBean(i, "APP"+ i));
        }

        fragments.clear();
        //初始化page
        initPages(true);
    }

    /**
     * 计算新的页数，对fragment进行有限的增加和删除
     * @param isInit 是否是初始化
     */
    private void initPages(boolean isInit){
        long start=System.nanoTime();
        //算出新的页数
        int page=1;
        int index=1;
        int oldPageSize=fragments.size();

        ArrayList<HomeBean> list=new ArrayList<>();
        VP1Fragment homeFragment=null;
        Bundle homeBundle=null;

        if (allList.size()==0){
            homeBundle=new Bundle();
            homeBundle.putSerializable("list",list);
            homeBundle.putInt("page",page);
            if (!isInit){
                fragments.get(0).setArguments(homeBundle);
                Log.e("aaaa","设置第一页");
            }else {
                homeFirstFragment=new VP2Fragment();
                homeFirstFragment.setUserVisibleHint(true);
                homeFirstFragment.setArguments(homeBundle);
                fragments.add(homeFirstFragment);
                isNew.put(homeFirstFragment.hashCode(),POSITION_UNCHANGED);
                Log.e("aaaa","设置第"+page+"页");
            }
        }else {
            for (int i=0;i<allList.size();i++){
                list.add(allList.get(i));
                index++;
                if (index==(pageSize+1)||i==(allList.size()-1)){
                    pageList.put(page,list);
                    if (page==1){
                        homeBundle=new Bundle();
                        homeBundle.putSerializable("list",list);
                        homeBundle.putInt("page",page);
                        if (!isInit){
                            fragments.get(0).setArguments(homeBundle);
                            Log.e("aaaa","设置第一页");
                        }else {
                            homeFirstFragment=new VP2Fragment();
                            homeFirstFragment.setUserVisibleHint(true);
                            homeFirstFragment.setArguments(homeBundle);
                            fragments.add(homeFirstFragment);
                            isNew.put(homeFirstFragment.hashCode(),POSITION_UNCHANGED);
                            Log.e("aaaa","设置第"+page+"页");
                        }
                    }else {
                        homeBundle=new Bundle();
                        homeBundle.putSerializable("list",list);
                        homeBundle.putInt("page",page);
                        if (page<=oldPageSize&&oldPageSize!=0){
                            homeFragment= (VP1Fragment) fragments.get(page-1);
                            homeFragment.setArguments(homeBundle);
                        }else {
                            homeFragment=new VP1Fragment();
                            homeFragment.setArguments(homeBundle);
                            fragments.add(homeFragment);
                            isNew.put(homeFragment.hashCode(),POSITION_UNCHANGED);
                        }
                    }
                    list=new ArrayList<>();
                    page++;
                    index=1;
                }
            }
            //如果页面减少了
            if ((page-1)<oldPageSize&&oldPageSize!=0){
                //移除相应页面
                for (int i=(fragments.size()-1);i>(page-2);i--){
                    isNew.put(fragments.get(i).hashCode(),POSITION_NONE);
                    fragments.remove(i);
                }
            }
        }
        long end=System.nanoTime();
        Log.e("change","initpage time="+(end-start));
    }

    class HomePageAdapter extends FragmentStatePagerAdapter {

        List<BaseFragment> fragments=null;

        public HomePageAdapter(@NonNull FragmentManager fm,List<BaseFragment> fragments) {
            super(fm);
            this.fragments=fragments;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {

            return isNew.get(object.hashCode());
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
