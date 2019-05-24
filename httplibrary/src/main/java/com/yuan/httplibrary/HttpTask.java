package com.yuan.httplibrary;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class HttpTask<T> implements Runnable, Delayed {
    private IHttpRequest request;
    private int delayCount;//重试次数
    private long delayTime;//重试间隔

    public HttpTask(String url, T requestData, IHttpRequest request, CallBackListener listener) {
        this.request = request;
        request.setUrl(url);
        String content = JSON.toJSONString(requestData);
        try {
            request.setData(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        request.setListener(listener);
    }

    @Override
    public void run() {
        try {
            request.execute();
        } catch (Exception e) {
            ThreadPoolManager.getInstance().addDelayTask(this);
        }
    }

    public int getDelayCount() {
        return delayCount;
    }

    public void setDelayCount(int delayCount) {
        this.delayCount = delayCount;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime + System.currentTimeMillis();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
