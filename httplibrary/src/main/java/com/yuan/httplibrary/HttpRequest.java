package com.yuan.httplibrary;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequest implements IHttpRequest {

    private String url;
    private byte[] bytes;
    private CallBackListener callBackListener;

    private HttpURLConnection connection;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public void setListener(CallBackListener listener) {
        this.callBackListener = listener;
    }

    @Override
    public void execute() {
        URL url = null;
        try {
            url = new URL(this.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000);//连接超时
            connection.setUseCaches(false);//是否缓存
            connection.setInstanceFollowRedirects(true);//是成员变量，进作用当前函数，设置当前这个连接是否可以重新定向
            connection.setReadTimeout(6000);//响应超时
            connection.setDoInput(true);//设置这个连接对是否可以写入数据
            connection.setDoOutput(true);//设置这个连接对是否可以输出数据
            connection.setRequestMethod("POST");//设置连接方式
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");//设置编码
            connection.connect();//建立连接，实际上是建立一个与服务器的TCP连接
            //使用字节流发送数据
            OutputStream outputStream = connection.getOutputStream();
            //缓存字节，包装字节流
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            //将字节写入缓冲区
            bos.write(bytes);
            //刷新缓冲区，发送数据
            bos.flush();
            outputStream.flush();
            bos.close();
            outputStream.close();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                callBackListener.onSuccess(inputStream);
            }else {
                throw new RuntimeException("请求失败");
            }

        } catch (Exception e) {
            throw new RuntimeException("请求失败");
        }finally {
            connection.disconnect();
        }
    }
}
