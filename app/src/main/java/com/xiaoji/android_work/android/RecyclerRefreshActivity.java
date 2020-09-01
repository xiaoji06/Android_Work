package com.xiaoji.android_work.android;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;

import java.util.ArrayList;

public class RecyclerRefreshActivity extends BaseActivity {
    RefreshLayout refreshLayout=null;
    RecyclerView recyclerView=null;
    ListAdapter adapter=null;

    ArrayList<String> data=new ArrayList();

    int j=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_refresh);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ListAdapter(R.layout.recycler_item);
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(R.layout.empty_view);

        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        //设置 Header 为 贝塞尔雷达 样式
        //refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        //设置 Footer 为 球脉冲 样式
        //refreshLayout.setRefreshFooter(new ClassicsFooter(this));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                data.clear();
                j=1;
//                for (int i=1;i<=20;i++){
//                    data.add("sfsdfsdf:"+j+""+i);
//                }
                adapter.setList(data);
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                j++;
                ArrayList<String> adddata=new ArrayList();
                for (int i=1;i<=10;i++){
                    adddata.add("sfsdfsdf:"+j+""+i);
                }
                adapter.addData(adddata);
                refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
            }
        });

        refreshLayout.autoRefresh(100);//延迟400毫秒后自动刷新
    }

    static class ListAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

        public ListAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, String s) {
                baseViewHolder.setText(R.id.text,s);
        }
    }
}
