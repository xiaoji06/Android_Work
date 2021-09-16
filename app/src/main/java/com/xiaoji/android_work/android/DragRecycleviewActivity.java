package com.xiaoji.android_work.android;

import android.app.Service;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DragRecycleviewActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> data = new ArrayList<>();
    private ItemTouchHelper myItemTouchHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_recycleview);
        initView();
        initData();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView){
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh,MotionEvent event) {
                showToast("点击了第 " + vh.getAbsoluteAdapterPosition()+ "个");
            }
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh,MotionEvent event) {
                if (checkArea(vh.itemView,R.id.v_drag,event)){
                    Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
                    vib.vibrate(70);
                    myItemTouchHelper.startDrag(vh);
                    showToast("开始拖拽第 " + vh.getAbsoluteAdapterPosition() + "个");
                }else {
                    showToast("长按了第 " + vh.getAbsoluteAdapterPosition() + "个");
                }
            }
        });
        myItemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallback());
        myItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private Toast myToast ;
    public void showToast(String msg) {
//        ToastUtils.show(msg);
        if (null == myToast){
            myToast =  Toast.makeText(this, null, Toast.LENGTH_SHORT);
            myToast.setText(msg);
        }else {
            if (!TextUtils.isEmpty(msg)){
                myToast.cancel();
                myToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
                myToast.setText(msg);
                myToast.setDuration(Toast.LENGTH_SHORT);
            }
        }
        myToast.show();
    }

    private void initData() {
        Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                data.clear();
                for (int i = 0; i < 30; i++) {
                    data.add("第 " + i + " 个");
                }
                return true;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Throwable {
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
    }

    private boolean checkArea(View parent,int id,MotionEvent e){
        View targetView = parent.findViewById(id);
        if (null != targetView){
            int[] location =new int[2];
            targetView.getLocationOnScreen(location);
            int left = location[0];
            int top = location[1];
            int right = left + targetView.getMeasuredWidth();
            int bottom = top + targetView.getMeasuredHeight();
            if ( e.getX() >= left && e.getX() <= right
                    && e.getY() >= top && e.getY() <= bottom){
                return true;
            }
        }
        return false;
    }

    public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback{

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int dragFlags = 0;
            int swipeFlags = 0;
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager){
                dragFlags = ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN |
                        ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT;
                swipeFlags = 0;
            } else {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN ;
                swipeFlags = 0;
            }
            return makeMovementFlags(dragFlags,swipeFlags);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            //得到拖动ViewHolder的position
            int fromPosition = viewHolder.getAbsoluteAdapterPosition();
            //得到目标ViewHolder的position
            int toPosition = target.getAbsoluteAdapterPosition();
            Collections.swap(data,fromPosition,toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);
            return true;
        }

        private Drawable originalBg;

        @Override
        public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
                originalBg = viewHolder.itemView.getBackground();
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackground(originalBg);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        }
    }

    public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener{

        private GestureDetectorCompat mGestureDetector;
        private RecyclerView recyclerView;

        public OnRecyclerItemClickListener(RecyclerView recyclerView){
            this.recyclerView = recyclerView;
            mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(),
                    new ItemTouchHelperGestureListener());
        }

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }

        public abstract void onItemClick(RecyclerView.ViewHolder vh,MotionEvent e);

        public abstract void onLongClick(RecyclerView.ViewHolder vh,MotionEvent e);

        private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View child  = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (null != child){
                    onItemClick(recyclerView.getChildViewHolder(child),e);
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child  = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (null != child){
                    onLongClick(recyclerView.getChildViewHolder(child),e);
                }
            }
        }
    }


    public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drag, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.name.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public View drag;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            drag = itemView.findViewById(R.id.v_drag);
        }
    }
}
