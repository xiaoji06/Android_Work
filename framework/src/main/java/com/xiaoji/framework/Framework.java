package com.xiaoji.framework;

public class Framework {

    private volatile static Framework framework;

    private Framework(){

    }

    public static Framework getFramework(){
        if (framework==null){
            synchronized (Framework.class){
                if (framework==null){
                    framework = new Framework();
                }
            }
        }
        return framework;
    }
}
