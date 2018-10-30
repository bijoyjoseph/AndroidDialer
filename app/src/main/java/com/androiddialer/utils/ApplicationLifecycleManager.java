package com.androiddialer.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class ApplicationLifecycleManager implements Application.ActivityLifecycleCallbacks {

    private static int visibleActivityCount = 0;
    private static int foregroundActivityCount = 0;

    public static boolean isAppInForeground() {
        return foregroundActivityCount > 0;
    }

    public static boolean isAppVisible() {
        return visibleActivityCount > 0;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        visibleActivityCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        foregroundActivityCount++;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        foregroundActivityCount--;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        visibleActivityCount--;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
