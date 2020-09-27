package com.xiaoji.android_work.android;

import java.io.Serializable;
import java.util.ArrayList;

public class TimeBean implements Serializable {
    public String time;
    public boolean isHead;

    public TimeBean(String time, boolean isHead) {
        this.time = time;
        this.isHead = isHead;
    }
}
