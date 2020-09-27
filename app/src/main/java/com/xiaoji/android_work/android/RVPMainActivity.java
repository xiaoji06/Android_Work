package com.xiaoji.android_work.android;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoji.android_work.BaseActivity;
import com.xiaoji.android_work.R;
import com.xiaoji.framework.utils.ScreenUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class RVPMainActivity extends BaseActivity implements PagingScrollHelper.onPageChangeListener {

    private RecyclerView recyclerView_time;
    private TimeAdapter timeAdapter;
    List<TimeBean> timeList = new ArrayList<>();

    private RecyclerView recyclerView_data;
    private HomeAdapter adapter;
    List<HomeBean> allList = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private int itemW;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rvp_main_activity);

        //计算item宽度
        int sw = ScreenUtils.getScreenWidth(getApplicationContext());
        itemW = (sw - ScreenUtils.dpToPx(getApplicationContext(), 152)) / 5;

        recyclerView_data = findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(this, 5);
        recyclerView_data.setLayoutManager(gridLayoutManager);
        adapter = new HomeAdapter(allList, itemW);
        recyclerView_data.setAdapter(adapter);
        recyclerView_data.setItemViewCacheSize(200);
        recyclerView_data.setHasFixedSize(true);
        recyclerView_data.setItemViewCacheSize(20);
        recyclerView_data.setDrawingCacheEnabled(true);
        recyclerView_data.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return allList.get(position).isHead ? 5 : 1;
            }
        });
        recyclerView_data.addItemDecoration(new GridItemDecoration(ScreenUtils.dpToPx(this, 10)));


        recyclerView_time = findViewById(R.id.recyclerView_time);
        recyclerView_time.setLayoutManager(new LinearLayoutManager(this));
        timeAdapter = new TimeAdapter(getApplicationContext(), R.layout.time_item2, timeList, itemW);
        recyclerView_time.setAdapter(timeAdapter);
        recyclerView_time.setItemViewCacheSize(20);
        recyclerView_time.setDrawingCacheEnabled(true);
        recyclerView_time.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        final RecyclerView.OnScrollListener[] scrollListeners = new RecyclerView.OnScrollListener[2];
        scrollListeners[0] = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                recyclerView_time.removeOnScrollListener(scrollListeners[1]);
                recyclerView_time.scrollBy(dx, dy);
                recyclerView_time.addOnScrollListener(scrollListeners[1]);
            }
        };
        scrollListeners[1] = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                recyclerView_data.removeOnScrollListener(scrollListeners[0]);
                recyclerView_data.scrollBy(dx, dy);
                recyclerView_data.addOnScrollListener(scrollListeners[0]);
            }
        };
        recyclerView_data.addOnScrollListener(scrollListeners[0]);
        recyclerView_time.addOnScrollListener(scrollListeners[1]);
        RVPMainActivityPermissionsDispatcher.initDataWithPermissionCheck(RVPMainActivity.this);
        initData();
    }

    private static final String[] SELECTION_ARGS = new String[]{
            "image/jpeg", "image/png", "image/webp"
    };

    private static final String SELECTION = String.format(
            " %s=? or %s=? or %s=?",
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.MIME_TYPE
    );

    private ArrayList<IMGViewModel> allImages=new ArrayList<>();
    private ArrayList<IMGViewModel> allModels=new ArrayList<>();

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    public void initData() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<Integer> emitter) throws Throwable {

                String  appCameraBasePath = FileUtil.getTempDirectoryPath(getApplicationContext())+ File.separator+"ANOAHCAMERA";
                String  appCameraBasePath2=FileUtil.getSDTempDirectoryPath(getApplicationContext())+ File.separator+"ANOAHCAMERA";

                Log.e("xxxx","appCameraBasePath:"+appCameraBasePath+"   appCameraBasePath2:"+appCameraBasePath2);

                LinkedHashMap<String, List<IMGViewModel>> images = new LinkedHashMap<>();

                ContentResolver contentResolver = getApplicationContext().getContentResolver();

                Cursor imageCursor = contentResolver.query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{
                                MediaStore.Images.Media.DATA,
                                MediaStore.Images.Media.SIZE
                        },
                        appCameraBasePath2+SELECTION, SELECTION_ARGS,
                        MediaStore.Images.Media.DATE_MODIFIED + " desc"
                );
                if (imageCursor == null) {
                    //
                }
                while (imageCursor.moveToNext()) {
                    long size = imageCursor.getLong(
                            imageCursor.getColumnIndex(MediaStore.Images.Media.SIZE)
                    );

                    String path = imageCursor.getString(
                            imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)
                    );

                    if (size == 0 || TextUtils.isEmpty(path)) {
                        continue;
                    }

                    File file = new File(path);
                    if (file.exists()) {
                        IMGViewModel model = new IMGViewModel(Uri.fromFile(file),IMGViewModel.TYPE_IMAGE);
                        model.setSize(size);
                        File parentFile = file.getParentFile();
                        if (parentFile != null && parentFile.exists()) {
                            String folderName = file.getParentFile().getName();
                            List<IMGViewModel> infos = images.get(folderName);
                            if (infos == null) {
                                infos = new ArrayList<>();
                                images.put(folderName, infos);
                            }
                            infos.add(model);
                            allImages.add(model);
                            allModels.add(model);
                        }
                    }
                }

                emitter.onNext(1);
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {

                    }
                });
        Random random = new Random(10);
        allList.clear();
        timeList.clear();
        int j = 0;
        timeList.add(new TimeBean("time", true));
        for (int i = 0; i < 1000; i++) {
            j++;
            if (random.nextInt(30) < 5) {
                allList.add(new HomeBean(i, "APP" + i, false));
                allList.add(new HomeBean(i, "APP" + i, true));
                timeList.add(new TimeBean("time" + i, true));
                j = 0;
            } else {
                allList.add(new HomeBean(i, "APP" + i, false));
                if (j == 6) {
                    timeList.add(new TimeBean("time" + i, false));
                    j = 0;
                }
            }
        }
        adapter.notifyDataSetChanged();
        timeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageChange(int index) {

    }

    public static class GridItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public GridItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = space;
        }
    }

    public static class TimeAdapter extends BaseQuickAdapter<TimeBean, BaseViewHolder> {

        private final int itemW;
        private final int dp10;
        private final int dp20;

        public TimeAdapter(Context context, int layoutResId, List<TimeBean> data, int itemW) {
            super(layoutResId, data);
            this.itemW = itemW;
            dp10 = ScreenUtils.dpToPx(context, 10);
            dp20 = ScreenUtils.dpToPx(context, 20);
        }

        @Override
        protected void convert(BaseViewHolder holder, TimeBean timeBean) {
            View view = holder.itemView;
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            if (timeBean.isHead) {
                holder.getView(R.id.point).setVisibility(View.VISIBLE);
                layoutParams.height = itemW + dp20 + dp10;
            } else {
                holder.getView(R.id.point).setVisibility(View.INVISIBLE);
                layoutParams.height = itemW + dp10;
            }
            view.setLayoutParams(layoutParams);
        }
    }
}
