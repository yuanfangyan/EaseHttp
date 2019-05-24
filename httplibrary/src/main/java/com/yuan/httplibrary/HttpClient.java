package com.yuan.httplibrary;

public class HttpClient {

    public static <T, M> void setHttp(String url, T requestData, Class<M> response, IHttpResponse iResponse) {
        IHttpRequest httpRequest = new HttpRequest();
        CallBackListener callBackListener = new JsonCallBackListener(response,iResponse);
        HttpTask httpTask = new HttpTask(url, requestData, httpRequest, callBackListener);
        ThreadPoolManager.getInstance().addTask(httpTask);
    }
}
