package com.user.kaoguan.common;

import android.text.TextUtils;

import com.example.framwork.base.QuickActivity;
import com.user.kaoguan.AppApplication;
import com.user.kaoguan.model.CommonInfo;
import com.user.kaoguan.model.UserInfo;

import org.greenrobot.eventbus.Subscribe;

public abstract class BaseActivity extends QuickActivity {
    protected UserInfo userInfo;
    protected BaseHelperClass baseHelperClass;
    protected CommonInfo commonInfo;
    protected AppApplication mApplication;

    @Override
    protected void initPUserInfo() {
        mApplication = (AppApplication) getApplication();
        userInfo = mApplication.getUserInfo();
        commonInfo = mApplication.getCommonInfo();
        baseHelperClass = new BaseHelperClass(this);
        baseHelperClass.setUserInfo(userInfo);
    }

    /**
     * 判断用户是否登录
     *
     * @return
     */
    public boolean isLogin(boolean needLogin) {
        if (userInfo == null || TextUtils.isEmpty(getUserId())) {
            if (needLogin)
                Goto.goLogin(mActivity);
            return false;
        }
        return true;
    }

    public String getUserId() {
        if (userInfo != null && userInfo.user_id != null && !TextUtils.isEmpty(userInfo.user_id)) {
            return userInfo.user_id;
        }
        return "";
    }


    @Override
    protected boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void onEventMainThread(String eb) {
        if (eb.equals(FusionType.EBKey.EB_LOGIN_SUCCESS) || eb.endsWith(FusionType.EBKey.EB_REFRESH_USER)) {
            if (mApplication == null)
                mApplication = (AppApplication) mActivity.getApplication();
            userInfo = mApplication.getUserInfo();
            baseHelperClass.setUserInfo(userInfo);
            loginRefreshView();
        } else if (eb.equals(FusionType.EBKey.EB_LOGOUT_SUCCESS)) {
            userInfo = null;
            baseHelperClass.setUserInfo(userInfo);
            logoutRefreshView();
        }
    }

    @Override
    protected void onDestroy() {
        if (baseHelperClass != null)
            baseHelperClass.dismissDialog();
        super.onDestroy();
    }

//    public boolean isWifiConnect() {
//        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//        NetworkInfo mWifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        return mWifiInfo.isConnected();
//
//    }
}
