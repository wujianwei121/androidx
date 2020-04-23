package com.example.framwork.mvp;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.example.framwork.base.QuickActivity;
import com.example.framwork.baseapp.BaseAppConfig;
import com.example.framwork.noHttp.Bean.BaseResponseBean;
import com.example.framwork.noHttp.CallServer;
import com.example.framwork.noHttp.FastJsonRequest;
import com.example.framwork.noHttp.HttpCallBack;
import com.example.framwork.noHttp.NetworkConfig;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.DLog;
import com.example.framwork.utils.DLog;
import com.example.framwork.utils.EncryptUtil;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.StringRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


/**
 * Created by wanjingyu on 2016/10/8.
 */

public class CustomRequest<Entity> {
    public final String TAG = "Network request";
    private static NetworkConfig sConfig;
    private String methodName;
    private Class<Entity> clazz;
    private EntityType type;

    public CustomRequest(Class<Entity> clazz, EntityType type) {
        this.clazz = clazz;
        this.type = type;
    }

    public static void setConfig(NetworkConfig config) {
        if (sConfig == null) {
            synchronized (NetworkConfig.class) {
                if (sConfig == null)
                    sConfig = config == null ? NetworkConfig.newBuilder().build() : config;
                else DLog.w("Kalle", "Only allowed to configure once.");
            }
        }
    }

    public static NetworkConfig getConfig() {
        setConfig(null);
        return sConfig;
    }


    public void resultModel(Context context, RequestMethod requestMethod, HashMap<String, Object> info, boolean isDLogin, boolean isShowLoading, boolean isShowToast, String loadingText, OnRequestListener<Object> requestListener) {
        if (!info.containsKey("methodName")) {
            DLog.e("设置方法");
            return;
        }
        this.methodName = info.get("methodName").toString();
        info.remove("methodName");
        Request<String> request;
        String jsonString = JSON.toJSONString(info);
        if (getConfig().isEncryption()) {
            request = new FastJsonRequest(BaseAppConfig.SERVICE_PATH + methodName,
                    requestMethod, jsonString);

        } else {
            request = new StringRequest(BaseAppConfig.SERVICE_PATH + methodName,
                    requestMethod);
            request.add(info);
        }
        DLog.d(TAG, requestMethod.getValue() + "请求：" + BaseAppConfig.SERVICE_PATH + methodName + jsonString);
        execute(context, request, isDLogin, isShowLoading, isShowToast, loadingText, requestListener);
    }


    public void resultPostImageModel(Context context, HashMap<String, String> imgInfo, HashMap<String, Object> info, boolean isDLogin, boolean isShowLoading, boolean isShowToast, String loadingText, OnRequestListener<Object> requestListener) {
        if (!info.containsKey("methodName")) {
            DLog.e("设置方法");
            return;
        }
        this.methodName = info.get("methodName").toString();
        info.remove("methodName");
        Request<String> request;
        String jsonString = JSON.toJSONString(info);
        if (getConfig().isEncryption()) {
            request = new FastJsonRequest(BaseAppConfig.SERVICE_PATH + methodName,
                    RequestMethod.POST, jsonString);
        } else {
            request = new StringRequest(BaseAppConfig.SERVICE_PATH + methodName,
                    RequestMethod.POST);
            request.add(info);
        }
        if (imgInfo.size() != 0) {
            StringBuilder imageDLog = new StringBuilder();
            for (Map.Entry<String, String> entry : imgInfo.entrySet()) {
                imageDLog.append("需要上传图片信息：").append(entry.getKey()).append(":").append(entry.getValue());
                if (entry.getValue() != null && !TextUtils.isEmpty(entry.getValue()) && !entry.getValue().startsWith("http")) {
                    request.add(entry.getKey(), new FileBinary(new File(entry.getValue())));
                }
            }
            DLog.d(TAG, imageDLog.toString());
        }
        DLog.d(TAG, "post入参：" + BaseAppConfig.SERVICE_PATH + methodName + jsonString);
        execute(context, request, isDLogin, isShowLoading, isShowToast, loadingText, requestListener);
    }

    public void execute(Context context, Request<String> request, boolean isDLogin, boolean isShowLoading, boolean isShowToast, String loadingText, OnRequestListener<Object> requestListener) {
        if (context instanceof QuickActivity) {
            if (isShowLoading) {
                QuickActivity b = (QuickActivity) context;
                if (TextUtils.isEmpty(loadingText))
                    b.showProgress();
                else b.showProgress(loadingText);
            }
        }
        addRequest(context, isDLogin, isShowLoading, isShowToast, request, requestListener);
    }

    private void addRequest(final Context context, final boolean isDLogin, final boolean isShowLoading, final boolean isShowToast, final Request<String> request, final OnRequestListener<Object> requestListener) {
        CallServer.getRequestInstance().add(context, request, new HttpCallBack<String>() {
            @Override
            public void onSucceed(int what, String response) {
                DLog.d(TAG, methodName + "原始出参：" + response);
                if (!TextUtils.isEmpty(response.trim())) {
                    BaseResponseBean bean;
                    if (getConfig().isEncryption()) {
                        String responseS = EncryptUtil.getInstance().decodeValue(response);
                        DLog.d(TAG, methodName + "解密出参：" + responseS);
                    }
                    try {
                        bean = (BaseResponseBean) BaseResponseBean.parseObj(response, getConfig().getReponseC());
                        if (bean.isSuccess()) {
                            if (clazz == null) {
                                requestListener.requestSuccess(bean);
                            } else {
                                if (type == EntityType.ENTITY) {
                                    requestListener.requestSuccess(bean.parseObject(clazz));
                                } else if (type == EntityType.LIST) {
                                    requestListener.requestSuccess(bean.parseList(clazz));
                                }
                            }
                        } else {
                            //如果过滤错误信息   就不调用failed方法
                            if (getConfig().getFilter() != null && !getConfig().getFilter().filterOperation(context, bean, isDLogin, bean.getCode())) {
                                if (isShowToast) {
                                    Toasty.error(context, bean.getMessage() == null ? "获取数据失败" : bean.getMessage()).show();
                                }
                                requestListener.requestFailed(bean.getCode(), bean, null, bean.getMessage() == null ? "获取数据失败" : bean.getMessage());
                            }
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        onFailed(1, null, "解析数据异常");
                    }
                }
            }

            @Override
            public void onFailed(int what, Exception exception, String error) {
                if (isShowToast) {
                    Toasty.error(context, error).show();
                }
                requestListener.requestFailed(what, null, exception, error);

            }

            @Override
            public void onFinish() {
                if (context instanceof QuickActivity && isShowLoading) {
                    QuickActivity activity = (QuickActivity) context;
                    if (activity.isFinishing())
                        return;
                    activity.hideProgress();
                }
                requestListener.requestFinish();
            }
        }, 0);
    }
}
