package com.yuan.httplibrary;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonCallBackListener<T> implements CallBackListener {
    private Class<T> response;
    private IHttpResponse httpResponse;
    private Handler handler = new Handler(Looper.getMainLooper());

    public JsonCallBackListener(Class<T> response, IHttpResponse httpResponse) {
        this.response = response;
        this.httpResponse = httpResponse;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        String content = getContent(inputStream);
        final T t = JSON.parseObject(content, response);
        handler.post(new Runnable() {
            @Override
            public void run() {
                httpResponse.onResponseSuccess(t);
            }
        });
    }

    @Override
    public void onFailed(String... error) {
        httpResponse.onResponseFailed(error);
    }

    /**
     * InputStream转成String
     *
     * @param inputStream
     * @return
     */
    private String getContent(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line + "/n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return stringBuffer.toString().replace("/n", "");
    }
}
