package com.example.coolweather.util;

/**
 * Created by WangFuyi on 2015/2/21.
 */
public interface HttpCallbackListener {

    void onFinish(String response);
    void onError(Exception e);

}
