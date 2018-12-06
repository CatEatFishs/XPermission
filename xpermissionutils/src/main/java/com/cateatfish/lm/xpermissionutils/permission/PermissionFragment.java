package com.cateatfish.lm.xpermissionutils.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Lm on 2018/1/29.
 * Email:1002464056@qq.com
 */

/**
 * 申请权限的Fragment
 */
public class PermissionFragment extends Fragment {

    /**
     * 请求码
     */
    private static final int CODE = 66;
    private static final int REQUEST_PERMISSION_SETTING = 55;

    PermissionListener mPermissionListener;
    private Activity mActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    /**
     * 设置成功回调的监听
     */
    public void setPermissionListener(PermissionListener permissionListener){
        this.mPermissionListener=permissionListener;
    }

    /**
     * 成功回调
     */
    public void onSucceed(){
        if (mPermissionListener!=null) {
            mPermissionListener.onSucceed();
        }
    }
    /**
     * 成功回调
     */
    public void onFiled(){
        if (mPermissionListener!=null) {
            mPermissionListener.onFiled();
        }
    }

    /**
     * 失败回调
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onFiled(String[] permissions){
        for (int i = 0; i < permissions.length; i++) {
            // 用户拒绝是true   用户选择不再提示是：false
            if (!shouldShowRequestPermissionRationale(permissions[i])) {
                new AlertDialog.Builder(mActivity)
                        .setTitle("权限被拒绝")
                        .setMessage("权限管理-->打开拒绝的权限")
                        .setPositiveButton("去打开", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                openSetting();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return;
            }

        }
        showToast("权限被拒绝");
    }

    /**
     * 显示Toast
     */
    private void showToast(String text) {
        Toast toast = Toast.makeText(mActivity, text, Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * 打开应用设置页面
     */
    private void openSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions(Activity activity, String[] permissions){
        this.mActivity=activity;
        requestPermissions(permissions,CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==CODE) {
            // 获取未申请的权限列表
            List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(mActivity, permissions);
            if (deniedPermissions.size()>0) {
                // 执行失败的方法
//                onFiled(permissions);
                onFiled();
            }else {
                // 执行成功的方法
                onSucceed();
            }
        }
    }

    @Override
    public void onDestroy() {
        mActivity=null;
        mPermissionListener=null;
        super.onDestroy();

    }
}
