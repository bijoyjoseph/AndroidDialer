package com.androiddialer.databaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androiddialer.dialer.AppComponent;
import com.androiddialer.models.CallDetailsModel;
import com.androiddialer.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final String TAG = DatabaseHandler.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "callDetailsManager";
    private static final String TABLE_CALL_DETAILS = "callDetails";

    private static final String KEY_ID = "id";
    private static final String KEY_IS_INTERNET_CONNECTED = "isConnectedToInternet";
    private static final String KEY_IS_CTD_MOB_INTERNET = "isConnectedToMobileInternet";
    private static final String KEY_IS_WIFI_CONNECTED = "isConnectedToWifi";
    private static final String KEY_WIFI_SIGNAL_STRENGTH = "wifiSignalStrength";
    private static final String KEY_SSID = "ssid";
    private static final String KEY_MOBILE_NETWORK_TYPE = "mobileNetworkType";
    private static final String KEY_MOBILE_NETWORK_SIGNAL_STRENGTH = "mobileNetworkSignalStrength";
    private static final String KEY_RADIO_TYPE = "radioType";
    private static final String KEY_CARRIER = "carrier";
    private static final String KEY_GPS_LOCATION = "gpsLocation";
    private static final String KEY_CELL_TOWER_LOCATION = "cellTowerLocation";
    private static final String KEY_PHONE_NUMBER = "phoneNumber";
    private static final String KEY_CALL_STATUS = "callStatus";
    private static final String KEY_CALL_DURATION = "callDuration";
    private static final String KEY_CALL_DAY_TIME = "callDayAndTime";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCreate ++");

        String CREATE_CALL_DETAILS_TABLE = "CREATE TABLE " + TABLE_CALL_DETAILS + "(" + KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_IS_INTERNET_CONNECTED + " INTEGER," +
                KEY_IS_CTD_MOB_INTERNET + " INTEGER," + KEY_IS_WIFI_CONNECTED + " INTEGER," +
                KEY_WIFI_SIGNAL_STRENGTH + " TEXT," + KEY_SSID + " TEXT," + KEY_MOBILE_NETWORK_TYPE + " TEXT," +
                KEY_MOBILE_NETWORK_SIGNAL_STRENGTH + " INTEGER," +
                KEY_RADIO_TYPE + " TEXT," + KEY_CARRIER + " TEXT," + KEY_GPS_LOCATION + " TEXT," +
                KEY_CELL_TOWER_LOCATION + " TEXT," + KEY_PHONE_NUMBER + " TEXT," + KEY_CALL_STATUS + " TEXT," +
                KEY_CALL_DURATION + " INTEGER," + KEY_CALL_DAY_TIME + " TEXT" + ")";

        db.execSQL(CREATE_CALL_DETAILS_TABLE);

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCreate --");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onUpgrade ++");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALL_DETAILS);
        onCreate(db);

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onUpgrade --");
    }

    public void setCallDetails(CallDetailsModel callDetailsModel) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "addCallDetails ++");

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if(callDetailsModel.isConnectedToInternet()) {
            contentValues.put(KEY_IS_INTERNET_CONNECTED, 1);
        } else {
            contentValues.put(KEY_IS_INTERNET_CONNECTED, 0);
        }

        if(callDetailsModel.isConnectedToMobileInternet()) {
            contentValues.put(KEY_IS_CTD_MOB_INTERNET, 1);
        } else {
            contentValues.put(KEY_IS_CTD_MOB_INTERNET, 0);
        }

        if(callDetailsModel.isConnectedToWifi()) {
            contentValues.put(KEY_IS_WIFI_CONNECTED, 1);
        } else {
            contentValues.put(KEY_IS_WIFI_CONNECTED, 0);
        }

        contentValues.put(KEY_WIFI_SIGNAL_STRENGTH, callDetailsModel.getWifiSignalStrength());
        contentValues.put(KEY_SSID, callDetailsModel.getSsid());
        contentValues.put(KEY_MOBILE_NETWORK_TYPE, callDetailsModel.getMobileNetworkType());
        contentValues.put(KEY_MOBILE_NETWORK_SIGNAL_STRENGTH, callDetailsModel.getMobileNetworkSignalStrength());
        contentValues.put(KEY_RADIO_TYPE, callDetailsModel.getRadioType());
        contentValues.put(KEY_CARRIER, callDetailsModel.getCarrier());
        contentValues.put(KEY_GPS_LOCATION, callDetailsModel.getGpsLocation());
        contentValues.put(KEY_CELL_TOWER_LOCATION, callDetailsModel.getCellTowerLocation());
        contentValues.put(KEY_PHONE_NUMBER, callDetailsModel.getPhoneNumber());
        contentValues.put(KEY_CALL_STATUS, callDetailsModel.getCallStatus());
        contentValues.put(KEY_CALL_DURATION, callDetailsModel.getCallDuration());
        contentValues.put(KEY_CALL_DAY_TIME, callDetailsModel.getCallDayAndTime());

        sqLiteDatabase.insertWithOnConflict(TABLE_CALL_DETAILS, null, contentValues,
                SQLiteDatabase.CONFLICT_REPLACE);
        sqLiteDatabase.close();

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "addCallDetails --");
    }

    public List<CallDetailsModel> getAllCallsList() {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getAllCallsList ++");

        List<CallDetailsModel> callDetailsModelArrayList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CALL_DETAILS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CallDetailsModel callDetailsModel = new CallDetailsModel();

                if(cursor.getInt(1) > 0) {
                    callDetailsModel.setConnectedToInternet(true);
                } else if(cursor.getInt(1) < 1) {
                    callDetailsModel.setConnectedToInternet(false);
                }

                if(cursor.getInt(2) > 0) {
                    callDetailsModel.setConnectedToMobileInternet(true);
                } else if(cursor.getInt(2) < 1) {
                    callDetailsModel.setConnectedToMobileInternet(false);
                }

                if(cursor.getInt(3) > 0) {
                    callDetailsModel.setConnectedToWifi(true);
                } else if(cursor.getInt(3) < 1) {
                    callDetailsModel.setConnectedToWifi(false);
                }

                callDetailsModel.setWifiSignalStrength(cursor.getString(4));
                callDetailsModel.setSsid(cursor.getString(5));
                callDetailsModel.setMobileNetworkType(cursor.getString(6));
                callDetailsModel.setMobileNetworkSignalStrength(cursor.getInt(7));
                callDetailsModel.setRadioType(cursor.getString(8));
                callDetailsModel.setCarrier(cursor.getString(9));
                callDetailsModel.setGpsLocation(cursor.getString(10));
                callDetailsModel.setCellTowerLocation(cursor.getString(11));
                callDetailsModel.setPhoneNumber(cursor.getString(12));
                callDetailsModel.setCallStatus(cursor.getString(13));
                callDetailsModel.setCallDuration(cursor.getInt(14));
                callDetailsModel.setCallDayAndTime(cursor.getString(15));

                callDetailsModelArrayList.add(callDetailsModel);

            } while (cursor.moveToNext());
        }

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getAllCallsList --");

        return callDetailsModelArrayList;
    }
}
