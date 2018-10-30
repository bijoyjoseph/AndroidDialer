package com.androiddialer.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.CallLog;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.androiddialer.R;
import com.androiddialer.adapter.CallLogAdapter;
import com.androiddialer.databaseHandler.DatabaseHandler;
import com.androiddialer.dialer.AppComponent;
import com.androiddialer.interfaces.NetworkResponseCallback;
import com.androiddialer.interfaces.PhoneStateDataTransfer;
import com.androiddialer.models.CallDetailsModel;
import com.androiddialer.utils.AppConstants;
import com.androiddialer.utils.CustomPhoneStateListener;
import com.androiddialer.utils.NetworkInfoUtils;
import com.androiddialer.utils.NetworkUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CallLogActivity extends AppCompatActivity implements PhoneStateDataTransfer {

    private final String TAG = CallLogActivity.class.getSimpleName();

    private ConstraintLayout constraint_layout;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private double cellTowerLatitude = 0.0;
    private double cellTowerLongitude = 0.0;
    private String cellTowerAccuracy;
    private TelephonyManager telephonyManager;
    private String radioType;
    private String carrie;
    private String considerIp;
    private int cellId;
    private int locationAreaCode;
    private int mobileCountryCode;
    private int mobileNetworkCode;
    private int age;
    private int signalStrength;
    private int timingAdvance;
    private RecyclerView rv_call_log_recycler;
    private CallLogAdapter callLogAdapter;
    private CallDetailsModel callDetailsModel;
    private ArrayList<CallDetailsModel> callDetailsModelArrayList;
    private DatabaseHandler databaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCreate ++");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);

        constraint_layout = findViewById(R.id.constraint_layout);

        callDetailsModel = new CallDetailsModel();

        getCurrentLatLng();

        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        telephonyManager.listen(new CustomPhoneStateListener(this),
                PhoneStateListener.LISTEN_CALL_STATE | PhoneStateListener.LISTEN_CELL_INFO |
                        PhoneStateListener.LISTEN_CELL_LOCATION | PhoneStateListener.LISTEN_DATA_ACTIVITY |
                        PhoneStateListener.LISTEN_DATA_CONNECTION_STATE | PhoneStateListener.LISTEN_SERVICE_STATE |
                        PhoneStateListener.LISTEN_SIGNAL_STRENGTHS | PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR |
                        PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR);

        NetworkInfoUtils.getNetworkDetailsInstance().getLocationFromCellTower(this);

        getCallDetails();

        initRecyclerView();

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCreate --");
    }

