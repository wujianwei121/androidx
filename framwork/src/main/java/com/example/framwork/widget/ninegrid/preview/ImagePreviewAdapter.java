package com.example.framwork.widget.ninegrid.preview;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.framwork.R;
import com.example.framwork.widget.ninegrid.ImageInfo;

import java.util.List;

import xyz.zpayh.hdimage.HDImageView;


/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/3/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ImagePreviewAdapter extends PagerAdapter {

    private List<ImageInfo> imageInfo;
    private Context context;
    private View currentView;
    private OnItemImageClickListener clickListener;

    public ImagePreviewAdapter(Context context, @NonNull List<ImageInfo> imageInfo, OnItemImageClickListener clickListener) {
        super();
        this.imageInfo = imageInfo;
        this.context = context.getApplicationContext();
        this.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return imageInfo.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentView = (View) object;
    }

    public View getPrimaryItem() {
        return currentView;
    }

    public HDImageView getPrimaryImageView() {
        return (HDImageView) currentView.findViewById(R.id.pv);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photoview, container, false);
        final HDImageView imageView = view.findViewById(R.id.pv);
        ImageInfo info = this.imageInfo.get(position);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.imageClick();
                }
            }
        });
        if (info.bigImageUrl.startsWith("http"))
            imageView.setImageURI(info.bigImageUrl);
        else imageView.setImageURI("file://" + info.bigImageUrl);
        container.addView(view);
        return view;
    }

    public interface OnItemImageClickListener {
        void imageClick();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}