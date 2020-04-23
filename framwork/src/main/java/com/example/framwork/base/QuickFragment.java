package com.example.framwork.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.framwork.R;
import com.example.framwork.noHttp.CallServer;
import com.example.framwork.utils.DLog;
import com.example.framwork.utils.DialogUtils;
import com.example.framwork.widget.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;


public abstract class QuickFragment extends Fragment {
    protected FragmentActivity mActivity = null;
    protected KProgressHUD progressHUD;
    /**
     * butterknife 8+ support
     */
    private Unbinder mUnbinder;

    /**
     * databinding
     */
    public View mainLayout;
    /**
     * Screen information
     */
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;
    protected Dialog oneBtnDialog;
    protected Dialog twoBtnDialog;
    /**
     * 标记已加载完成，保证懒加载只能加载一次
     */
    private boolean hasLoaded = false;
    /**
     * 标记Fragment是否已经onCreate
     */
    private boolean isCreated = false;
    /**
     * 界面对于用户是否可见
     */
    private boolean isVisibleToUser = false;
    private View view;

    /**
     * 此方法可以得到上下文对象
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
        if (isUseEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        mActivity = getActivity();
        initPUserInfo();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getContentViewLayoutID();
        if (layoutId != 0) {
            mainLayout = inflater.inflate(layoutId, container, false);
            mainLayout.setClickable(true);
            mUnbinder = ButterKnife.bind(this, mainLayout);
            init(mainLayout, savedInstanceState);
            return mainLayout;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    public void init(View view, Bundle savedInstanceState) {
        isCreated = true;
        this.view = view;
        initViewsAndEvents(this.view, savedInstanceState);
        lazyLoad(this.view, savedInstanceState);
    }

    /**
     * 懒加载方法，获取数据什么的放到这边来使用，在切换到这个界面时才进行网络请求
     */
    private void lazyLoad(View view, Bundle savedInstanceState) {

        //如果该界面不对用户显示、已经加载、fragment还没有创建，
        //三种情况任意一种，不获取数据
        if (isLazyLoad() && (!isVisibleToUser || hasLoaded || !isCreated)) {
            return;
        }
        lazyInit(view, savedInstanceState);
        //注：关键步骤，确保数据只加载一次
        hasLoaded = true;
    }

    /**
     * 子类必须实现的方法，这个方法里面的操作都是需要懒加载的
     */
    public abstract void lazyInit(View view, Bundle savedInstanceState);

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected boolean isLazyLoad() {
        return true;
    }

