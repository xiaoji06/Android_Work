package com.xiaoji.android_work.android;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoji.android_work.R;
import com.xiaoji.framework.utils.ScreenUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.PrimitiveIterator;

class HomeAdapter extends BaseMultiItemQuickAdapter<HomeBean, BaseViewHolder> implements VP2Fragment.ItemTouchHelperAdapter {

    private List<HomeBean> data;
    private int itemW;
    public static int SPACE=1;
    public static int DATA=2;

    public HomeAdapter(int layoutResId, List<HomeBean> data) {
        super(data);
        this.data=data;
    }

    public HomeAdapter(List<HomeBean> data,int itemW) {
        super(data);
        this.data=data;
        this.itemW=itemW;
        // 绑定 layout 对应的 type
        addItemType(DATA, R.layout.home_item);
        addItemType(SPACE, R.layout.home_item3);
    }

    private boolean canInitView;

    public boolean isCanInitView() {
        return canInitView;
    }

    public void setCanInitView(boolean canInitView) {
        this.canInitView = canInitView;
    }

    @Override
    protected void convert(BaseViewHolder holder, HomeBean homeBean) {
            if (holder.getItemViewType()==DATA){
                ImageView imageView=holder.getView(R.id.img);
                FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) imageView.getLayoutParams();
                if (layoutParams.height==itemW){return;}
                layoutParams.height=itemW;
                layoutParams.width=itemW;
                imageView.setLayoutParams(layoutParams);
            }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (null != data) {
            //交换位置
            Collections.swap(data, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }
    }
}
