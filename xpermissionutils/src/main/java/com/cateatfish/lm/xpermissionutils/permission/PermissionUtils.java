package com.cateatfish.lm.xpermissionutils.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lm on 2018/1/29.
 * Email:1002464056@qq.com
 */

public class PermissionUtils {

    /**
     * 获取用户未授权的权限列表
     */
    public static List<String> getDeniedPermissions(Activity activity, String... permissions){
        List<String> list=new ArrayList<>();
        if (permissions==null) {
            return list;
        }
        for (String p : permissions){
            int selfPermission = ContextCompat.checkSelfPermission(activity, p);
            if (selfPermission!= PackageManager.PERMISSION_GRANTED) {
                list.add(p);
            }
        }

        return list;
    }

    /**
     * 判断版本是不是大于6.0
     */
    public static boolean isUpAndroidM(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
