package com.androiddialer.utils;

import android.content.Context;

import com.androiddialer.dialer.AppComponent;

import org.json.JSONObject;

public class JsonUtil {

    private final String TAG = JsonUtil.class.getSimpleName();

    private final String TAG_LOCATION = "location";
    private final String TAG_LATITUDE = "lat";
    private final String TAG_LONGITUDE = "lng";
    private final String TAG_ACCURACY = "accuracy";

    private Context mContext;

    public JsonUtil(Context context) {
        this.mContext = context;
    }

    public Object[] parseGeolocationInfo(JSONObject cellTowerResponse) throws Exception {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "parseGeolocationInfo ++");

        double latitude = 0.0;
        double longitude = 0.0;
        String accuracy = "";

        if(cellTowerResponse.has(TAG_LOCATION)) {
            JSONObject locationObject = cellTowerResponse.getJSONObject(TAG_LOCATION);
            if(locationObject.has(TAG_LATITUDE) && locationObject.has(TAG_LONGITUDE)) {
                latitude = locationObject.getDouble(TAG_LATITUDE);
                longitude = locationObject.getDouble(TAG_LONGITUDE);
            }
        }

        if(cellTowerResponse.has(TAG_ACCURACY)) {
            try {
                String temp = cellTowerResponse.getString(TAG_ACCURACY);
                if (temp != null && temp.length() != 0 && !temp.equals("null"))
                accuracy = temp;
            } catch (Exception e) {
                accuracy = "";
            }
        }

        Object[] returnObject = new Object[3];
        returnObject[0] = latitude;
        returnObject[1] = longitude;
        returnObject[2] = accuracy;

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "parseGeolocationInfo -- ");

        return returnObject;
    }
}
