package com.example.framwork.noHttp;

import android.content.Context;

import com.example.framwork.noHttp.Bean.BaseResponseBean;

import java.util.List;

public interface FilterExecuteLinstener {
    //根据code分别处理
    boolean filterOperation(Context context, BaseResponseBean bean, boolean isLogin, int code);

    //用于SuperRecyclerViewUtils或其他页面登录过期处理
    boolean logoutOfDate(BaseResponseBean bean, int code);//用于SuperRecyclerViewUtils或其他页面登录过期处理
}