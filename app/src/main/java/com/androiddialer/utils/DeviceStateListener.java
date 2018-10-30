package com.androiddialer.utils;

import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;

import com.androiddialer.dialer.AppComponent;

public class DeviceStateListener extends PhoneStateListener {

    private final String TAG = DeviceStateListener.class.getSimpleName();

    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onSignalStrengthsChanged ++");
        super.onSignalStrengthsChanged(signalStrength);

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onSignalStrengthsChanged --");
    }
}
