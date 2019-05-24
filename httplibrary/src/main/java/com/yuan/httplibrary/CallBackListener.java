package com.yuan.httplibrary;

import java.io.InputStream;

interface CallBackListener {
    void onSuccess(InputStream inputStream);

    void onFailed(String... error);
}
