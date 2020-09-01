package com.xiaoji.android_work.android;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;

import java.util.ArrayList;

import static androidx.recyclerview.widget.RecyclerView.HORIZONTAL;

public class TouchConflictActivity extends BaseActivity {

    RecyclerView rlv;
    RecyclerRefreshActivity.ListAdapter adapter=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_conflict);
        rlv=findViewById(R.id.rlv);
        rlv.setLayoutManager(new LinearLayoutManager(this, HORIZONTAL,false));
        ArrayList<String> data=new ArrayList();
        for (int i=1;i<=20;i++){
            data.add("sfsdfsdf:"+i);
        }
        adapter=new RecyclerRefreshActivity.ListAdapter(R.layout.recycler_item);
        adapter.addData(data);
        rlv.setAdapter(adapter);
    }

}