//    private void getConnectivityStatus() {
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo Info = cm.getActiveNetworkInfo();
//        if (Info == null || !Info.isConnectedOrConnecting()) {
//            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "No connection");
//        } else {
//            int netType = Info.getType();
//            int netSubtype = Info.getSubtype();
//
//            if (netType == ConnectivityManager.TYPE_WIFI) {
//                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "Wifi connection");
//                WifiManager wifiManager = (WifiManager) getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//                List<ScanResult> scanResult = wifiManager.getScanResults();
//                for (int i = 0; i < scanResult.size(); i++) {
//                    if (AppConstants.LOG) AppComponent.getInstance().writeLog("scanResult", "Speed of wifi"+scanResult.get(i).level);//The db level of signal
//                }
//
//
//            } else if (netType == ConnectivityManager.TYPE_MOBILE) {
//                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "GPRS/3G connection");
//            }
//        }
//    }

    private void initRecyclerView() {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "initRecyclerView ++");

        rv_call_log_recycler = findViewById(R.id.rv_call_log_recycler);
        databaseHandler = new DatabaseHandler(CallLogActivity.this);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(CallLogActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        callDetailsModelArrayList = (ArrayList<CallDetailsModel>) databaseHandler.getAllCallsList();

        for (int i = callDetailsModelArrayList.size() - 1; i > 0; i--) {
            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "firstLoop i ++");
            for (int j = i - 1; j >= 0; j--) {
                if (AppConstants.LOG)
                    AppComponent.getInstance().writeLog(TAG, "secondLoop j ++");
                if (callDetailsModelArrayList.get(i).getCallDayAndTime().equals(callDetailsModelArrayList.get(j).getCallDayAndTime())) {
                    if (AppConstants.LOG)
                        AppComponent.getInstance().writeLog(TAG, "thirdLoop remove ++");
                    callDetailsModelArrayList.remove(i);
                    if (AppConstants.LOG)
                        AppComponent.getInstance().writeLog(TAG, "thirdLoop remove --");
                    break;
                }
            }
        }

        callLogAdapter = new CallLogAdapter(CallLogActivity.this, callDetailsModelArrayList);
        rv_call_log_recycler.setLayoutManager(mLinearLayoutManager);
        rv_call_log_recycler.setAdapter(callLogAdapter);

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "initRecyclerView --");
    }

    private void getCurrentLatLng() {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getCurrentLatLng ++");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (AppConstants.LOG)
                    AppComponent.getInstance().writeLog(TAG, "onLocationChanged ++");

                if (AppComponent.getInstance().isGpsEnabled()) {

                    if (AppConstants.LOG)
                        AppComponent.getInstance().writeLog(TAG, "isGpsEnabled ++");

                    if (locationManager != null) {
                        if (ActivityCompat.checkSelfPermission(CallLogActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CallLogActivity.this,
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if (AppConstants.LOG)
                            AppComponent.getInstance().writeLog(TAG, "latlng: ++ " +
                                    location.getLatitude() + " " + location.getLongitude());

                        callDetailsModel.setGpsLocation(getUserLocation(location.getLatitude(),
                                location.getLongitude()));
                    }

                    if (AppConstants.LOG)
                        AppComponent.getInstance().writeLog(TAG, "isGpsEnabled --");

                }

                if (AppConstants.LOG)
                    AppComponent.getInstance().writeLog(TAG, "onLocationChanged -- " +
                            location.getLatitude() + " " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                if (AppConstants.LOG)
                    AppComponent.getInstance().writeLog(TAG, "onStatusChanged ++");

                if (AppConstants.LOG)
                    AppComponent.getInstance().writeLog(TAG, "onStatusChanged --");
            }

            @Override
            public void onProviderEnabled(String provider) {
                if (AppConstants.LOG)
                    AppComponent.getInstance().writeLog(TAG, "onProviderEnabled ++");

                if (AppConstants.LOG)
                    AppComponent.getInstance().writeLog(TAG, "onProviderEnabled --");
            }

            @Override
            public void onProviderDisabled(String provider) {
                if (AppConstants.LOG)
                    AppComponent.getInstance().writeLog(TAG, "onProviderDisabled ++");

                if (AppConstants.LOG)
                    AppComponent.getInstance().writeLog(TAG, "onProviderDisabled --");
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 1, locationListener);

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getCurrentLatLng --");
    }

    private String getUserLocation(double latitude, double longitude) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getUserLocation ++");
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getAddressLine(0)).append(",");
                result.append(address.getCountryName());
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getUserLocation --");

        return result.toString();
    }

    private void handleUserLocationFromCellTowerInfo(String radioType, String carrier,
                                                     int cellId, int locationAreaCode, int mobileCountryCode,
                                                     int mobileNetworkCode, int age, int signalStrength) {

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "handleUserLocationFromCellTowerInfo ++");

        new NetworkUtil(this).getLatLngFromCellTowerInfo(radioType, carrier, "true", cellId,
                locationAreaCode, mobileCountryCode, mobileNetworkCode, age,
                signalStrength, 0, new NetworkResponseCallback() {

                    @Override
                    public void onSuccess(Object... object) {
                        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onSuccess ++");

                        try {

                            cellTowerLatitude = (double) object[0];
                            cellTowerLongitude = (double) object[1];
                            cellTowerAccuracy = (String) object[2];

                            callDetailsModel.setCellTowerLocation(getUserLocation(cellTowerLatitude, cellTowerLongitude));
                            callDetailsModel.setConnectedToInternet(AppComponent.getInstance().isNetworkConnected(CallLogActivity.this));
                            callDetailsModel.setConnectedToMobileInternet(AppComponent.getInstance().isConnectedToMobileData(CallLogActivity.this));
                            callDetailsModel.setConnectedToWifi(AppComponent.getInstance().isConnectedToWifi(CallLogActivity.this));
                            callDetailsModel.setCarrier(carrier);
                            callDetailsModel.setRadioType(radioType);
                            callDetailsModel.setMobileNetworkSignalStrength(signalStrength);
                            callDetailsModel.setWifiSignalStrength((String) NetworkInfoUtils.getNetworkDetailsInstance().getWifiInfo(CallLogActivity.this)[0]);
                            callDetailsModel.setSsid((String) NetworkInfoUtils.getNetworkDetailsInstance().getWifiInfo(CallLogActivity.this)[1]);
                            callDetailsModel.setMobileNetworkType((String) NetworkInfoUtils.getNetworkDetailsInstance().getMobileNetworkInfo(CallLogActivity.this)[0]);

                            databaseHandler.setCallDetails(callDetailsModel);

                            if (callDetailsModelArrayList == null) {
                                callDetailsModelArrayList = new ArrayList<>();
                            }

                            if (callDetailsModelArrayList.size() == 0) {
                                AppComponent.getInstance().globalToast("Call Log is Empty", CallLogActivity.this);
                            } else if(callDetailsModelArrayList != null) {
                                callLogAdapter.update(callDetailsModelArrayList);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onSuccess CellTowerLatLng -- " +
                                cellTowerLatitude + " " + cellTowerLongitude + " " + getUserLocation(cellTowerLatitude, cellTowerLongitude));
                    }

                    @Override
                    public void onError(Exception exception) {
                        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onError ++");

                        AppComponent.getInstance()
                                .globalSnackbar(constraint_layout,
                                        getResources().getString(R.string.something_went_wrong),
                                        Snackbar.LENGTH_SHORT, "",
                                        ContextCompat.getColor(CallLogActivity.this,
                                                android.R.color.holo_red_light));

                        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onError --");
                    }
                });

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "handleUserLocationFromCellTowerInfo --");
    }

    @Override
    public void getCellLocationData(Object... object) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getCellLocationData ++");
        try {
            handleUserLocationFromCellTowerInfo((String) NetworkInfoUtils.getNetworkDetailsInstance().getMobileNetworkInfo(this)[2],
                    (String) NetworkInfoUtils.getNetworkDetailsInstance().getLocationFromCellTower(this)[2],
                    (int) object[1], (int) object[2], (int) NetworkInfoUtils.getNetworkDetailsInstance().getLocationFromCellTower(this)[0],
                    (int) NetworkInfoUtils.getNetworkDetailsInstance().getLocationFromCellTower(this)[1],
                    0, (int) NetworkInfoUtils.getNetworkDetailsInstance().getMobileNetworkInfo(this)[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getCellLocationData --");
    }

    private void getCallDetails() {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getCallDetails ++");

        try {

            Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI,
                    null, null, null, CallLog.Calls.DATE +
                            " DESC limit 1;");
            String callStatus = "";

            if (cursor != null) {

                cursor.moveToLast();
                String phNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                String callType = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                String callDate = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                Date callDayTime = new Date(Long.valueOf(callDate));
//            long timestamp = convertDateToTimestamp(callDayTime);
                String callDuration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));

                int dircode = Integer.parseInt(callType);

                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        callStatus = "OUTGOING";
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        callStatus = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        callStatus = "MISSED";
                        break;
                }

                cursor.close();

                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getCallDetails ** \n" +
                        "phone Number: " + phNumber + "\n callType: " + callType + "\n callDate: " + callDate +
                        "\n callDayTime: " +callDayTime + " \n callDuration: " + callDuration +
                        "\n callStatus: " + callStatus);

                callDetailsModel.setPhoneNumber(phNumber);
                callDetailsModel.setCallStatus(callStatus);
                callDetailsModel.setCallDayAndTime(new SimpleDateFormat().format(callDayTime));
                callDetailsModel.setCallDuration(Integer.parseInt(callDuration));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getCallDetails -- ");
    }
}
