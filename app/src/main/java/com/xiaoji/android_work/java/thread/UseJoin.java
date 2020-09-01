package com.xiaoji.android_work.java.thread;

public class UseJoin {
	
    static class JumpQueue implements Runnable {
        private Thread thread;//用来插队的线程

        public JumpQueue(Thread thread) {
            this.thread = thread;
        }

        public void run() {
        	try {
        		System.out.println(thread.getName()+" will be join before "
        				+Thread.currentThread().getName());
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	System.out.println(Thread.currentThread().getName()+" terminted.");
        }
    }

    public static void main(String[] args) throws Exception {
        Thread previous = Thread.currentThread();//现在是主线程
        for (int i = 0; i < 10; i++) {
            //i=0,previous 是主线程，i=1;previous是i=0这个线程
            Thread thread =
                    new Thread(new JumpQueue(previous), String.valueOf(i));
//            System.out.println(previous.getName()+" jump a queue the thread:"
//                    +thread.getName());
            thread.start();
            previous = thread;
        }

        Thread.sleep(2);//让主线程休眠2秒
        System.out.println(Thread.currentThread().getName() + " terminate.");
    }
}
