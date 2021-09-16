package com.xiaoji.android_work.android;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;
import com.xiaoji.android_work.ScreenUtils;
import com.xiaoji.android_work.android.view.FillingRecyclerView;
import com.xiaoji.android_work.android.view.FrameLayout1;
import com.xiaoji.android_work.android.view.FrameLayout2;
import com.xiaoji.android_work.android.view.TouchUtils;
import com.xiaoji.android_work.android.view.View3;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class TouchActivity extends BaseActivity {

    private FrameLayout1 frameLayout1;
    private FrameLayout2 frameLayout2;
    private View3 view3;
    private FillingRecyclerView recyclerView;
    private ArrayList<String> data = new ArrayList<>();

    private ArrayList<RecyclerView.ViewHolder> mAttachedScrapL;
    private ArrayList<RecyclerView.ViewHolder> mChangedScrapL;
    private ArrayList<RecyclerView.ViewHolder> mCachedViewsL;
    private List<RecyclerView.ViewHolder> mUnmodifiableAttachedScrapL;
    private RecyclerView.RecycledViewPool mRecyclerPoolL;
    private SparseArray<Object> mScrapL;
    private ArrayList<RecyclerView.ViewHolder> mScrapHeapL;
    private ArrayList<RecyclerView.ViewHolder> map = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);

        frameLayout1=findViewById(R.id.vg1);
        frameLayout2=findViewById(R.id.vg2);
        view3=findViewById(R.id.v3);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager =  new LinearLayoutManager(this){
            //RecyclerView滑动速度的设置
            @Override
            public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
                int a = super.scrollVerticallyBy((int)(1.0*dy), recycler, state);//屏蔽之后无滑动效果，证明滑动的效果就是由这个函数实现
                if(a == (int)(1.0*dy)){
                    return dy;
                }
                return a;
            }
        };
        Log.e("xxx","xxxx mMaxFlingVelocity:" + recyclerView.getMaxFlingVelocity());
        recyclerView.setflingScale(0.46);
        //layoutManager.setItemPrefetchEnabled(false);
        recyclerView.setLayoutManager(layoutManager);
//        try{
//            Field field = recyclerView.getClass().getDeclaredField("mMaxFlingVelocity");
//            field.setAccessible(true);
//            field.set(recyclerView, 2000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        //((GridLayoutManager)recyclerView.getLayoutManager()).setInitialPrefetchItemCount(7);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0,20);
        recyclerView.setItemViewCacheSize(8);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new RecyclerView.Adapter<Holder>() {
            @NonNull
            @Override
            public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Holder holder = new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_recyclerview,parent,false));
