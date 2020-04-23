package com.example.framwork.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.framwork.WebViewActivity;
import com.example.framwork.widget.ninegrid.ImageInfo;
import com.example.framwork.widget.ninegrid.preview.ImagePreviewActivity;
import com.example.framwork.widget.selectgvimage.UpdatePhotoInfo;
import com.example.framwork.zxing.ui.CaptureActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/3/7.
 */

public class BaseGoto {
    /**
     * 去到拨号界面
     *
     * @param mobile
     */
    public static void toDialMobile(Context context, String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + mobile);
        intent.setData(data);
        context.startActivity(intent);
    }


    /**
     * @param context
     * @param webTitle            标题
     * @param webUrl              访问地址
     * @param titleBg             标题背景
     * @param backBtnRes          返回键
     * @param isTranslucentStatus 是否沉浸式
     */
    public static void toWebView(Context context, String webTitle, String webUrl, int titleBg, int backBtnRes, boolean isTranslucentStatus) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("title", webTitle);
        intent.putExtra("url", webUrl);
        intent.putExtra("title_bg", titleBg);
        intent.putExtra("back_res", backBtnRes);
        intent.putExtra("status_bar", isTranslucentStatus);
        context.startActivity(intent);
    }

    public static void toWebView(Context context, String title, String webUrl) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", webUrl);
        intent.putExtra("title_bg", 0);
        intent.putExtra("back_res", 0);
        intent.putExtra("status_bar", false);
        context.startActivity(intent);
    }

    public static void toQRCode(Context context, int bgId) {
        Intent intent = new Intent(context, CaptureActivity.class);
        intent.putExtra("title_bg", bgId);
        context.startActivity(intent);
    }

    /**
     * 图片预览
     *
     * @param context
     * @param list
     * @param position
     */
    public static void toImagePreviewActivity(Context context, List<ImageInfo> list, int position) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) list);
        bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, position);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 图片预览
     *
     * @param context
     */
    public static void toImagePreviewActivity(Context context, String imgUrl) {
        List<ImageInfo> list = new ArrayList<>();
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setBigImageUrl(imgUrl);
        list.add(imageInfo);
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) list);
        bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, 0);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
