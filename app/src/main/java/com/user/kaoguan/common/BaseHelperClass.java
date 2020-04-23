package com.user.kaoguan.common;

import android.app.Dialog;
import android.content.Context;

import com.user.kaoguan.model.UserInfo;


/**
 * Created by lenovo on 2018/7/3.
 */

public class BaseHelperClass {
    private Context mContext;
    private Dialog vipDialog;
    private UserInfo userInfo;

    public BaseHelperClass(Context mContext) {
        this.mContext = mContext;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

//    /**
//     * 是否认证切升级vip
//     */
//    public boolean isVip() {
//        if (userInfo == null || TextUtils.isEmpty(getUserId())) {
//            Goto.goLogin(mContext);
//            return false;
//        } else if (!userInfo.isVip()) {
//            if (userInfo.is_vip==1){
//                EventBus.getDefault().post(FusionType.EBKey.EB_REFRESH_USER);
//            }
//            if (vipDialog == null) {
//                vipDialog = DialogUtils.getInstance().getCenterDialog(mContext, R.layout.dialog_recharge);
//                ImageView ivClose = vipDialog.findViewById(R.id.iv_close);
//                TextView goRecharge = vipDialog.findViewById(R.id.btn_recharge);
//                goRecharge.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dismissDialog();
//                        Goto.goRecharge(mContext);
//                    }
//                });
//                ivClose.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dismissDialog();
//                    }
//                });
//            }
//            vipDialog.show();
//            return false;
//        } else
//            return true;
//    }
//
//
    public void dismissDialog() {
        if (vipDialog != null && vipDialog.isShowing()) {
            vipDialog.dismiss();
        }
    }
//
//    public String getUserId() {
//        if (userInfo != null && userInfo.user_id != null && !TextUtils.isEmpty(userInfo.user_id)) {
//            return userInfo.user_id;
//        }
//        return "";
//    }
}
