package com.user.kaoguan.common;

import com.example.framwork.baseapp.BaseAppConfig;
import com.user.kaoguan.BuildConfig;


/**
 * Created by lenovo on 2017/2/22.
 */

public class AppConfig extends BaseAppConfig {
    public static final int SERVER_TYPE_OLINE = 2;
    public static final int SERVER_TYPE = BuildConfig.SERVER_TYPE;

    /**
     * 必须在Application初始化  为服务器地址赋值
     */
    public static void initServerSpServices() {
        if (SERVER_TYPE == SERVER_TYPE_OLINE)
            SERVICE_PATH = "http://api.live.yunduopu.com/?service=";
        else
            SERVICE_PATH = "http://api.agxn.renxixi.com/";
    }
}
