package com.user.kaoguan;

import com.example.framwork.BaseApplication;
import com.example.framwork.mvp.CustomRequest;
import com.example.framwork.noHttp.NetworkConfig;
import com.example.framwork.utils.DLog;
import com.user.kaoguan.common.AppConfig;
import com.user.kaoguan.common.ResponseBean;
import com.user.kaoguan.listener.FilterExecuteListener;
import com.user.kaoguan.model.CommonInfo;
import com.user.kaoguan.model.UserInfo;
import com.yanzhenjie.nohttp.Logger;


public class AppApplication extends BaseApplication {
    public UserInfo userInfo;
    private CommonInfo commonInfo;


    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public CommonInfo getCommonInfo() {
        return commonInfo;
    }

    public void setCommonInfo(CommonInfo commonInfo) {
        this.commonInfo = commonInfo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppConfig.init(this, getPackageName());
        AppConfig.initServerSpServices();
        DLog.setIsLog(BuildConfig.DEBUG);
        Logger.setDebug(BuildConfig.DEBUG);
        CustomRequest.setConfig(NetworkConfig.newBuilder().filterExecuteLinstener(new FilterExecuteListener())
                .isEncryption(false).reponseClass(ResponseBean.class).build());
    }
}
