package com.user.kaoguan.common;

import com.example.framwork.noHttp.Bean.BaseResponseBean;

public class ResponseBean extends BaseResponseBean {
    public int code;

    public String msg;

    public String data;
    public int success;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    @Override
    public boolean isSuccess() {
        return success == 1;
    }
}
