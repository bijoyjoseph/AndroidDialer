package com.androiddialer.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androiddialer.R;
import com.androiddialer.dialer.AppComponent;
import com.androiddialer.utils.AppConstants;
import com.androiddialer.utils.NetworkInfoUtils;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private EditText et_phone_number;
    private Button btn_user_action[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCreate ++");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCreate --");
    }

    private void initUI() {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "initUI ++");

        btn_user_action = new Button[16];

        et_phone_number = findViewById(R.id.et_phone_number);
        btn_user_action[0] = findViewById(R.id.btn_one);
        btn_user_action[1] = findViewById(R.id.btn_two);
        btn_user_action[2] = findViewById(R.id.btn_three);
        btn_user_action[3] = findViewById(R.id.btn_four);
        btn_user_action[4] = findViewById(R.id.btn_five);
        btn_user_action[5] = findViewById(R.id.btn_six);
        btn_user_action[6] = findViewById(R.id.btn_seven);
        btn_user_action[7] = findViewById(R.id.btn_eight);
        btn_user_action[8] = findViewById(R.id.btn_nine);
        btn_user_action[9] = findViewById(R.id.btn_asterix);
        btn_user_action[10] = findViewById(R.id.btn_zero);
        btn_user_action[11] = findViewById(R.id.btn_hash);
        btn_user_action[12] = findViewById(R.id.btn_call);
        //btn_user_action[13] = findViewById(R.id.btn_hang_up);
        btn_user_action[13] = findViewById(R.id.btn_call_logs);
        btn_user_action[14] = findViewById(R.id.btn_plus);
        btn_user_action[15] = findViewById(R.id.btn_clear);

        try {
            for(int index = 0; index < btn_user_action.length; index++) {
                btn_user_action[index].setOnClickListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "initUI --");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_one ++");
                et_phone_number.setText(et_phone_number.getText() + getResources().getString(R.string.number_one));
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_one --");
                break;

            case R.id.btn_two:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_two ++");
                et_phone_number.setText(et_phone_number.getText() + getResources().getString(R.string.number_two));
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_two --");
                break;

            case R.id.btn_three:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_three ++");
                et_phone_number.setText(et_phone_number.getText() + getResources().getString(R.string.number_three));
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_three --");
                break;

            case R.id.btn_four:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_four ++");
                et_phone_number.setText(et_phone_number.getText() + getResources().getString(R.string.number_four));
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_four --");
                break;

            case R.id.btn_five:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_five ++");
                et_phone_number.setText(et_phone_number.getText() + getResources().getString(R.string.number_five));
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_five --");
                break;

            case R.id.btn_six:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_six ++");
                et_phone_number.setText(et_phone_number.getText() + getResources().getString(R.string.number_six));
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_six --");
                break;

            case R.id.btn_seven:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_seven ++");
                et_phone_number.setText(et_phone_number.getText() + getResources().getString(R.string.number_seven));
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_seven --");
                break;

            case R.id.btn_eight:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_eight ++");
                et_phone_number.setText(et_phone_number.getText() + getResources().getString(R.string.number_eight));
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_eight --");
                break;

            case R.id.btn_nine:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_nine ++");
                et_phone_number.setText(et_phone_number.getText() + getResources().getString(R.string.number_nine));
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_nine --");
                break;

            case R.id.btn_zero:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_zero ++");
                et_phone_number.setText(et_phone_number.getText() + getResources().getString(R.string.number_zero));
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_zero --");
                break;

            case R.id.btn_asterix:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_asterix ++");
                et_phone_number.setText(et_phone_number.getText() + getResources().getString(R.string.asterix));
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_asterix --");
                break;

            case R.id.btn_hash:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_hash ++");
                et_phone_number.setText(et_phone_number.getText() + getResources().getString(R.string.hash));
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_hash --");
                break;

            case R.id.btn_plus:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_plus ++");
                et_phone_number.setText(et_phone_number.getText() + getResources().getString(R.string.plus));
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_plus --");
                break;

            case R.id.btn_clear:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_clear ++");
                deleteDigits();
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_clear --");
                break;

            case R.id.btn_call:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_call ++");
                callPhone(et_phone_number.getText().toString().trim());
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_call --");
                break;

//            case R.id.btn_hang_up:
//                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_hang_up ++");
//                hangUpCall();
//                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_hang_up --");
//                break;

            case R.id.btn_call_logs:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_call_logs **" + " NetworkInfo-> " +
                        NetworkInfoUtils.getNetworkDetailsInstance().getInternetStatus(this) + "\n isConnectedToInternet-> " +
                        AppComponent.getInstance().isNetworkConnected(this) + "\n isConnectedToMobileNetwork-> " +
                        AppComponent.getInstance().isConnectedToMobileData(this) + "\n isConnectedToWifi-> " +
                        AppComponent.getInstance().isConnectedToWifi(this) + "\n WifiInfo-> wifiSIgnalStrength: " +
                        NetworkInfoUtils.getNetworkDetailsInstance().getWifiInfo(this)[0] + " SSID: " +
                        NetworkInfoUtils.getNetworkDetailsInstance().getWifiInfo(this)[1] +
                        "\n mobileNetworkInfo-> mobileNetworkConnection: " +
                        NetworkInfoUtils.getNetworkDetailsInstance().getMobileNetworkInfo(this)[0] + " signalStrength: " +
                        NetworkInfoUtils.getNetworkDetailsInstance().getMobileNetworkInfo(this)[1] + " radioType: " +
                        NetworkInfoUtils.getNetworkDetailsInstance().getMobileNetworkInfo(this)[2] + "\n cellTowerLocation-> ");

                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_call_logs ++");

                startActivity(new Intent(this, CallLogActivity.class));

                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "btn_call_logs --");
                break;
        }
    }

    private void deleteDigits() {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "deleteDigits ++");
        try {
            if(et_phone_number.getText().length() > 0 ) {
                et_phone_number.getText().delete(et_phone_number.getText().length() - 1, et_phone_number.getText().length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "deleteDigits --");
    }

    private void callPhone(String phoneNumber) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "callPhone ++");
        if (!phoneNumber.equals("")) {
            Uri number = Uri.parse("tel:" + phoneNumber);
            Intent dial = new Intent(Intent.ACTION_CALL, number);
            startActivity(dial);
        }
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "callPhone --");
    }

//    private void hangUpCall() {
//        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "hangUpCall ++");
//        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        try {
//            Class aClass = Class.forName(telephonyManager.getClass().getName());
//            Method method = aClass.getDeclaredMethod("getITelephony");
//            method.setAccessible(true);
//            CallAction callAction = (CallAction) method.invoke(telephonyManager);
//            if(et_phone_number.getText().toString().trim() != null) {
//                callAction.endCall();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "hangUpCall --");
//    }
}
