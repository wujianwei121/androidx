package com.example.framwork.widget;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by wanjingyu on 2016/6/2.
 */
public class CustomerRecyclerView extends RecyclerView {
    public CustomerRecyclerView(Context context) {
        super(context);
    }

    public CustomerRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 只重写该方法，达到使ListView适应ScrollView的效果
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
