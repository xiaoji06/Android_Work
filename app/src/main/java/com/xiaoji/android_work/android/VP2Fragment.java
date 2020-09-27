package com.xiaoji.android_work.android;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.xiaoji.android_work.BaseFragment;
import com.xiaoji.android_work.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class VP2Fragment extends BaseFragment {
    RecyclerView recyclerView;
    HomeAdapter adapter;
    ArrayList<HomeBean> list=new ArrayList<>();
    int page=-1;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e("aa","onAttach:"+"  page:"+page);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.clear();
        list.addAll((ArrayList<HomeBean>) getArguments().getSerializable("list"));
        page=getArguments().getInt("page");
        Log.e("aa","onCreate:"+"  page:"+page);
        EventBus.getDefault().register(this);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.vp_fragment, container, false);
        initView(mRootView);
        Log.e("aa","onCreateView:"+"  page:"+page);
        return mRootView;
    }

    private void initView(View mRootView) {
        recyclerView=mRootView.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        adapter=new HomeAdapter(R.layout.home_item,list);
        //先实例化Callback
        ItemTouchHelper.Callback callback = new MoveCallBack(adapter);
        //用Callback构造ItemtouchHelper
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        //调用ItemTouchHelper的attachToRecyclerView方法建立联系
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {


                return false;
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent event){
        long start=System.nanoTime();
        ArrayList<HomeBean> newList=(ArrayList<HomeBean>) getArguments().getSerializable("list");
        if (!list.equals(newList)){
            list.clear();
            list.addAll(newList);
            if (adapter!=null){
                adapter.notifyDataSetChanged();
            }
        }
        long end=System.nanoTime();
        Log.e("change","event: page:"+page+" time="+(end-start));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.e("aa","onDestroy:"+"  page:"+page);
    }

    public interface ItemTouchHelperAdapter {
        //数据交换
        void onItemMove(int fromPosition,int toPosition);
    }
    public static class MoveCallBack extends ItemTouchHelper.Callback{

        private HomeAdapter adapter;

        public MoveCallBack(HomeAdapter adapter){
            this.adapter=adapter;
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;        //允许上下的拖动
            int swipeFlags = -1;   //不允许侧滑
            return makeMovementFlags(dragFlags,swipeFlags);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            //onItemMove是接口方法
            adapter.onItemMove(viewHolder.getBindingAdapterPosition(),target.getBindingAdapterPosition());
            //viewHolder.itemView.setVisibility(View.VISIBLE);
            //target.itemView.setVisibility(View.VISIBLE);
            return true;
        }

        @Override
        public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
            super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
        }

        @Override
        public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

//            if(isCurrentlyActive){
//                //创建WindowManager
//                viewHolder.itemView.setVisibility(View.INVISIBLE);
//            }else {
//                viewHolder.itemView.setVisibility(View.VISIBLE);
//            }

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        }


    }
}
