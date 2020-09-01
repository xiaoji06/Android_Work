package com.xiaoji.android_work.java.proxy;

public class BbFactory implements WomanToolsFactory {
    @Override
    public void saleWomanTools(float length) {
        System.out.println("B工厂生产："+length+"");
    }
}
