package com.example.framwork.mvp;

import android.content.Context;

import com.example.framwork.noHttp.OnRequestListener;
import com.yanzhenjie.nohttp.RequestMethod;

import java.util.HashMap;
import java.util.List;


/**
 * Created by wanjingyu on 2016/10/8.
 */

public abstract class BasePresenter<Entity> {
    protected CustomRequest request;
    protected Context context;
    protected HashMap<String, Object> requestInfo;

    public BasePresenter(Context context, Class<Entity> clazz, EntityType type) {
        request = new CustomRequest(clazz, type);
        this.context = context;
    }

    public BasePresenter(Context context) {
        request = new CustomRequest(null, EntityType.ENTITY);
        this.context = context;
    }

    public void psot(OnRequestListener<Entity> requestListener) {
        request.resultModel(context, RequestMethod.POST, requestInfo, true, true, true, "", requestListener);
    }

    public void postNoToast(String loadtxt, OnRequestListener<Entity> requestListener) {
        request.resultModel(context, RequestMethod.POST, requestInfo, true, true, false, loadtxt, requestListener);
    }

    public void post(String loadtxt, OnRequestListener<Entity> requestListener) {
        request.resultModel(context, RequestMethod.POST, requestInfo, true, true, true, loadtxt, requestListener);
    }

    public void post(boolean isLoading, OnRequestListener<Entity> requestListener) {
        request.resultModel(context, RequestMethod.POST, requestInfo, true, isLoading, isLoading, "", requestListener);
    }

    public void post(boolean isLoading, String txt, OnRequestListener<Entity> requestListener) {
        request.resultModel(context, RequestMethod.POST, requestInfo, true, isLoading, isLoading, txt, requestListener);
    }

    public void postNoLoad(OnRequestListener<Entity> requestListener) {
        request.resultModel(context, RequestMethod.POST, requestInfo, true, false, true, "", requestListener);
    }

    public void postNoLoginNoLoading(OnRequestListener<Entity> requestListener) {
        request.resultModel(context, RequestMethod.POST, requestInfo, false, false, true, "", requestListener);
    }

    public void postNoLogin(String loadTxt, OnRequestListener<Entity> requestListener) {
        request.resultModel(context, RequestMethod.POST, requestInfo, false, true, true, loadTxt, requestListener);
    }

    public void post3NoLogin(OnRequestListener<Entity> requestListener) {
        request.resultModel(context, RequestMethod.POST, requestInfo, false, false, false, "", requestListener);
    }

    public void postLoadTxt(String loadingText, OnRequestListener<Entity> requestListener) {
        request.resultModel(context, RequestMethod.POST, requestInfo, true, true, true, loadingText, requestListener);
    }


    public void postImage(HashMap<String, Object> imgMap, boolean isLogin, boolean isShowLoading, boolean isShowToast, String loadTxt, OnRequestListener<Entity> requestListener) {
        request.resultPostImageModel(context, imgMap, requestInfo, isLogin, isShowLoading, isShowToast, loadTxt, requestListener);
    }

    public void postImage(String loadTxt, HashMap<String, Object> imgMap, OnRequestListener<Entity> requestListener) {
        request.resultPostImageModel(context, imgMap, requestInfo, true, true, true, loadTxt, requestListener);
    }

}
