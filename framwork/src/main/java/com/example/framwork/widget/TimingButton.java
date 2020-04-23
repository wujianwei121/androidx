package com.example.framwork.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.example.framwork.R;

/**
 * Author MingRuQi
 * E-mail mingruqi@sina.cn,
 * <p>
 * DateTime 2019/2/15 16:18
 */
public class TimingButton extends AppCompatButton {
    private int total, interval;
    private String psText;
    private TimerLisenter timerLisenter;

    public TimingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义属性，并赋值
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TimingButton);
        total = typedArray.getInteger(R.styleable.TimingButton_tb_totalTime, 60000);
        interval = typedArray.getInteger(R.styleable.TimingButton_tb_timeInterval, 1000);
        psText = typedArray.getString(R.styleable.TimingButton_tb_psText);
        typedArray.recycle();
    }


    public class TimeCount extends CountDownTimer {
        private long countDownInterval;

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
            this.countDownInterval = countDownInterval;
        }

        @Override
        public void onFinish() {//计时完毕时触发
            setText(psText);
            setEnabled(true);
            if (timerLisenter != null) {
                timerLisenter.timerOver();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            setEnabled(false);
            long time = millisUntilFinished / countDownInterval;
            if (timerLisenter != null) {
                timerLisenter.clocking(time);
            }
            setText(time + "秒");
        }
    }


    /*---------------------------对外方法-----------------------------------*/


    /**
     * 设置倒计时监听
     *
     * @param timerLisenter 监听器
     */
    public void setTimerLisenter(TimerLisenter timerLisenter) {
        this.timerLisenter = timerLisenter;
    }


    /**
     * 开始倒计时
     */
    //执行
    public void start() {
        TimeCount time = new TimeCount(total, interval);
        time.onFinish();
    }

    /*------------------------接口----------------------------*/

    /**
     * 倒计时监听
     */
    public interface TimerLisenter {
        /**
         * 正在倒计时
         *
         * @param time 剩余时间（秒）
         */
        void clocking(long time);

        /**
         * 倒计时结束
         */
        void timerOver();

    }
}
