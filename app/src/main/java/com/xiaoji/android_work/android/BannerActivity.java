package com.xiaoji.android_work.android;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.transformer.DepthPageTransformer;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends BaseActivity implements OnBannerListener, OnPageChangeListener {
    Banner banner = null;
    ArrayList<Integer> data=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        //--------------------------简单使用-------------------------------
        //创建（new banner()）或者布局文件中获取banner
        banner = (Banner) findViewById(R.id.banner);
        data.add(R.mipmap.aaa);
        data.add(R.mipmap.wenzi);
        data.add(R.mipmap.app_icon);
        //默认直接设置adapter就行了
        banner.setAdapter(new ImageAdapter(data));

        //--------------------------详细使用-------------------------------
        banner.setIndicator(new CircleIndicator(this));
        banner.setIndicatorSelectedColorRes(R.color.colorPrimary);
        banner.setIndicatorNormalColorRes(R.color.colorAccent);
        banner.setIndicatorGravity(IndicatorConfig.Direction.LEFT);
        banner.setIndicatorSpace((int) BannerUtils.dp2px(20));
        banner.setIndicatorMargins(new IndicatorConfig.Margins((int) BannerUtils.dp2px(10)));
        banner.setIndicatorWidth(10,20);
        banner.setOnBannerListener(this);
        banner.addOnPageChangeListener(this);
        banner.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stop();
    }


    /**
     * 自定义布局，下面是常见的图片样式，更多实现可以看demo，可以自己随意发挥
     */
    static class ImageAdapter extends BannerAdapter<Integer, ImageAdapter.BannerViewHolder> {

        public ImageAdapter(List<Integer> mDatas) {
            //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
            super(mDatas);
        }

        //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
        @Override
        public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(parent.getContext());
            //注意，必须设置为match_parent，这个是viewpager2强制要求的
            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return new BannerViewHolder(imageView);
        }

        @Override
        public void onBindView(BannerViewHolder holder, Integer data, int position, int size) {
            holder.imageView.setImageResource(data);
        }

        class BannerViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public BannerViewHolder(@NonNull ImageView view) {
                super(view);
                this.imageView = view;
            }
        }
    }

    @Override
    public void OnBannerClick(Object data, int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
