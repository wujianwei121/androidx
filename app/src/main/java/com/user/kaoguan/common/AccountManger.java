package com.user.kaoguan.common;

import android.content.Context;

import com.example.framwork.utils.SPUtils;
import com.user.kaoguan.AppApplication;
import com.user.kaoguan.model.CommonInfo;
import com.user.kaoguan.model.UserInfo;

/**
 * Created by lenovo on 2017/3/4.
 */

public class AccountManger {

    private static AccountManger instance;
    private AppApplication application;
    private Context context;

    public AccountManger(Context context) {
        this.context = context;
        application = (AppApplication) context.getApplicationContext();
    }

    /**
     * 创建一个单例类
     */
    public static AccountManger getInstance(Context context) {
        if (instance == null) {
            synchronized (AccountManger.class) {
                if (instance == null) {
                    instance = new AccountManger(context);
                }
            }
        }
        return instance;
    }

    public void getUserInfo() {
        if (SPUtils.getInstance().contains(context, FusionType.SPKey.USER_INFO)) {
            UserInfo userInfo = (UserInfo) SPUtils.getInstance().readObject(context, FusionType.SPKey.USER_INFO);
            if (userInfo == null) {
                userInfo = new UserInfo();
            }
            if (application == null) {
                application = (AppApplication) context.getApplicationContext();
            }
            application.setUserInfo(userInfo);
        }
    }

    public void setCommonInfo(CommonInfo commonInfo) {
        if (application == null) {
            application = (AppApplication) context.getApplicationContext();
        }
        application.setCommonInfo(commonInfo);
    }

    public void clearUserInfo(Context context) {
        SPUtils.getInstance().remove(context, FusionType.SPKey.USER_INFO);
        SPUtils.getInstance().remove(context, FusionType.SPKey.USER_ACCOUNT);
        if (application == null) {
            application = (AppApplication) context.getApplicationContext();
        }
        application.setUserInfo(null);

    }

    public void setUserInfo(UserInfo userInfo) {
        SPUtils.getInstance().saveObject(context, FusionType.SPKey.USER_INFO, userInfo);
        if (application == null) {
            application = (AppApplication) context.getApplicationContext();
        }
        application.setUserInfo(userInfo);
    }

    public void updateUserInfo(UserInfo userInfo) {
        if (application == null) {
            application = (AppApplication) context.getApplicationContext();
        }
        UserInfo oldInfo = application.getUserInfo();
        SPUtils.getInstance().saveObject(context, FusionType.SPKey.USER_INFO, oldInfo);
        application.setUserInfo(oldInfo);
    }


}