    //ViewPage 会调用
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        //注：关键步骤
        this.isVisibleToUser = menuVisible;
        lazyLoad(view, null);
    }


    protected void initPUserInfo() {
    }

    protected void setStatusBar() {
    }

    protected abstract void initViewsAndEvents(View view, Bundle savedInstanceState);

    /**
     * 初始化layout
     */
    protected abstract int getContentViewLayoutID();

    public void logoutRefreshView() {
    }

    public void loginRefreshView() {
    }

    public void showProgress(Boolean isCancel, String hint) {
        if (progressHUD == null) {
            progressHUD = KProgressHUD.create(mActivity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.5f);
        }
        if (!TextUtils.isEmpty(hint)) {
            progressHUD.setLabel(hint);
        } else {
            progressHUD.setLabel("拼命加载...");
        }
        progressHUD.setCancellable(isCancel);
        progressHUD.show();
    }

    public void showProgress() {
        showProgress(true, "");
    }

    public void showProgress(String hint) {
        showProgress(true, hint);
    }

    public void showProgress(boolean isCancel) {
        showProgress(isCancel, "");
    }

    public void hideProgress() {
        if (progressHUD != null) {
            progressHUD.dismiss();
        }
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(View view) {
        InputMethodManager mInputMethodManager =
                (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        mInputMethodManager
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected boolean isShowSoft() {
        InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        return inputMethodManager.isActive();
    }

    /**
     * 关闭软键盘
     */
    protected void closeSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        //如果软键盘已经开启
        if (inputMethodManager.isActive() && mActivity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 切换软键盘的状态
     */
    protected void toggleSoftKeyboardState() {
        ((InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE)).toggleSoftInput(
                InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 打开软键盘
     */
    protected void showSoftInput(View view) {
        InputMethodManager mInputMethodManager =
                (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        mInputMethodManager
                .showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    @Override
    public void onDestroyView() {
        if (isUseEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        dismissQuickDialog();
        isCreated = false;
        hasLoaded = false;
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        hideProgress();
        CallServer.getRequestInstance().cancelBySign(this);
        if (isUseEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    /**
     * 是否使用 EventBus
     *
     * @return
     */
    protected boolean isUseEventBus() {
        return false;
    }

    /**
     * 提示错误信息
     *
     * @param errorMsg
     */
    public void toastError(String errorMsg) {
        Toasty.error(mActivity, errorMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示基本信息
     *
     * @param errorMsg
     */
    public void toastInfo(String errorMsg) {
        Toasty.info(mActivity, errorMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示成功信息
     *
     * @param errorMsg
     */
    public void toastSuccess(String errorMsg) {
        Toasty.success(mActivity, errorMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示警告信息
     *
     * @param errorMsg
     */
    public void toastWarning(String errorMsg) {
        Toasty.warning(mActivity, errorMsg, Toast.LENGTH_SHORT).show();
    }

    protected void showTwoBtnDialog(String title, String leftTxt, String rightTxt, final QuickActivity.IDialogListener listener) {
        showTwoBtnDialog("", title, leftTxt, rightTxt, 0, 0, 0, 0, 0, 0, false, listener, null);
    }

    protected void showTwoBtnDialog(String title, String leftTxt, String rightTxt, View.OnClickListener listener) {
        showTwoBtnDialog("", title, leftTxt, rightTxt, 0, 0, 0, 0, 0, 0, false, null, listener);
    }

    protected void showTwoBtnDialog(String tips, String title, String leftTxt, String rightTxt, int leftTxtColor, int rightTxtColor, int leftBgColor, int rightBgColor, int tipsColor, int contentColor, boolean isShowTips, final QuickActivity.IDialogListener listener, final View.OnClickListener clickListener) {
        if (twoBtnDialog == null) {
            twoBtnDialog = DialogUtils.getInstance().getCenterDialog(mActivity, R.layout.dialog_two_btn);
            TextView txtTitle = twoBtnDialog.findViewById(R.id.txt_title);
            TextView txtTips = twoBtnDialog.findViewById(R.id.txt_tips);
            Button btnLeft = twoBtnDialog.findViewById(R.id.btn_left);
            Button btnRight = twoBtnDialog.findViewById(R.id.btn_right);
            if (clickListener != null) {
                btnLeft.setOnClickListener(clickListener);
                btnRight.setOnClickListener(clickListener);
            }
            if (isShowTips) {
                if (tipsColor != 0) {
                    txtTips.setTextColor(ContextCompat.getColor(mActivity, tipsColor));
                }
                if (!TextUtils.isEmpty(tips)) {
                    txtTips.setText(tips);
                }
                txtTips.setVisibility(View.VISIBLE);
            } else {
                txtTips.setVisibility(View.GONE);
            }

            if (contentColor != 0) {
                txtTitle.setTextColor(ContextCompat.getColor(mActivity, contentColor));
            }
            if (leftTxtColor != 0) {
                btnLeft.setTextColor(ContextCompat.getColor(mActivity, leftTxtColor));
            }
            if (leftTxtColor != 0) {
                btnLeft.setTextColor(ContextCompat.getColor(mActivity, leftTxtColor));
            }
            if (leftBgColor != 0) {
                btnLeft.setBackgroundResource(leftBgColor);
            }
            if (rightTxtColor != 0) {
                btnRight.setTextColor(ContextCompat.getColor(mActivity, rightTxtColor));
            }
            if (rightBgColor != 0) {
                btnRight.setBackgroundResource(rightBgColor);
            }
            txtTitle.setText(title);
            btnLeft.setText(leftTxt);
            btnRight.setText(rightTxt);
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.leftClick();
                    }
                    dismissQuickDialog();
                }
            });
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.rightClick();
                    }
                    dismissQuickDialog();
                }
            });
        }
        twoBtnDialog.show();
    }

    protected void showOneBtnDialog(String tips, String title, String btn, int btnTxtColor, int btnBgColor, int tipsColor, int contentColor, boolean isShowTips, final boolean isCancel, final QuickActivity.IOneDialogListener listener) {
        if (oneBtnDialog == null) {
            oneBtnDialog = DialogUtils.getInstance().getCenterDialog(mActivity, isCancel, R.layout.dialog_one_btn);
            TextView txtTitle = oneBtnDialog.findViewById(R.id.txt_title);
            TextView txtTips = twoBtnDialog.findViewById(R.id.txt_tips);
            Button btnRight = oneBtnDialog.findViewById(R.id.btn_ok);
            if (isShowTips) {
                if (tipsColor != 0) {
                    txtTips.setTextColor(ContextCompat.getColor(mActivity, tipsColor));
                }
                if (!TextUtils.isEmpty(tips)) {
                    txtTips.setText(tips);
                }
                txtTips.setVisibility(View.VISIBLE);
            } else {
                txtTips.setVisibility(View.GONE);
            }
            if (contentColor != 0) {
                txtTitle.setTextColor(ContextCompat.getColor(mActivity, contentColor));
            }
            if (btnBgColor != 0) {
                btnRight.setBackgroundResource(btnBgColor);
            }
            if (btnTxtColor != 0) {
                btnRight.setTextColor(ContextCompat.getColor(mActivity, btnTxtColor));
            }
            txtTitle.setText(title);
            btnRight.setText(btn);
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.clickLisenter();
                    }
                    dismissQuickDialog();
                }
            });
            oneBtnDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if (!isCancel) {
                        mActivity.finish();
                    }
                }
            });
        }
        oneBtnDialog.show();
    }


    public interface IDialogListener {
        void rightClick();

        void leftClick();
    }

    public interface IOneDialogListener {
        void clickLisenter();
    }

    public void dismissQuickDialog() {
        if (oneBtnDialog != null && oneBtnDialog.isShowing()) {
            oneBtnDialog.dismiss();
        }
        if (twoBtnDialog != null && twoBtnDialog.isShowing()) {
            twoBtnDialog.dismiss();
        }
    }

    public void setDrawableLeft(@DrawableRes int icon, TextView view) {
        Drawable drawable = getResources().getDrawable(
                icon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        view.setCompoundDrawables(drawable, null, null, null);
    }

    public void setDrawableRight(@DrawableRes int icon, TextView view) {
        Drawable drawable = getResources().getDrawable(
                icon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        view.setCompoundDrawables(null, null, drawable, null);
    }

    public void setDrawableNull(TextView view) {
        view.setCompoundDrawables(null, null, null, null);
    }

}