//                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                //map.add(holder);
                Log.e("xxx","onCreateViewHolder : " + holder.toString());
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull Holder holder, int position) {
                Log.e("xxx", " position:"+position + " onBindViewHolder : " + holder.toString());
                holder.textView.setBackgroundColor((position%2==0)?Color.RED:Color.GREEN);
                holder.textView.setText(data.get(position));
            }

            @Override
            public int getItemCount() {
                return data.size();
            }

        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                getMessage();
            }
        });

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                getMessage();
            }
        },1000);

        view3.post(new Runnable() {
            @Override
            public void run() {
                Log.e("xxx", " post start: -----------------------");
                for (int i=0;i<20;i++){
                        Holder holder = (Holder) recyclerView.getAdapter().createViewHolder(recyclerView, 0);
                        recyclerView.getRecycledViewPool().putRecycledView(holder);
                    }
                Log.e("xxx", " post end: -------------------------");
            }
        });

        frameLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.clear();
                for (int i=0;i<1000;i++){
                    data.add("第 " + i + "个");
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metric);
        int width = metric.widthPixels; // 宽度（PX）
        int height = metric.heightPixels; // 高度（PX）
        float density = metric.density;
        float scaledDensity = metric.scaledDensity;
        int densityDpi = metric.densityDpi;

        Log.e("screen:","xxxxxxxxx width:"+width+"height"+height+"density"+density+"scaledDensity"+scaledDensity+"densityDpi"+densityDpi
                +"  screen size:"+(Math.sqrt((height*height + width*width))/densityDpi));

        TimeUnit unit;
        BlockingQueue workQueue;
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(, , , unit, workQueue);

        SpannableString spanString = new SpannableString("课堂视频录制完成，请前往我的云盘>视频查看");
        //构造一个改变字体颜色的Span
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#1295FA"));
        //将这个Span应用于指定范围的字体
        spanString.setSpan(span, 12, 19, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置给TextView显示出来
        ((TextView)findViewById(R.id.tv)).setText(spanString);
//        frameLayout1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("touch","frameLayout1:setOnClickListener:");
//            }
//        });
//
//        frameLayout1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.e("touch","frameLayout1:setOnTouchListener:");
//                return true;
//            }
//        });


    }

    private void getMessage(){
        try {
            Class recyclerViewClass = RecyclerView.class;
            Field mRecycler = recyclerViewClass.getDeclaredField("mRecycler");
            mRecycler.setAccessible(true);
            RecyclerView.Recycler recycler = (RecyclerView.Recycler) mRecycler.get(recyclerView);

            Class recyclerClass =  RecyclerView.Recycler.class;
            Field mAttachedScrap = recyclerClass.getDeclaredField("mAttachedScrap");
            Field mChangedScrap = recyclerClass.getDeclaredField("mChangedScrap");
            Field mCachedViews = recyclerClass.getDeclaredField("mCachedViews");
            Field mUnmodifiableAttachedScrap = recyclerClass.getDeclaredField("mUnmodifiableAttachedScrap");
            Field mRecyclerPool = recyclerClass.getDeclaredField("mRecyclerPool");
            mAttachedScrap.setAccessible(true);
            mChangedScrap.setAccessible(true);
            mCachedViews.setAccessible(true);
            mUnmodifiableAttachedScrap.setAccessible(true);
            mRecyclerPool.setAccessible(true);
            mAttachedScrapL = (ArrayList<RecyclerView.ViewHolder>) mAttachedScrap.get(recycler);
            mChangedScrapL = (ArrayList<RecyclerView.ViewHolder>) mChangedScrap.get(recycler);
            mCachedViewsL = (ArrayList<RecyclerView.ViewHolder>) mCachedViews.get(recycler);
            mUnmodifiableAttachedScrapL = (List<RecyclerView.ViewHolder>) mUnmodifiableAttachedScrap.get(recycler);
            mRecyclerPoolL = (RecyclerView.RecycledViewPool) mRecyclerPool.get(recycler);

            Class recycledViewPoolClass =  RecyclerView.RecycledViewPool.class;
            Field mScrap = recycledViewPoolClass.getDeclaredField("mScrap");
            mScrap.setAccessible(true);
            mScrapL = (SparseArray<Object>) mScrap.get(mRecyclerPoolL);

            if (null != mScrapL && mScrapL.size()>0){
                Class scrapDataClass =  (mScrapL.valueAt(0)).getClass();
                Field mScrapHeap = scrapDataClass.getDeclaredField("mScrapHeap");
                mScrapHeap.setAccessible(true);
                mScrapHeapL = (ArrayList<RecyclerView.ViewHolder>) mScrapHeap.get(mScrapL.valueAt(0));
            }

            Log.e("xxx","xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx : ");
           // Log.e("xxx","mAttachedScrapL : " + getList(mAttachedScrapL));
           // Log.e("xxx","mChangedScrapL : " + getList(mChangedScrapL));
            Log.e("xxx","size: " + mCachedViewsL.size() + "   mCachedViewsL : " + getList(mCachedViewsL));
           // Log.e("xxx","mRecyclerPoolL : " + mRecyclerPoolL.getRecycledViewCount(0));
//            if (max < mScrapHeapL.size()){
//                max = mScrapHeapL.size();
//            }
            Log.e("xxx","size: " + mRecyclerPoolL.getRecycledViewCount(0) + "  mScrapHeapL : " + getList(mScrapHeapL));
           // Log.e("xxx","xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx : ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private String getList(List<RecyclerView.ViewHolder> list) {
        if (null == list){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (RecyclerView.ViewHolder holder:list){
            sb.append(holder.toString()).append("\n");
        }
        return sb.toString();
    }

    public static class Holder extends RecyclerView.ViewHolder implements Cloneable{
        public TextView textView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }

        @NonNull
        @Override
        public Object clone(){
            Holder holder = null;
            try {
                holder = (Holder) super.clone();
            }catch (CloneNotSupportedException e){
                e.printStackTrace();
            }
            return holder;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // Log.e("touch","activity:dispatchTouchEvent:"+ TouchUtils.getEventString(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // Log.e("touch","activity:onTouchEvent:"+TouchUtils.getEventString(event));
        return super.onTouchEvent(event);
    }
}