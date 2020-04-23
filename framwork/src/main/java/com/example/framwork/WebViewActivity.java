package com.example.framwork;

import android.content.Intent;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.framwork.base.QuickActivity;
import com.example.framwork.utils.StatusBarUtil;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;


/**
 * Created by gaoy on 2017/1/21.
 * webview
 */

public class WebViewActivity extends QuickActivity {
    protected AgentWeb mAgentWeb;
    private String webUrl;
    private String webTitle;
    private int titleBg;
    private int backBtnRes;
    private boolean isTranslucentStatus;//是否沉浸式


    @Override
    protected boolean isLoadDefaultTitleBar() {
        return true;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.web_view_ui;
    }


    @Override
    protected void initViewsAndEvents() {
        if (titleBg == R.color.white) {
            actionBar.setConterTextColor(R.color.black);
        }
        if (backBtnRes != 0) {
            actionBar.getLeftRes().setImageResource(backBtnRes);
        }
        setActionBarTitle(webTitle);
        FrameLayout fbWb = findViewById(R.id.fb_wb);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(fbWb, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(webUrl);
    }

    @Override
    protected int setActionBarBackground() {
        return titleBg;
    }


    @Override
    protected boolean setStatusBarTrans() {
        return isTranslucentStatus;
    }

    @Override
    protected void setStatusBarView() {
        super.setStatusBarView();
        if (titleBg == R.color.white) {
            StatusBarUtil.setLightMode(mActivity);
        }
    }

    @Override
    protected void getIntentData(Intent intent) {
        if (intent != null) {
            webUrl = intent.getStringExtra("url");
            webTitle = intent.getStringExtra("title");
            titleBg = intent.getIntExtra("title_bg", 0);
            backBtnRes = intent.getIntExtra("back_res", 0);
            isTranslucentStatus = intent.getBooleanExtra("status_bar", false);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }


    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
