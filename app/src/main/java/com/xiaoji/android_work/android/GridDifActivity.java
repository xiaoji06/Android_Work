package com.xiaoji.android_work.android;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;

import java.util.ArrayList;
import java.util.List;

public class GridDifActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> data;
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_grid_dif);

        recyclerView=findViewById(R.id.list);
        data=new ArrayList<>();
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("5");

        adapter=new MyAdapter(R.layout.item_grid_dif,data);
        GridLayoutManager manager=new GridLayoutManager(this,6);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position<3){
                    return 2;
                }
                return 3;
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }

    public class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

        public MyAdapter(int layoutResId, List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, String s) {
            baseViewHolder.setText(R.id.text,s);
        }
    }
}
