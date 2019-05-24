package com.yuan.httplibrary;

interface IHttpRequest {
    void setUrl(String url);

    void setData(byte[] bytes);

    void setListener(CallBackListener listener);

    void execute();
}
