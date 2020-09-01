package com.xiaoji.android_work.java.proxy;

public class AaFactory implements ManToolsFactory {
    @Override
    public void saleManTools(String size) {
        System.out.println("A工厂生产："+size+"");
    }
}
