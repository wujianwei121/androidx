package com.example.framwork.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.framwork.R;
import com.example.framwork.bean.UpdatePhotoInfo;
import com.example.framwork.glide.GlideEngine;
import com.example.framwork.glide.ImageLoaderUtils;
import com.example.framwork.utils.FileUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by lenovo on 2018/5/11.
 */

public class CustomHeaderView extends RelativeLayout {
    public static final int SELECT_HEADER_CODE = 0x128;
    private ImageView ivHeader;
    private String leftHint;
    private int hintIcon;
    private int hintColor;
    private float hintW;
    private int iconGravity;

    private Context mContext;
    private AppCompatActivity mActivity;
    private ISelectHeaderListener selectHeaderListener;

    public void setSelectHeaderListener(ISelectHeaderListener selectHeaderListener) {
        this.selectHeaderListener = selectHeaderListener;
    }

    public CustomHeaderView(Context context) {
        super(context);
        this.mContext = context;
        initAttrs(context, null);
    }

    public CustomHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initAttrs(context, attrs);
    }

    public CustomHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomHeaderView);
        leftHint = typedArray.getString(R.styleable.CustomHeaderView_chv_hint);
        hintIcon = typedArray.getResourceId(R.styleable.CustomHeaderView_chv_icon, R.drawable.ic_circle_header);
        hintColor = typedArray.getResourceId(R.styleable.CustomHeaderView_chv_hint_color, R.color.black);
        hintW = typedArray.getDimension(R.styleable.CustomHeaderView_chv_hint_w, 0);
        iconGravity = typedArray.getInt(R.styleable.CustomHeaderView_chv_icon_gravity, 2);

        typedArray.recycle();
        init();
    }

    private void init() {
        if (mContext instanceof AppCompatActivity) {
            mActivity = (AppCompatActivity) mContext;
        }
        LayoutInflater.from(getContext()).inflate(R.layout.view_header_view, this, true);
        ivHeader = findViewById(R.id.iv_cus_header);
        TextView tvHint = findViewById(R.id.tv_hint);
        ivHeader.setImageResource(hintIcon);

        RelativeLayout.LayoutParams hintLp = new RelativeLayout.LayoutParams(tvHint.getLayoutParams());
        hintLp.addRule(RelativeLayout.CENTER_VERTICAL);
        tvHint.setLayoutParams(hintLp);

        tvHint.setTextColor(ContextCompat.getColor(mContext, hintColor));
        if (iconGravity == 1) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ivHeader.getLayoutParams());
            lp.setMargins((int) hintW, 0, 0, 0);
            ivHeader.setLayoutParams(lp);
        } else {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ivHeader.getLayoutParams());
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            ivHeader.setLayoutParams(lp);
        }

        tvHint.setText(leftHint);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput(ivHeader);
                // 进入相册 以下是例子：用不到的api可以不写
                PictureSelector.create(mActivity)
                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                        .imageEngine(GlideEngine.createGlideEngine())
                        .imageSpanCount(4)// 每行显示个数 int
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .isCamera(true)// 是否显示拍照按钮 true or false
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .isGif(false)// 是否显示gif图片 true or false
                        .withAspectRatio(1, 1)
                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                        .circleDimmedLayer(false)
                        .isEnableCrop(true)
                        .isDragFrame(true)
                        .scaleEnabled(true)
                        .rotateEnabled(false)
                        .isCompress(true)
                        .freeStyleCropEnabled(true)
                        .isPreviewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                        .forResult(SELECT_HEADER_CODE);//结果回调onActivityResult code
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == SELECT_HEADER_CODE) {
            List<LocalMedia> pathList = PictureSelector.obtainMultipleResult(data);
            if (pathList != null && pathList.size() != 0) {
                LocalMedia localMedia = pathList.get(0);
                UpdatePhotoInfo photoInfo = new UpdatePhotoInfo();
                if (localMedia.isCut())
                    photoInfo.localPath = localMedia.getCutPath();
                else {
                    int version = android.os.Build.VERSION.SDK_INT;
                    if (version >= 29) {
                        photoInfo.localPath = localMedia.getAndroidQToPath();
                    } else {
                        photoInfo.localPath = localMedia.getPath();
                    }
                }
                photoInfo.photoSize = (int) (FileUtil.getInstance().getFileSizeL(mActivity, photoInfo.localPath) / 1024);
                if (photoInfo.photoSize == 0) {
                    Toasty.warning(mActivity, "所选图片已损坏");
                }
                if (selectHeaderListener != null) {
                    selectHeaderListener.selectHeaderImageSuccess(photoInfo.localPath);
                }
            }
        }
    }

    public interface ISelectHeaderListener {
        void selectHeaderImageSuccess(String url);
    }

    public void setUrlImage(String url) {
        if (!TextUtils.isEmpty(url))
            ImageLoaderUtils.displayCircleBorder(mContext, ivHeader, url, hintIcon);
    }

    public ImageView getIvHeader() {
        return ivHeader;
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput(View view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
        mInputMethodManager
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
