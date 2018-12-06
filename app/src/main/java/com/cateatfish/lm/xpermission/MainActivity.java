package com.cateatfish.lm.xpermission;

import android.Manifest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.cateatfish.lm.xpermissionutils.permission.PermissionListener;
import com.cateatfish.lm.xpermissionutils.permission.XPermission;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new XPermission(this)
                .permissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE})
                .request(new PermissionListener() {
            @Override
            public void onSucceed() {
                Toast.makeText(MainActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFiled() {
                Toast.makeText(MainActivity.this, "申请失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
