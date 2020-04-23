package com.example.framwork.noHttp;


import com.example.framwork.noHttp.Bean.BaseResponseBean;

/**
 * Created by lenovo on 2017/9/1.
 */

public abstract class OnInterfaceRespListener<T> implements OnRequestListener<T> {

    @Override
    public void requestFinish() {

    }

    @Override
    public void requestFailed(int errorCode, BaseResponseBean bean, Exception exception, String error) {

    }
}
