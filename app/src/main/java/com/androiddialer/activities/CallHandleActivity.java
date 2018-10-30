package com.androiddialer.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androiddialer.R;
import com.androiddialer.dialer.AppComponent;
import com.androiddialer.utils.AppConstants;

import java.lang.reflect.Method;
import java.util.Date;

public class CallHandleActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = CallHandleActivity.class.getSimpleName();

    private TextView tv_caller;
    private Button btn_answer_call;
    private Button btn_hang_up;
    private String callingPerson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCreate ++");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_call);
        if (getIntent() != null) {
            initUI(getIntent().getStringExtra("message"));
            //callingPerson = getIntent().getStringExtra("callPerson");
        }
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCreate --");
    }

    private void initUI(String phoneNumber) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "initUI ++");

        tv_caller = findViewById(R.id.tv_caller);
        btn_answer_call = findViewById(R.id.btn_answer_call);
        btn_hang_up = findViewById(R.id.btn_hang_up);
//        if(callingPerson != null && !callingPerson.isEmpty()) {
//            tv_caller.setText(getResources().getString(R.string.calling) + " " + callingPerson);
//            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "callingPerson ++ " + callingPerson);
//            btn_answer_call.setVisibility(View.GONE);
//        } else {
//            tv_caller.setText(phoneNumber + " " + getResources().getString(R.string.calling));
//        }
        btn_answer_call.setOnClickListener(this);
        btn_hang_up.setOnClickListener(this);

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "initUI --");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_answer_call:
                if (AppConstants.LOG)
                    AppComponent.getInstance().writeLog(TAG, "btn_answer_call ++");
                answerCall();
                if (AppConstants.LOG)
                    AppComponent.getInstance().writeLog(TAG, "btn_answer_call --");
                break;

            case R.id.btn_hang_up:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_hang_up ++");
                try {
                    hangUpCall();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_hang_up --");
                break;
        }
    }

    private void answerCall() {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "answerCall ++");
        TelecomManager telecomManager;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            telecomManager = (TelecomManager) CallHandleActivity.this
                    .getSystemService(Context.TELECOM_SERVICE);

            if (telecomManager == null) {
                // whether you want to handle this is up to you really
                throw new NullPointerException("tm == null");
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                telecomManager.acceptRingingCall();
            }
        } /*else {
            new Thread(() -> {
                try {
                    Runtime.getRuntime().exec("input keyevent " +
                            Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));
                } catch (IOException e) {
                    // Runtime.exec(String) had an I/O problem, try to fall back
                    String enforcedPerm = "android.permission.CALL_PRIVILEGED";
                    Intent btnDown = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                            Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN,
                                    KeyEvent.KEYCODE_HEADSETHOOK));
                    Intent btnUp = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                            Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP,
                                    KeyEvent.KEYCODE_HEADSETHOOK));

                    CallHandleActivity.this.sendOrderedBroadcast(btnDown, enforcedPerm);
                    CallHandleActivity.this.sendOrderedBroadcast(btnUp, enforcedPerm);
                }
            }).start();
        }*/

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "answerCall --");
    }

    private void hangUpCall() {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "hangUpCall ++");
        try {

            String serviceManagerName = "android.os.ServiceManager";
            String serviceManagerNativeName = "android.os.ServiceManagerNative";
            String telephonyName = "com.android.internal.telephony.ITelephony";
            Class<?> telephonyClass;
            Class<?> telephonyStubClass;
            Class<?> serviceManagerClass;
            Class<?> serviceManagerNativeClass;
            Method telephonyEndCall;
            Object telephonyObject;
            Object serviceManagerObject;
            telephonyClass = Class.forName(telephonyName);
            telephonyStubClass = telephonyClass.getClasses()[0];
            serviceManagerClass = Class.forName(serviceManagerName);
            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
            Method getService = // getDefaults[29];
                    serviceManagerClass.getMethod("getService", String.class);
            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
            Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            telephonyObject = serviceMethod.invoke(null, retbinder);
            telephonyEndCall = telephonyClass.getMethod("endCall");
            telephonyEndCall.invoke(telephonyObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "hangUpCall --");
    }

}
