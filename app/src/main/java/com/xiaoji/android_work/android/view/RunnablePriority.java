package com.xiaoji.android_work.android.view;

/**
 * 项目名称：NettyDemo
 * 类描述：
 * 创建人：liuwenxing
 * 创建时间：2019/3/1512:18
 * 修改人：liuwenxing
 * 修改时间：
 * 修改备注：
 */
public class RunnablePriority implements Runnable,Comparable<RunnablePriority>{
    private int priority;
    private String id;
    private long runTime;

    public int getPriority() {
        return priority;
    }
    public String getId() {
        return id;
    }
    public long getRunTime() {
        return runTime;
    }

    public RunnablePriority(int priority,long runTime,String id) {
        this.priority = priority;
        this.id = id;
        this.runTime = runTime;
    }
    @Override
    public int compareTo(RunnablePriority o) {
        // 复写此方法进行任务执行优先级排序
        if (this.getPriority() < o.priority) {
            return 1;
        }
        if(this.getPriority()>o.priority){
            return -1;
        }
        if(this.priority==o.priority){
            if(this.getRunTime() < o.runTime){
                return -1;
            }
            if(this.getRunTime()>o.runTime){
                return 1;
            }
        }
        return 0;
    }


    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() +"\t  "+Thread.currentThread().toString()+"  id:" +getId() +" 办理业务");
    }

}


