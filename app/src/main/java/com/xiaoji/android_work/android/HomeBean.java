package com.xiaoji.android_work.android;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeBean implements Serializable, MultiItemEntity {

    public HomeBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public HomeBean(int id, String name,boolean isHead) {
        this.id = id;
        this.name = name;
        this.isHead=isHead;
    }

    public final int id;
    public String name;
    public boolean isHead;

    @Override
    public int getItemType() {
        return isHead?HomeAdapter.SPACE:HomeAdapter.DATA;
    }
}
