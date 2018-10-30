package com.androiddialer.utils;

import android.content.Context;

import com.androiddialer.dialer.AppComponent;
import com.androiddialer.interfaces.NetworkResponseCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkUtil {

    private final String TAG = NetworkUtil.class.getSimpleName();

    private final String GOOGLE_API_URL = "https://www.googleapis.com/geolocation/v1/geolocate?key=";
    private Context context;
    private MediaType JSON;

    private final String TAG_RADIO_TYPE = "radioType";
    private final String TAG_RADIO_CARRIER = "carrier";
    private final String TAG_CONSIDER_IP = "considerIp";
    private final String TAG_CELL_TOWERS = "cellTowers";
    private final String TAG_CELL_ID = "cellId";
    private final String TAG_LOCATION_AREA_CODE = "locationAreaCode";
    private final String TAG_MOBILE_COUNTRY_CODE = "mobileCountryCode";
    private final String TAG_MOBILE_NETWORK_CODE = "mobileNetworkCode";
    private final String TAG_AGE = "age";
    private final String TAG_SIGNAL_STRENGTH = "signalStrength";
    private final String TAG_TIMING_ADVANCE = "timingAdvance";

    public NetworkUtil(Context context) {
        this.context = context;
        JSON = MediaType.parse("application/json; charset=utf-8");
    }

    public void getLatLngFromCellTowerInfo(String radioType, String carrier, String considerIp,
                                           int cellId, int locationAreaCode, int mobileCountryCode,
                                           int mobileNetworkCode, int age, int signalStrength,
                                           int timingAdvance, NetworkResponseCallback networkResponseCallback) {
        if (AppConstants.LOG)
            AppComponent.getInstance().writeLog(TAG, "getLatLngFromCellTowerInfo ++ ");

        JSONObject latlngRequest = new JSONObject();
        JSONObject cellTowerObject = new JSONObject();

        try {

            JSONArray cellTowersArray = new JSONArray();

            cellTowerObject.put(TAG_CELL_ID, cellId);
            cellTowerObject.put(TAG_LOCATION_AREA_CODE, locationAreaCode);
            cellTowerObject.put(TAG_MOBILE_COUNTRY_CODE, mobileCountryCode);
            cellTowerObject.put(TAG_MOBILE_NETWORK_CODE, mobileNetworkCode);
            cellTowerObject.put(TAG_AGE, age);
            cellTowerObject.put(TAG_SIGNAL_STRENGTH, signalStrength);
            cellTowerObject.put(TAG_TIMING_ADVANCE, timingAdvance);
            cellTowersArray.put(cellTowerObject);

            latlngRequest.put(TAG_RADIO_TYPE, radioType);
            latlngRequest.put(TAG_RADIO_CARRIER, carrier);
            latlngRequest.put(TAG_CONSIDER_IP, considerIp);
            latlngRequest.put(TAG_CELL_TOWERS, cellTowersArray);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (AppConstants.LOG)
            AppComponent.getInstance().writeLog(TAG, "LatLngFromCellTowerInfo URL " + GOOGLE_API_URL);
        if (AppConstants.LOG)
            AppComponent.getInstance().writeLog(TAG, "LatLngFromCellTowerInfo request " + latlngRequest.toString());

        try {

            RequestBody requestBody = RequestBody.create(JSON, latlngRequest.toString());
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(GOOGLE_API_URL + AppConstants.GoogleAPIKey.API_KEY)
                    .post(requestBody)
                    .build();

            AppComponent.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException ioException) {
                    if (AppConstants.LOG)
                        AppComponent.getInstance().writeLog(TAG, "onFailure ++ ");

                    AppComponent.getInstance().getHandler().post(() -> networkResponseCallback.onError(ioException));

                    if (AppConstants.LOG)
                        AppComponent.getInstance().writeLog(TAG, "onFailure -- ");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (AppConstants.LOG)
                        AppComponent.getInstance().writeLog(TAG, "onResponse ++ ");

                    final String stringResponse = response.body().string();

                    if (AppConstants.LOG)
                        AppComponent.getInstance().writeLog(TAG, "LatLngFromCellTowerInfo onResponse ++ " + stringResponse);
                    Object[] returnObject = new Object[3];

                    try {

                        returnObject = new JsonUtil(context).parseGeolocationInfo(new JSONObject(stringResponse));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    final Object[] finalReturnObject = returnObject;
                    AppComponent.getInstance().getHandler().post(() -> networkResponseCallback.onSuccess(finalReturnObject));

                    if (AppConstants.LOG)
                        AppComponent.getInstance().writeLog(TAG, "onResponse -- ");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (AppConstants.LOG)
            AppComponent.getInstance().writeLog(TAG, "getLatLngFromCellTowerInfo -- ");
    }
}
