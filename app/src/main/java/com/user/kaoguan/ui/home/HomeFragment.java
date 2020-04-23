package com.user.kaoguan.ui.home;

import android.os.Bundle;
import android.view.View;

import com.user.kaoguan.common.BaseFragment;

public class HomeFragment extends BaseFragment {
    public static HomeFragment getInstance() {
        HomeFragment sf = new HomeFragment();
        return sf;
    }


    @Override
    public void lazyInit(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initViewsAndEvents(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

}
