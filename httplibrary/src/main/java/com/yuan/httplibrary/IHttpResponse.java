package com.yuan.httplibrary;

public interface IHttpResponse<T> {
    void onResponseSuccess(T t);

    void onResponseFailed(String... error);

}
