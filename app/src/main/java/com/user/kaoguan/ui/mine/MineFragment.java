package com.user.kaoguan.ui.mine;

import android.os.Bundle;
import android.view.View;

import com.user.kaoguan.common.BaseFragment;

public class MineFragment extends BaseFragment {

    public static MineFragment getInstance() {
        MineFragment sf = new MineFragment();
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

    @Override
    public void loginRefreshView() {
    }


}
