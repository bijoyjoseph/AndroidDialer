package com.androiddialer.activities;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.androiddialer.R;
import com.androiddialer.dialer.AppComponent;
import com.androiddialer.utils.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    private ConstraintLayout cl_splash_activity_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        cl_splash_activity_layout = findViewById(R.id.cl_splash_activity_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cl_splash_activity_layout != null) {
                cl_splash_activity_layout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        AppComponent.getInstance().getHandler().postDelayed(() -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                permissionCheck();

            } else {
                LaunchApp();
            }
        }, 2000);
    }

    private void permissionCheck() {
        List<String> permissionsNeeded = new ArrayList<>();

        final List<String> permissionsList = new ArrayList<>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("FINE_LOCATION");
        if (!addPermission(permissionsList, Manifest.permission.READ_CALL_LOG))
            permissionsNeeded.add("READ_CALL_LOG");
        if (!addPermission(permissionsList, Manifest.permission.CALL_PHONE))
            permissionsNeeded.add("CALL_PHONE");
        if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE))
            permissionsNeeded.add("READ_PHONE_STATE");
        if (!addPermission(permissionsList, Manifest.permission.PROCESS_OUTGOING_CALLS))
            permissionsNeeded.add("PROCESS_OUTGOING_CALLS");
        if (!addPermission(permissionsList, Manifest.permission.ANSWER_PHONE_CALLS))
            permissionsNeeded.add("ANSWER_PHONE_CALLS");
        /*if (!addPermission(permissionsList, Manifest.permission.MODIFY_PHONE_STATE))
            permissionsNeeded.add("MODIFY_PHONE_STATE");*/

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                String message = "Please grant " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        (dialog, which) -> {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        AppConstants.MULTIPLE_PERMISSIONS);
                            }

                        });
                return;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        AppConstants.MULTIPLE_PERMISSIONS);
            }

            return;
        } else {
            LaunchApp();
        }

    }

    private boolean addPermission(List<String> permissionsList, String permission) {

        Boolean cond;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                if (!shouldShowRequestPermissionRationale(permission))
                    cond = false;
            }
            cond = true;
        } else {
            cond = true;
        }
        return cond;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SplashActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == Build.VERSION_CODES.M) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                AppComponent.getInstance().globalToast(getResources().getString(R.string.permission_message), this);
            }
        }
        if (requestCode == AppConstants.MULTIPLE_PERMISSIONS) {
            Map<String, Integer> perms = new HashMap<>();
            perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.READ_CALL_LOG, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.PROCESS_OUTGOING_CALLS, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.ANSWER_PHONE_CALLS, PackageManager.PERMISSION_GRANTED);
            /*perms.put(Manifest.permission.MODIFY_PHONE_STATE, PackageManager.PERMISSION_GRANTED);*/
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
            if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.PROCESS_OUTGOING_CALLS) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.ANSWER_PHONE_CALLS) == PackageManager.PERMISSION_GRANTED /*&&
                    perms.get(Manifest.permission.MODIFY_PHONE_STATE) == PackageManager.PERMISSION_GRANTED*/) {
                LaunchApp();

            } else {
                AppComponent.getInstance().globalToast(getResources().getString(R.string.permission_message), SplashActivity.this);

                AppComponent.getInstance().getHandler().postDelayed(() -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, AppConstants.MULTIPLE_PERMISSIONS);
                    finish();
                }, 2000);
            }
        }
    }

    public void LaunchApp() {
        Thread background = new Thread() {
            public void run() {

                try {
                    sleep(2000);

                    if (AppComponent.getInstance().isNetworkAvailable()) {
                        ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(SplashActivity.this,
                                R.anim.activity_enter, R.anim.activity_exit);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class), activityOptions.toBundle());
                        finish();
                    } else {
                        AppComponent.getInstance().globalSnackbar(cl_splash_activity_layout,
                                getResources().getString(R.string.no_internet),
                                Snackbar.LENGTH_LONG, "", ContextCompat.getColor(SplashActivity.this, android.R.color.holo_red_light));
                    }

                } catch (Exception e) {

                }
            }
        };
        background.start();
    }
}
