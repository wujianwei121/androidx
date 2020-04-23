package com.user.kaoguan.common;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.framwork.adapter.CommonQuickAdapter;
import com.example.framwork.utils.SuperRecyclerViewUtils;
import com.example.framwork.widget.LoadDataLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.user.kaoguan.R;

import butterknife.BindView;

public abstract class BaseRecyclerViewActivity extends BaseTitleActivity implements OnItemClickListener {

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.loaddata_layout)
    LoadDataLayout loaddataLayout;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    protected SuperRecyclerViewUtils recyclerViewUtils;
    protected CommonQuickAdapter mAdapter;

    @Override
    protected void initViewsAndEvents() {
        initRecycler();
    }

    public void initRecycler() {
        initAdapter();
        mAdapter.setOnItemClickListener(this);
        recyclerViewUtils = new SuperRecyclerViewUtils(mActivity, rvContent, refreshLayout, loaddataLayout, mAdapter, initCallBack());
        recyclerViewUtils.setEmptyRes(setEmptyTxt(), setEmptyRes());
        loaddataLayout.setAllPageBackgroundColor(setLoaddataLayoutBackground());
        setRecyclerLayoutManager();
        recyclerViewUtils.setLoginRes(R.color.white, R.drawable.btn_login_bg);
        recyclerViewUtils.setLoginCall(() -> Goto.goLogin(mActivity));
        recyclerViewUtils.call();
    }

    protected void setRecyclerLayoutManager() {
        recyclerViewUtils.setRecyclerViewForList(mActivity, 0, 0);
    }

    protected abstract void initAdapter();

    protected int setLoaddataLayoutBackground() {
        return R.color.white;
    }

    protected int setEmptyRes() {
        return 0;
    }

    protected String setEmptyTxt() {
        return "空空如也~";
    }

    @Override
    public void loginRefreshView() {
        recyclerViewUtils.logout();
    }

    @Override
    public void logoutRefreshView() {
        recyclerViewUtils.call();
    }

    protected abstract SuperRecyclerViewUtils.CallBack initCallBack();
}
