package com.cateatfish.lm.xpermissionutils.permission;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;

/**
 * Created by Lm on 2018/1/29.
 * Email:1002464056@qq.com
 */

public class XPermission {

    private Activity mActivity;
    private PermissionFragment mPermissionFragment;// 申请权限的中间Fragment
    private String[] mPermissions;
    private static final String TAG = "XPermission";
    public XPermission(Activity activity){
        this.mActivity=activity;
        this.mPermissionFragment=getPermissionFragment(activity);
    }

    private PermissionFragment getPermissionFragment(Activity activity) {
        PermissionFragment permissionsFragment = findPermissionsFragment(activity);
        boolean isNewInstance = permissionsFragment == null;
        if (isNewInstance) {
            permissionsFragment=new PermissionFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.beginTransaction()
                    .add(permissionsFragment,TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }

        return permissionsFragment;
    }

    /**
     * 去找权限的Fragment
     */
    private PermissionFragment findPermissionsFragment(Activity activity) {

        return (PermissionFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }

    /**
     * 要申请的权限
     */
    public XPermission permissions(String[] permissions){
        this.mPermissions=permissions;
        return this;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void request(PermissionListener permissionListener){
        mPermissionFragment.setPermissionListener(permissionListener);
        // 1.判断一下是不是6.0以上的系统
        if (!PermissionUtils.isUpAndroidM()) {
            //6.0以下 执行成功的方法
            mPermissionFragment.onSucceed();
            return;
        }
        // 2.获取未申请的权限列表
        List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(mActivity, mPermissions);
        if (deniedPermissions.size()>0) {
            // 3.要去申请权限
            mPermissionFragment.requestPermissions(mActivity,deniedPermissions.toArray(new String[deniedPermissions.size()]));
        }else {
            // 执行成功的方法
            mPermissionFragment.onSucceed();
        }
    }
}
