package com.xiaoji.android_work.android;

import android.net.Uri;

/**
 * Created by felix on 2017/12/26 上午11:21.
 */

public class IMGViewModel {
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 2;

    private int modelType = 0;

    private Uri uri;
    private String thumbPath;
    private long duration;
    private boolean isSelected;

    private boolean isOriginal;

    private int width;

    private int height;

    private long size;

    private int position;

    public IMGViewModel(Uri uri, int modelType) {
        this.uri = uri;
        this.modelType = modelType;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public boolean isOriginal() {
        return isOriginal;
    }

    public void setOriginal(boolean original) {
        isOriginal = original;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void toggleSelected() {
        isSelected = !isSelected;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getModelType() {
        return modelType;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
