package com.example.framwork.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.framwork.R;
import com.example.framwork.utils.DialogUtils;
import com.example.framwork.widget.kprogresshud.KProgressHUD;

import es.dmoral.toasty.Toasty;


public abstract class QuickPageView {
    protected AppCompatActivity mActivity;
    protected KProgressHUD progressHUD;
    protected View rootView;
    protected Dialog oneBtnDialog;
    protected Dialog twoBtnDialog;

    public QuickPageView(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
        initData();
        initView();

    }

    public View getRootView() {
        return rootView;
    }

    public abstract void initView();

    public abstract void initData();

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(EditText view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) mActivity.getSystemService(mActivity.INPUT_METHOD_SERVICE);
        mInputMethodManager
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 打开软键盘
     */
    protected void showSoftInput(EditText view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) mActivity.getSystemService(mActivity.INPUT_METHOD_SERVICE);
        mInputMethodManager
                .showSoftInput(view, InputMethodManager.SHOW_FORCED);
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
}

