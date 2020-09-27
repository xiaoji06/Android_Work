package com.xiaoji.android_work.android.launcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.xiaoji.android_work.R;
import com.xiaoji.android_work.android.HomeBean;
import com.xiaoji.android_work.android.launcher.adapter.DragPageAdapter;
import com.xiaoji.android_work.android.launcher.bean.PageData;

import java.util.List;

public class PageAdapter extends DragPageAdapter<HomeBean> {
    public PageAdapter(Context context, PageData<HomeBean> pageData) {
        super(context, pageData, true);
    }

    @Override
    public ItemAdapter generateItemRecyclerAdapter(List<HomeBean> pageData, int pageIndex) {
        return new ItemAdapter(pageData, pageIndex);
    }

    private class ItemAdapter extends DragPageAdapter<HomeBean>.ItemDragAdapter<HomeItemViewHolder> {

        ItemAdapter(List<HomeBean> list, int pageIndex) {
            super(list, pageIndex);
        }

        @Override
        public HomeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
            return new HomeItemViewHolder(inflate);
        }

        @Override
        public void onBindItemViewHolder(HomeItemViewHolder holder, int position) {
            HomeBean testBean = (HomeBean) data.get(position);
            holder.textView.setText("" + testBean.name);
        }

        @Override
        public long getStableItemId(int position) {
            return data.get(position).id;
        }

        @Override
        public int getPositionForId(long itemId) {
            int size = data.size();
            for (int i = 0; i < size; i++) {
                int positionItemId = data.get(i).id;
                if (positionItemId == itemId) {
                    return i;
                }
            }
            return RecyclerView.NO_POSITION;
        }
    }

    private static class HomeItemViewHolder extends DragViewHolder {

        private final TextView textView;

        HomeItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.time);
        }
    }
}
