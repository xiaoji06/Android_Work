package com.xiaoji.android_work.android;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GridActivity extends BaseActivity {

    RecyclerView recyclerView;
    GridLayoutManager manager;
    GridAdapter adapter;
    GridItemDecoration itemDecoration;
    ArrayList<HomeBean> list=new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vp_fragment);
        recyclerView=findViewById(R.id.rv);
        manager=new GridLayoutManager(this,4){
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        adapter=new GridAdapter(R.layout.home_item,list);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        itemDecoration=new GridItemDecoration(0,0);
        recyclerView.addItemDecoration(itemDecoration);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0;i<30;i++){
                            list.add(new HomeBean(i,"app"+i+new Random().nextInt(999999999)));
                        }
                        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                Display defaultDisplay = getWindowManager().getDefaultDisplay();
                                Point point = new Point();
                                defaultDisplay.getSize(point);
                                int width = point.x;
                                int height = point.y;
                                Log.e("xxx", "width = " + width + ",height = " + height);
                                int itemW=manager.findViewByPosition(0).getMeasuredWidth();
                                int itemH=manager.findViewByPosition(0).getMeasuredHeight();
                                //280  168
                                Log.e("xxx","xxxxxxxxxxx width:"+itemW+"  height:"+itemH);
                                int ldes=(width-4*itemW)/4/3;
                                int bdes=(height-8*itemH)/7;
                                itemDecoration.setLdesAndBdes(ldes,bdes);
                                adapter.notifyDataSetChanged();
                            }
                        });
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();


    }

    public class GridItemDecoration extends RecyclerView.ItemDecoration{

        int ldes;
        int bdes;

        public GridItemDecoration(int ldes,int bdes) {
            this.ldes=ldes;
            this.bdes=bdes;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left=ldes*(parent.getChildAdapterPosition(view)%4);
            outRect.bottom=parent.getChildAdapterPosition(view)<28?bdes:0;
        }

        public void setLdesAndBdes(int ldes,int bdes){
            this.ldes=ldes;
            this.bdes=bdes;
        }
    }

    public class GridAdapter extends BaseQuickAdapter<HomeBean, BaseViewHolder>{

        public GridAdapter(int layoutResId, List<HomeBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, HomeBean homeBean) {
            baseViewHolder.setText(R.id.name,homeBean.name);
        }
    }
}
