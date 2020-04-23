package com.user.kaoguan.ui;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import com.example.framwork.baseapp.AppManager;
import com.example.framwork.bean.TabEntity;
import com.example.framwork.utils.StatusBarUtil;
import com.example.framwork.widget.tablayout.CommonTabLayout;
import com.example.framwork.widget.tablayout.listener.CustomTabEntity;
import com.example.framwork.widget.tablayout.listener.OnTabSelectListener;
import com.user.kaoguan.R;
import com.user.kaoguan.common.BaseActivity;
import com.user.kaoguan.ui.home.presenter.MainPresenter;

import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tl_main)
    CommonTabLayout tlMain;
    private int[] mIconUnselectIds = {};
    private int[] mIconSelectIds = {};
    private String[] mTitles;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private MainPresenter presenter;


    @Override
    protected void getIntentData(Intent intent) {

    }

    @Override
    protected boolean isLoadDefaultTitleBar() {
        return false;
    }


    @Override
    protected void initViewsAndEvents() {
        NumberFormat cu=NumberFormat.getCurrencyInstance();
        presenter = new MainPresenter(mActivity, getSupportFragmentManager());
//        initTablayout();
        tlMain.setVisibility(View.GONE);
    }

    private void initTablayout() {
        mTitles = getResources().getStringArray(R.array.fragment_home_pass);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tlMain.setTabData(mTabEntities);
        tlMain.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                presenter.showGroup(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        presenter.showGroup(0);
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //System.currentTimeMillis()无论何时调用，肯定大于2000
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                toastNormal("再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                AppManager.getAppManager().finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }
}
