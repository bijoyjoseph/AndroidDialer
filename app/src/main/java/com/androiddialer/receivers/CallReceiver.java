package com.androiddialer.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.androiddialer.activities.CallHandleActivity;
import com.androiddialer.dialer.AppComponent;
import com.androiddialer.utils.AppConstants;

public class CallReceiver extends BroadcastReceiver {

    private final String TAG = CallReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onReceive ++");
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCallStateChanged ++");
                super.onCallStateChanged(state, incomingNumber);

                Intent callIntent = new Intent(context, CallHandleActivity.class);
                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.putExtra("message", incomingNumber);
                context.startActivity(callIntent);

                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCallStateChanged --");
            }
        },PhoneStateListener.LISTEN_CALL_STATE);
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onReceive --");
    }
}
