package com.user.kaoguan.ui.home.presenter;

import android.content.Context;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.framwork.mvp.BasePresenter;
import com.user.kaoguan.R;
import com.user.kaoguan.ui.mine.MineFragment;
import com.user.kaoguan.ui.home.HomeFragment;

public class MainPresenter extends BasePresenter {
    private HomeFragment homeFragment;
    private MineFragment mineFragment;
    private HomeFragment rankingFragment;
    private HomeFragment rechargeFragment;
    private FragmentManager fragmentManager = null;


    public MainPresenter(Context context, FragmentManager fragmentManager) {
        super(context);
        this.fragmentManager = fragmentManager;
    }

    /**
     * 控制显示fragment
     */
    public void showGroup(int pos) {
        FragmentTransaction transaction;
        switch (pos) {
            case 0:
                //首页
                transaction = fragmentManager.beginTransaction();
                if (null == homeFragment) {
                    homeFragment = HomeFragment.getInstance();
                    transaction.add(R.id.fl_change, homeFragment);
                }
                if (null != mineFragment) {
                    transaction.hide(mineFragment);
                }
                if (null != rechargeFragment) {
                    transaction.hide(rechargeFragment);
                }
                if (null != rankingFragment) {
                    transaction.hide(rankingFragment);
                }
                transaction.show(homeFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 1:
                //首页
                transaction = fragmentManager.beginTransaction();
                if (null == rankingFragment) {
                    rankingFragment = HomeFragment.getInstance();
                    transaction.add(R.id.fl_change, rankingFragment);
                }
                if (null != mineFragment) {
                    transaction.hide(mineFragment);
                }
                if (null != rechargeFragment) {
                    transaction.hide(rechargeFragment);
                }
                if (null != homeFragment) {
                    transaction.hide(homeFragment);
                }
                transaction.show(rankingFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 2:
                transaction = fragmentManager.beginTransaction();
                if (null == rechargeFragment) {
                    rechargeFragment = HomeFragment.getInstance();
                    transaction.add(R.id.fl_change, rechargeFragment);
                }
                if (null != homeFragment) {
                    transaction.hide(homeFragment);
                }
                if (null != mineFragment) {
                    transaction.hide(mineFragment);
                }
                if (null != rankingFragment) {
                    transaction.hide(rankingFragment);
                }
                transaction.show(rechargeFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 3:
                transaction = fragmentManager.beginTransaction();
                if (null == mineFragment) {
                    mineFragment = MineFragment.getInstance();
                    transaction.add(R.id.fl_change, mineFragment);
                }
                if (null != homeFragment) {
                    transaction.hide(homeFragment);
                }
                if (null != rechargeFragment) {
                    transaction.hide(rechargeFragment);
                }
                if (null != rankingFragment) {
                    transaction.hide(rankingFragment);
                }
                transaction.show(mineFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

//    public void getConfig() {
//        requestInfo = BaseRequestInfo.getInstance().getRequestInfo(context, "App.Home.GetConfig", false);
//        post3NoLogin(new OnInterfaceRespListener<List<CommonInfo>>() {
//            @Override
//            public void requestSuccess(List<CommonInfo> bean) {
//                if (bean != null && bean.size() != 0)
//                    AccountManger.setCommonInfo(context, bean.get(0));
//            }
//        });
//    }
//
//    public void updateVersion(final CommonInfo commonInfo, boolean isShowToast) {
//        if (commonInfo != null && commonInfo.apk_ver != null && !TextUtils.isEmpty(commonInfo.apk_ver)) {
//            String curVersionStr = CommonUtil.getVersion(context);
//            String newVersionStr = commonInfo.apk_ver;
//            int curVersion;
//            int newVersion;
//            if (curVersionStr != null && curVersionStr.contains(".")) {
//                curVersion = Integer.valueOf(curVersionStr.replace(".", ""));
//            } else {
//                curVersion = Integer.valueOf(curVersionStr);
//            }
//            if (newVersionStr != null && newVersionStr.contains(".")) {
//                newVersion = Integer.valueOf(newVersionStr.replace(".", ""));
//            } else {
//                newVersion = Integer.valueOf(newVersionStr);
//            }
//            if (newVersion > curVersion) {
//                DownloadBuilder builder = AllenVersionChecker
//                        .getInstance()
//                        .downloadOnly(crateUIData(commonInfo));
//                builder.setCustomVersionDialogListener(createCustomDialogTwo(commonInfo));
//                if (commonInfo.is_force == 1) {
//                    builder.setForceUpdateListener(new ForceUpdateListener() {
//                        @Override
//                        public void onShouldForceUpdate() {
//                            AppManager.getAppManager().finishAllActivity();
//                        }
//                    });
//                }
//                builder.executeMission(context);
//            } else if (isShowToast) {
//                Toasty.success(context, "当前版本已是最新~").show();
//            }
//        }
//
//    }
//
//    private CustomVersionDialogListener createCustomDialogTwo(final CommonInfo commonInfo) {
//        return new CustomVersionDialogListener() {
//            @Override
//            public Dialog getCustomVersionDialog(Context context, UIData versionBundle) {
//                BaseDialog baseDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.custom_version_dialog);
//                TextView tvMsg = baseDialog.findViewById(R.id.tv_msg);
//                TextView tvVersion = baseDialog.findViewById(R.id.tv_version);
//                tvVersion.setText("V" + commonInfo.apk_ver);
//                tvMsg.setText(versionBundle.getContent());
//                if (commonInfo.is_force != 1) {
//                    baseDialog.setCanceledOnTouchOutside(true);
//                }
//                baseDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        if (commonInfo.is_force == 1) {
//                            AppManager.getAppManager().finishAllActivity();
//                        }
//                    }
//                });
//                return baseDialog;
//            }
//        };
//    }
//
//    private UIData crateUIData(CommonInfo commonInfo) {
//        UIData uiData = UIData.create();
//        uiData.setTitle("泥娃娃新版更新");
//        uiData.setDownloadUrl(commonInfo.apk_url);
//        uiData.setContent(commonInfo.apk_des);
//        return uiData;
//    }
}
