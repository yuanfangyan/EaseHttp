package com.yuan.easehttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yuan.httplibrary.HttpClient;
import com.yuan.httplibrary.IHttpResponse;

public class MainActivity extends AppCompatActivity {
    String url = "http://192.168.0.251:80";
//    String url = "http://192.168.0.251:8080/yiwei-service/api/checkPasswd?adminPasswd=999";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpClient.setHttp(url, null, Bean.class, new IHttpResponse<Bean>() {

            @Override
            public void onResponseSuccess(Bean bean) {
                Log.e("onResponseSuccess: ", bean.getResultMsg() + "==" + bean.getResultCode());
            }

            @Override
            public void onResponseFailed(String... error) {
                Log.e("onResponseFailed: ", error[0]);
            }
        });
    }
}
