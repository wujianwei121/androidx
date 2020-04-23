package com.user.kaoguan.common;

import android.content.Context;
import android.text.TextUtils;

import com.user.kaoguan.AppApplication;

import java.util.HashMap;

/**
 * @author LackAi
 * @Date 2017/12/9 14:45
 */

public class BaseRequestInfo {

    private static BaseRequestInfo instance;
    private AppApplication application;
    private Context context;

    public BaseRequestInfo(Context context) {
        this.context = context.getApplicationContext();
        application = (AppApplication) context.getApplicationContext();
    }

    /**
     * 创建一个单例类
     */
    public static BaseRequestInfo getInstance(Context context) {
        if (instance == null) {
            synchronized (BaseRequestInfo.class) {
                if (instance == null) {
                    instance = new BaseRequestInfo(context);
                }
            }
        }
        return instance;
    }

    /**
     * 公共请求参数
     *
     * @param needLogin 是否需要强制登录
     * @return
     */
    public HashMap getRequestInfo(String methodName, boolean needLogin) {
        HashMap<String, Object> info = new HashMap<>();
        info.clear();
        info.put("methodName", methodName);
        info.put("device", "2");
        info.put("device_id", AppConfig.DEVICE_NO);
        info.put("app_version", AppConfig.VERSION_NUM);
        if (needLogin) {
            if (application == null) {
                application = (AppApplication) context.getApplicationContext();
            }
            if (application.getUserInfo() != null && !TextUtils.isEmpty(application.getUserInfo().getUser_id())) {
                info.put("user_id", application.getUserInfo().user_id);
                info.put("token", application.getUserInfo().token);
            }

        }
        return info;
    }
}
