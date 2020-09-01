package com.xiaoji.android_work.android;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.VirtualLayoutManager.LayoutParams;
import com.alibaba.android.vlayout.layout.ColumnLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.sunfusheng.marqueeview.MarqueeView;
import com.xiaoji.android_work.R;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.BannerUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class VLayoutActivity extends Activity {

    private static final boolean BANNER_LAYOUT = true;

    private static final boolean FIX_LAYOUT = true;

    private static final boolean ONEN_LAYOUT = true;

    private static final boolean COLUMN_LAYOUT = true;

    private static final boolean GRID_LAYOUT = true;

    private static final boolean STICKY_LAYOUT = true;

    private static final boolean STAGGER_LAYOUT = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlayout);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        final VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(4, 4, 4, 4);
            }
        };
        recyclerView.addItemDecoration(itemDecoration);


        final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

        recyclerView.setRecycledViewPool(viewPool);

        viewPool.setMaxRecycledViews(0, 20);

        layoutManager.setRecycleOffset(300);

        final DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);

        recyclerView.setAdapter(delegateAdapter);

        final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

        if (BANNER_LAYOUT) {
            adapters.add(new SubAdapter(this, new LinearLayoutHelper(),R.layout.vlayout_banner,1){
                @Override
                public void onViewRecycled(BaseViewHolder holder) {
                    if (holder.itemView instanceof Banner) {
                        ((Banner) holder.itemView).setAdapter(null);
                    }
                }

                @Override
                public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    if (viewType == 1)
                        return new BaseViewHolder(
                                LayoutInflater.from(VLayoutActivity.this).inflate(R.layout.vlayout_banner, parent, false));

                    return super.onCreateViewHolder(parent, viewType);
                }

                @Override
                public int getItemViewType(int position) {
                    return 1;
                }

                @Override
                public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                    ArrayList<Integer> data=new ArrayList<>();
                    data.add(R.mipmap.aaa);
                    data.add(R.mipmap.wenzi);
                    data.add(R.mipmap.app_icon);

                    Banner banner = (Banner) holder.getView(R.id.banner);
                    //默认直接设置adapter就行了
                    banner.setAdapter(new BannerActivity.ImageAdapter(data));

                    //--------------------------详细使用-------------------------------
                    banner.setIndicator(new CircleIndicator(VLayoutActivity.this));
                    banner.setIndicatorSelectedColorRes(R.color.colorPrimary);
                    banner.setIndicatorNormalColorRes(R.color.colorAccent);
                    banner.setIndicatorGravity(IndicatorConfig.Direction.LEFT);
                    banner.setIndicatorSpace((int) BannerUtils.dp2px(20));
                    banner.setIndicatorMargins(new IndicatorConfig.Margins((int) BannerUtils.dp2px(10)));
                    banner.setIndicatorWidth(10,20);
                    banner.start();
                }
            });
        }


        {
            GridLayoutHelper helper = new GridLayoutHelper(5);
            helper.setAspectRatio(5f);
            helper.setPadding(0, 20, 0, 0);
            helper.setVGap(10);//垂直间距
            helper.setHGap(0);//水平间距
            adapters.add(new SubAdapter(this, helper, R.layout.vlayout_menu,10){

                @Override
                public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    if (viewType == 2)
                        return new BaseViewHolder(
                                LayoutInflater.from(VLayoutActivity.this).inflate(R.layout.vlayout_menu, parent, false));

                    return super.onCreateViewHolder(parent, viewType);
                }

                @Override
                public int getItemViewType(int position) {
                    return 2;
                }

                @Override
                public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
                    //应用
                    String[] ITEM_NAMES = {"天猫", "聚划算", "天猫国际", "外卖", "天猫超市", "充值中心", "飞猪旅行", "领金币", "拍卖", "分类"};
                    int[] IMG_URLS = {R.mipmap.ic_tian_mao, R.mipmap.ic_ju_hua_suan, R.mipmap.ic_tian_mao_guoji,
                            R.mipmap.ic_waimai, R.mipmap.ic_chaoshi, R.mipmap.ic_voucher_center,
                            R.mipmap.ic_travel, R.mipmap.ic_tao_gold, R.mipmap.ic_auction, R.mipmap.ic_classify};
                    holder.setText(R.id.tv_menu_title_home, ITEM_NAMES[position] + "");
                    holder.setImageResource(R.id.iv_menu_home, IMG_URLS[position]);
                    holder.getView(R.id.ll_menu_home).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), ITEM_NAMES[position],
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    super.onBindViewHolder(holder, position);
                }
            });
        }


        {
            adapters.add(new SubAdapter(this, new LinearLayoutHelper(),R.layout.vlayout_news,1){
                @Override
                public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    if (viewType == 3)
                        return new BaseViewHolder(
                                LayoutInflater.from(VLayoutActivity.this).inflate(R.layout.vlayout_news, parent, false));

                    return super.onCreateViewHolder(parent, viewType);
                }

                @Override
                public int getItemViewType(int position) {
                    return 3;
                }
                @Override
                public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                    MarqueeView marqueeView1 = holder.getView(R.id.marqueeView1);
                    MarqueeView marqueeView2 = holder.getView(R.id.marqueeView2);

                    List<String> info1 = new ArrayList<>();
                    info1.add("天猫超市最近发大活动啦，快来抢");
                    info1.add("没有最便宜，只有更便宜！");

                    List<String> info2 = new ArrayList<>();
                    info2.add("这个是用来搞笑的，不要在意这写小细节！");
                    info2.add("啦啦啦啦，我就是来搞笑的！");

                    marqueeView1.startWithList(info1);
                    marqueeView2.startWithList(info2);
                    // 在代码里设置自己的动画
                    marqueeView1.startWithList(info1, R.anim.anim_bottom_in, R.anim.anim_top_out);
                    marqueeView2.startWithList(info2, R.anim.anim_bottom_in, R.anim.anim_top_out);

                    marqueeView1.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position, TextView textView) {
                            Toast.makeText(getApplicationContext(), textView.getText().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    marqueeView2.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position, TextView textView) {
                            Toast.makeText(getApplicationContext(), textView.getText().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        if (STICKY_LAYOUT) {
            StickyLayoutHelper layoutHelper = new StickyLayoutHelper();
            //layoutHelper.setOffset(100);
            layoutHelper.setAspectRatio(4);
            adapters.add(new SubAdapter(this, layoutHelper, R.layout.item,1){
                @Override
                public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                    holder.setText(R.id.title,""+position);
                }
            });
        }

        if (COLUMN_LAYOUT) {
            ColumnLayoutHelper layoutHelper = new ColumnLayoutHelper();
            layoutHelper.setWeights(new float[]{40f, 30f,20f,10f});
            adapters.add(new SubAdapter(this, layoutHelper, R.layout.item,4) {

                @Override
                public void onBindViewHolder(BaseViewHolder holder, int position) {
                    LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
                    holder.itemView.setLayoutParams(layoutParams);
                    holder.setText(R.id.title,""+position);
                }

            });
        }

        if (ONEN_LAYOUT) {
            OnePlusNLayoutHelper helper = new OnePlusNLayoutHelper();
            helper.setBgColor(0xffef8ba3);
            helper.setAspectRatio(1.8f);
            //helper.setColWeights(new float[]{40f});
            //helper.setRowWeight(50f);
            helper.setMargin(10, 20, 10, 20);
            helper.setPadding(10, 10, 10, 10);
            adapters.add(new SubAdapter(this, helper, R.layout.item, 4) {
                @Override
                public void onBindViewHolder(BaseViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                    holder.setText(R.id.title,""+position);
                }
            });
        }

        if (ONEN_LAYOUT) {
            adapters.add(new SubAdapter(this, new OnePlusNLayoutHelper(), R.layout.item, 0));
            OnePlusNLayoutHelper helper = new OnePlusNLayoutHelper();
            helper.setBgColor(0xff87e543);
            helper.setAspectRatio(1.8f);
            //helper.setColWeights(new float[]{40f});
            helper.setMargin(10, 20, 10, 20);
            //helper.setPadding(10, 10, 10, 10);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            adapters.add(new SubAdapter(this, helper, R.layout.item, 3) {
                @Override
                public void onBindViewHolder(BaseViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                    holder.setText(R.id.title,""+position);
                }
            });
        }


        if (GRID_LAYOUT) {
            GridLayoutHelper helper = new GridLayoutHelper(4);
            helper.setAspectRatio(4f);
            helper.setMargin(0, 10, 0, 10);
            helper.setGap(10);
            adapters.add(new SubAdapter(this, helper, R.layout.item,80) {
                @Override
                public void onBindViewHolder(BaseViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                    holder.setText(R.id.title,""+position);
                }
            });
        }

        if (STAGGER_LAYOUT) {
            // adapters.add(new SubAdapter(this, new StaggeredGridLayoutHelper(2, 0), 0));
            final StaggeredGridLayoutHelper helper = new StaggeredGridLayoutHelper(2, 10);
            helper.setMargin(20, 10, 10, 10);
            helper.setPadding(10, 10, 20, 10);
            helper.setBgColor(0xFF86345A);
            adapters.add(new SubAdapter(this, helper,R.layout.item, 27) {
                @Override
                public void onBindViewHolder(BaseViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                    LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
                    if (position % 2 == 0) {
                        layoutParams.mAspectRatio = 1.0f;
                    } else {
                        layoutParams.height = 340 + position % 7 * 20;
                    }
                    holder.itemView.setLayoutParams(layoutParams);
                    holder.setText(R.id.title,""+position);
                }
            });
        }

        if (FIX_LAYOUT) {
            adapters.add(new SubAdapter(this, new ScrollFixLayoutHelper(20, 20), R.layout.item,1) {
                @Override
                public void onBindViewHolder(BaseViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                    LayoutParams layoutParams = new LayoutParams(200, 200);
                    holder.itemView.setLayoutParams(layoutParams);
                    holder.setText(R.id.title,""+position);
                }
            });
        }


        delegateAdapter.setAdapters(adapters);

        setListenerToRootView();
    }

    boolean isOpened = false;

    public void setListenerToRootView() {
        final View activityRootView = getWindow().getDecorView().findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > 100) { // 99% of the time the height diff will be due to a keyboard.
                    if (isOpened == false) {
                        //Do two things, make the view top visible and the editText smaller
                    }
                    isOpened = true;
                } else if (isOpened == true) {
                    isOpened = false;
                    final RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });
    }


    static class SubAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {
        private Context mContext;

        private LayoutHelper mLayoutHelper;

        private int mLayoutId;
        private int mCount = 0;

        public SubAdapter(Context context, LayoutHelper layoutHelper,int layoutId, int count) {
            this.mContext = context;
            this.mLayoutHelper = layoutHelper;
            this.mLayoutId=layoutId;
            this.mCount = count;
        }

        @Override
        public LayoutHelper onCreateLayoutHelper() {
            return mLayoutHelper;
        }

        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(mLayoutId, parent,
                    false));
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mCount;
        }
    }

}
