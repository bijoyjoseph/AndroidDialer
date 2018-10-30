package com.androiddialer.dialer;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androiddialer.utils.AppConstants;
import com.androiddialer.utils.ApplicationLifecycleManager;
import com.androiddialer.utils.NetworkInfoUtils;

import androidx.multidex.MultiDexApplication;
import okhttp3.OkHttpClient;

public class AppComponent extends MultiDexApplication {

    private final String TAG = AppComponent.class.getSimpleName();
    private static AppComponent appComponent = null;
    private Snackbar snackbar;
    private Toast toast;
    private Handler mHandler = null;
    private OkHttpClient okHttpClient = null;

    public static synchronized AppComponent getInstance() {
        if(appComponent == null) {
            appComponent = new AppComponent();
        }
        return appComponent;
    }

    @Override
    public void onCreate() {
        if (AppConstants.LOG) writeLog(TAG, "onCreate ++");
        super.onCreate();
        appComponent = this;
        registerActivityLifecycleCallbacks(new ApplicationLifecycleManager());
        if (AppConstants.LOG) writeLog(TAG, "onCreate --");
    }

    public void writeLog(final String TAG, final String log) {
        Log.d(TAG, log);
    }

    public Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    public OkHttpClient getOkHttpClient() {
        if (AppConstants.LOG) writeLog(TAG, "getOkHttpClient ++");
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder().build();
        }
        if (AppConstants.LOG) writeLog(TAG, "getOkHttpClient --");
        return okHttpClient;
    }


    public boolean isNetworkAvailable() {
        if (AppConstants.LOG) writeLog(TAG, "isNetworkAvailable ++");
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null)
            return activeNetworkInfo.isConnected();
        else {
            return Boolean.FALSE;
        }
    }

    public int globalSnackbar(final View view, final String message, final int length, final String actionMessage, final int color) {
        if (AppConstants.LOG) writeLog(TAG, "globalSnackbar ++ " + message);
        int returnValue = 0;
        try {
            if (message != null && !message.equals("")) {
                if (snackbar == null && view != null) {
                    snackbar = Snackbar.make(view, message, length);
                }
                if (snackbar != null) {
                    snackbar.setText(message);
                    snackbar.getView().setBackgroundColor(color);
                    snackbar.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (AppConstants.LOG) writeLog(TAG, "globalSnackbar --");
        return returnValue;
    }

    public void dismissGlobalSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
        snackbar = null;
    }

    public void globalToast(String message, Context globalContext) {
        if (AppConstants.LOG) writeLog(TAG, "globalToast ++ ");
        try {
            if (message != null && !message.equals("")) {
                if (toast == null && globalContext != null) {
                    toast = Toast.makeText(globalContext, "", Toast.LENGTH_LONG);
                }
                if (toast != null) {
                    toast.setText(message);
                    toast.show();
                }

            } else if (message != null && message.equals("") && toast != null) {
                toast.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (AppConstants.LOG) writeLog(TAG, "globalToast --");
    }

    public void dismissGlobalToast() {
        if (toast != null) {
            toast.cancel();
        }
        toast = null;
    }

    public boolean isNetworkConnected(Context context){
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "isNetworkConnected ++");
        NetworkInfo info = NetworkInfoUtils.getNetworkDetailsInstance().getInternetStatus(context);
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "isNetworkConnected --");
        return (info != null && info.isConnected());
    }

    public boolean isConnectedToWifi(Context context){
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "isConnectedToWifi ++");
        NetworkInfo info = NetworkInfoUtils.getNetworkDetailsInstance().getInternetStatus(context);
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "isConnectedToWifi --");
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public boolean isConnectedToMobileData(Context context){
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "isConnectedToMobileData ++");
        NetworkInfo info = NetworkInfoUtils.getNetworkDetailsInstance().getInternetStatus(context);
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "isConnectedToMobileData --");
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public boolean isGpsEnabled() {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "isGpsEnabled ++");
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "isGpsEnabled --");
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
