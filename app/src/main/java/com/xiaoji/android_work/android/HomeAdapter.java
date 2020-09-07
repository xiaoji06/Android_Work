package com.xiaoji.android_work.android;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoji.android_work.R;

import java.util.List;

class HomeAdapter extends BaseQuickAdapter<HomeBean, BaseViewHolder> {
    public HomeAdapter(int layoutResId, List<HomeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeBean homeBean) {
        baseViewHolder.setText(R.id.name,homeBean.name);
    }
}
