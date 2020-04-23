package com.user.kaoguan.common;


import com.user.kaoguan.R;

public abstract class BaseTitleActivity extends BaseActivity {
    @Override
    protected boolean isLoadDefaultTitleBar() {
        return true;
    }

    @Override
    protected int setActionBarBackground() {
        return R.color.colorPrimary;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setActionBarTitle(getActionBarTitle());
    }


    protected abstract String getActionBarTitle();

    @Override
    protected boolean setStatusBarTrans() {
        return false;
    }
}
