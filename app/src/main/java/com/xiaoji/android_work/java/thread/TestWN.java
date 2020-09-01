package com.xiaoji.android_work.java.thread;
public class TestWN {
    private static Express express = new Express(0,Express.CITY);

    /*检查里程数变化的线程,不满足条件，线程一直等待*/
    private static class CheckKm extends Thread{
        @Override
        public void run() {
            express.waitKm();
        }
    }

    /*检查地点变化的线程,不满足条件，线程一直等待*/
    private static class CheckSite extends Thread{
        @Override
        public void run() {
            express.waitSite();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<3;i++){//三个线程,等待快递到达地点的变化
            new CheckSite().start();
        }
        for(int i=0;i<3;i++){//三个线程,等待里程数的变化
            new CheckKm().start();
        }

        Thread.sleep(1000);
        express.changeKm();//快递里程数变化
    }
}

class Express {
    public final static String CITY = "ShangHai";
    private int km;/*快递运输里程数*/
    private String site;/*快递到达地点*/

    public Express() {
    }

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    /* 变化公里数，然后通知处于wait状态并需要处理公里数的线程进行业务处理*/
    public synchronized void changeKm(){
    	this.km = 101;
        notify();
    }

    /* 变化地点，然后通知处于wait状态并需要处理地点的线程进行业务处理*/
    public synchronized void changeSite(){
		this.site = "BeiJing";
		notify();
    }

    public synchronized void waitKm(){
		while(this.km<100){
            try {
                wait();
                System.out.println("check km thread["
                        +Thread.currentThread().getName()+"] is be notifyed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    	System.out.println("the km is"+this.km+",I will change db.");

    }

    public synchronized void waitSite(){
        while(CITY.equals(this.site)){
            try {
                wait();
                System.out.println("check site thread["
                        +Thread.currentThread().getName()+"] is be notifyed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    	System.out.println("the site is"+this.site+",I will call user.");
    }
}
