package com.xiaoji.android_work.android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.BaseFragment;
import com.xiaoji.android_work.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class VPMainActivity extends BaseActivity {

    ViewPager viewPager;
    Button add,remove;
    TextView page;
    HomePageAdapter adapter;
    List<BaseFragment> fragments=new ArrayList<>();
    VP2Fragment homeFirstFragment=null;
    List<HomeBean> allList=new ArrayList<>();
    LinkedHashMap<Integer,ArrayList<HomeBean>> pageList=new LinkedHashMap<>();
    int pageSize=4;

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
                for (int i=0;i<2;i++){
                    allList.add(new HomeBean("APP"+ (1+allList.size())));
                }
                initPage();
                if (null!=adapter){
                    adapter.notifyDataSetChanged();
                    viewPager.setOffscreenPageLimit(fragments.size());
                }
            }
        });

        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index=allList.size()-1;
                for (int i=0;i<2;i++){
                    allList.remove(index);
                    index--;
                }
                initPage();
                if (null!=adapter){
                    adapter.notifyDataSetChanged();
                    viewPager.setOffscreenPageLimit(fragments.size());
                }
            }
        });
    }

    private void initData() {
        allList.clear();
        for (int i=0;i<20;i++){
            allList.add(new HomeBean("APP"+ i));
        }

        homeFirstFragment=new VP2Fragment();
        fragments.clear();
        //page
        initPage();
    }

    private void initPage() {
        int page=1;
        int index=1;
        int oldSize=fragments.size();
        ArrayList<HomeBean> list=new ArrayList<>();
        VP1Fragment homeFragment=null;
        Bundle homeBundle=null;
        for (int i=0;i<allList.size();i++){
            list.add(allList.get(i));
            index++;
            if (index==(pageSize+1)||i==(allList.size()-1)){
                pageList.put(page,list);
//                if (page==1){
//                    homeBundle=new Bundle();
//                    homeBundle.putSerializable("list",list);
//                    homeBundle.putInt("page",1);
//                    homeFirstFragment.setArguments(homeBundle);
//                    fragments.add(homeFirstFragment);
//                }else {
//                    homeFragment=new VP1Fragment();
//                    homeBundle=new Bundle();
//                    homeBundle.putSerializable("list",list);
//                    homeBundle.putInt("page",page);
//                    homeFragment.setArguments(homeBundle);
//                    fragments.add(homeFragment);
//                }
                list=new ArrayList<>();
                page++;
                index=1;
            }
        }
        if (oldSize!=0){
            if (oldSize>page){//页面减少
                int dis=oldSize-page;
                for (int i=0;i<dis;i++){
                    fragments.remove(page+i);
                }
            }
        }else{

        }
    }

    class HomePageAdapter extends FragmentPagerAdapter {

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
        public long getItemId(int position) {
            return this.fragments.get(position).hashCode();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }
}
