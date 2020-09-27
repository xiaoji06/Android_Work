package com.xiaoji.android_work.android.launcher;

public interface ViewPagerHelper<T> extends IRelease {
    T getCurrentItem(int position);
}