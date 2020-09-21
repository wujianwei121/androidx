package com.example.framwork.utils.permission;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.framwork.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yanzhenjie.permission.runtime.PermissionDef;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by lenovo on 2018/5/10.
 */

public class PermissionUtil {
    public static final int REQUEST_CODE_SETTING = 0x001;

    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     *
     * @author xuzhaohu
     */
    private static class SingletonHolder {
        private static PermissionUtil instance = new PermissionUtil();

    }

    /**
     * 私有的构造函数
     */
    private PermissionUtil() {
    }

    public static PermissionUtil getInstance() {
        return PermissionUtil.SingletonHolder.instance;
    }

    public interface IPermissionsCallBck {
        void premissionsCallback(boolean isSuccess);
    }

    public void requestPermission(final AppCompatActivity activity, final IPermissionsCallBck permissionsCallBck, @PermissionDef String... permissions) {
        AndPermission.with(activity)
                .runtime()
                .permission(permissions)
                .rationale(new DefaultRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (permissionsCallBck != null)
                            permissionsCallBck.premissionsCallback(true);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        Toasty.warning(activity, "权限申请失败，可能会影响您的正常使用").show();
                        if (permissionsCallBck != null)
                            permissionsCallBck.premissionsCallback(false);
                        if (AndPermission.hasAlwaysDeniedPermission(activity, permissions)) {
                            showSetting(activity, permissions);
                        }
                    }
                })
                .start();
    }


    public void showSetting(final Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AndPermission.with(context)
                                .runtime()
                                .setting()
                                .start(REQUEST_CODE_SETTING);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}
