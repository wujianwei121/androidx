package com.example.framwork;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.framwork.base.QuickActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author LackAi
 * @Date 2017/12/22 10:02
 */

public abstract class CommonSplashActivity extends QuickActivity implements View.OnClickListener {

    protected ImageView mIvSplash;
    protected Button mBtnJump;
    private boolean isCanJump = false;
    private Timer mTimer = new Timer();
    private ISplashActivityCallBack mCallBack;
    private int mDuration;//动画时长

    public void setCallBack(ISplashActivityCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    protected void initViewsAndEvents() {
        mDuration = getDuration();
        mIvSplash = findViewById(R.id.welcome_img);
        mBtnJump = findViewById(R.id.btn_jump);
        mBtnJump.setOnClickListener(this);
    }

    @Override
    public void hideStatusBar() {
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }


    private void initSplash() {
        AlphaAnimation anima = new AlphaAnimation(0.3f, 1.0f);
        anima.setDuration(2000);// 设置动画显示时间
        mIvSplash.startAnimation(anima);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    mDuration--;
                    mBtnJump.setText(mDuration + " 跳过");
                    if (mDuration < 0) {
                        mTimer.cancel();
                        mBtnJump.setVisibility(View.GONE);
                        gotoActivity();
                    }
                }
            });
        }
    };

    public interface ISplashActivityCallBack {
        void gotoActivity();
    }


    private void gotoActivity() {
        if (mCallBack != null) {
            mCallBack.gotoActivity();
        }
        finish();
    }


    /**
     * 开始倒计时
     */
    public void startCountDown() {
        isCanJump = true;
        initSplash();
        mBtnJump.setVisibility(View.VISIBLE);
        mTimer.schedule(task, 1000, 1000);
    }

    protected abstract int getDuration();

    @Override
    public void onClick(View v) {
        if (isCanJump) {
            task.cancel();
            gotoActivity();
        }
    }
}
