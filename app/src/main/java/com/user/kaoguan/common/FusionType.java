package com.user.kaoguan.common;

/**
 * 类型管理
 *
 * @date 2017/8/26 上午10:41
 */
public class FusionType {

    //1 注册  2 忘记密码  3 绑定手机号
    public interface SMSType {
        int SMS_REGISTER = 1;
        int SMS_FORGET_PSD = 2;
        int SMS_BIND_PHONE = 3;
    }

    public interface SPKey {
        String USER_INFO = "user_info";
        String USER_ACCOUNT = "user_account";
        String COMMON_INFO = "common_info";
        String Video_Play_First = "video_first";
        String Vip_login_First = "vip_login_first";
    }

    public interface EBKey {
        String EB_LOGIN_SUCCESS = "EB_LOGIN_SUCCESS";
        //刷新个人中心
        String EB_REFRESH_USER = "EB_REFRESH_USER";
        String EB_LOGOUT_SUCCESS = "LOGOUT_SUCCESS";
    }

    public interface ComeWhere {
        int CW_HOTFILM = 1;
        int CW_VIDEO_CATE = 2;
        int CW_MAIN_ACTIVITY = 3;
    }
}
