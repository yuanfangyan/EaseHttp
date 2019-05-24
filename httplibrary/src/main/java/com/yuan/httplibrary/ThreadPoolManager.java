package com.yuan.httplibrary;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
    private static ThreadPoolManager instance = new ThreadPoolManager();
    //线程池
    private Executor executor;
    //线程队列
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();
    //重试队列
    private DelayQueue<HttpTask> delayQueue = new DelayQueue<>();

    public static ThreadPoolManager getInstance() {
        return instance;
    }

    private ThreadPoolManager() {
        executor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                //将拒绝的线程重新加入队列
                mQueue.add(r);
            }
        });
        executor.execute(coreTask);
        executor.execute(delayTask);
    }

    public void addTask(Runnable runnable) {
        try {
            if (runnable != null) {
                mQueue.put(runnable);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addDelayTask(HttpTask task) {
        if (task != null) {
            task.setDelayTime(3000);//设置重试时间间隔
            delayQueue.put(task);
        }
    }

    //不断的从线程池中取任务执行
    private Runnable coreTask = new Runnable() {
        Runnable take = null;

        @Override
        public void run() {
            while (true) {
                try {
                    take = mQueue.take();
                    executor.execute(take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    };

    private Runnable delayTask = new Runnable() {
        HttpTask task = null;

        @Override
        public void run() {
            try {
                task = delayQueue.take();
                if (task.getDelayCount() < 3) {
                    executor.execute(task);
                    task.setDelayCount(task.getDelayCount() + 1);
                    Log.e( "重试机制：",task.getDelayCount() + "==" + System.currentTimeMillis() );
                }else {
                    Log.e( "重试机制：","重试机制超过三次");

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
