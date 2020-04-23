package com.user.kaoguan.listener;

import android.content.Context;

import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.FilterExecuteLinstener;

/**
 * @author LackAi
 * @Date 2017/11/27 11:11
 */

public class FilterExecuteListener implements FilterExecuteLinstener {

    @Override
    public boolean filterOperation(Context context, BaseResponseBean bean, boolean isLogin, int code) {
        return false;
    }

    @Override
    public boolean logoutOfDate(BaseResponseBean bean, int code) {
        return false;
    }
}
