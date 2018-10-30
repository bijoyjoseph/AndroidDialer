package com.androiddialer.utils;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.androiddialer.R;
import com.androiddialer.dialer.AppComponent;

import java.util.List;

public class NetworkInfoUtils {

    private static final String TAG = NetworkInfoUtils.class.getSimpleName();

    private static NetworkInfoUtils networkInfoUtils = null;

    public static synchronized NetworkInfoUtils getNetworkDetailsInstance() {
        if (networkInfoUtils == null) {
            networkInfoUtils = new NetworkInfoUtils();
        }
        return networkInfoUtils;
    }

    public NetworkInfo getInternetStatus(Context context) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getInternetStatus ++");
        ConnectivityManager connectivityManager = null;
        try {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getInternetStatus --");
        return connectivityManager.getActiveNetworkInfo();
    }

    public Object[] getWifiInfo(Context context) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getWifiInfo ++");
//        int netlevel = 0;
        String wifiSignalStrength = "";
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().
                getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (AppComponent.getInstance().isConnectedToWifi(context)) {
            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "isConnectedToWifi ++");
            if (wifiInfo.getRssi() >= AppConstants.RssiSignalStrength.EXCELLENT_SIGNAL &&
                    wifiInfo.getRssi() <= AppConstants.RssiSignalStrength.MAX_SIGNAL) {
                wifiSignalStrength = context.getResources().getString(R.string.excellent_signal_strength);
            } else if (wifiInfo.getRssi() >= AppConstants.RssiSignalStrength.GOOD_SIGNAL &&
                    wifiInfo.getRssi() <= AppConstants.RssiSignalStrength.EXCELLENT_SIGNAL) {
                wifiSignalStrength = context.getResources().getString(R.string.good_signal_strength);
            } else if (wifiInfo.getRssi() >= AppConstants.RssiSignalStrength.WEAK_SIGNAL &&
                    wifiInfo.getRssi() <= AppConstants.RssiSignalStrength.GOOD_SIGNAL) {
                wifiSignalStrength = context.getResources().getString(R.string.weak_signal_strength);
            } else if (wifiInfo.getRssi() >= AppConstants.RssiSignalStrength.UNRELIABLE_SIGNAL &&
                    wifiInfo.getRssi() <= AppConstants.RssiSignalStrength.WEAK_SIGNAL) {
                wifiSignalStrength = context.getResources().getString(R.string.unreliable_signal_strength);
            }

            /**
             * use below code if you want the signal strengths in connectivity levels
             */
//            int numberOfLevels = 5;
//            int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);

            if (AppConstants.LOG)
                AppComponent.getInstance().writeLog(TAG, "isConnectedToWifi -- " + wifiInfo.getRssi() + " internetInfo: " + wifiSignalStrength);
        } /*else if(isConnectedToMobileData(context)) {
            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "isConnectedToMobileData ++");

            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "isConnectedToMobileData --");
        }*/

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getWifiInfo --");

        /**
         * from 8.0 onwards we won't get SSID unless the GPS is turned on
         */

        Object[] returnObject = new Object[2];
        returnObject[0] = wifiSignalStrength;
        returnObject[1] = wifiInfo.getSSID();

        return returnObject;
    }

    public Object[] getMobileNetworkInfo(Context context) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getMobileNetworkInfo ++");

        String mobileNetworkConnection;
        int signalStrength = 0;
        String radioType = "";

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        switch (telephonyManager.getNetworkType()) {

            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                mobileNetworkConnection = "2g";
                break;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:

            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                mobileNetworkConnection = "3g";
                break;
            case TelephonyManager.NETWORK_TYPE_LTE:
                mobileNetworkConnection = "4g";
                break;
            default:
                mobileNetworkConnection = "Notfound";

        }


        if (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_ABSENT) {
        } else {

//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//            }
//
////            List<CellInfo> cellInfoList = telephonyManager.getAllCellInfo();
//
//            try {
//
//                for (CellInfo cellInfo : cellInfoList) {
//                    if (cellInfo instanceof CellInfoLte) {
//                        // cast to CellInfoLte and call all the CellInfoLte methods you need
//                        signalStrength = ((CellInfoLte) cellInfo).getCellSignalStrength().getDbm();
//                    }
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                signalStrength = telephonyManager.getSignalStrength().getCdmaDbm();
            } else {
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);

                List<CellInfo> cellInfoList = telephonyManager.getAllCellInfo();

                if(cellInfoList != null) {
                    for (int i = 0 ; i<cellInfoList.size(); i++) {
                        if(cellInfoList.get(i).isRegistered()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                if (cellInfoList.get(i) instanceof CellInfoWcdma) {
                                    CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) telephonyManager.getAllCellInfo().get(0);
                                    CellSignalStrengthWcdma cellSignalStrengthWcdma = cellInfoWcdma.getCellSignalStrength();
                                    signalStrength = cellSignalStrengthWcdma.getDbm();
                                }
                            } else if (cellInfoList.get(i) instanceof CellInfoGsm) {
                                CellInfoGsm cellInfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
                                CellSignalStrengthGsm cellSignalStrengthGsm = cellInfogsm.getCellSignalStrength();
                                signalStrength = cellSignalStrengthGsm.getDbm();
                            } else if (cellInfoList.get(i) instanceof CellInfoLte) {
                                CellInfoLte cellInfoLte = (CellInfoLte) telephonyManager.getAllCellInfo().get(0);
                                CellSignalStrengthLte cellSignalStrengthLte = cellInfoLte.getCellSignalStrength();
                                signalStrength = cellSignalStrengthLte.getDbm();
                            } else if(cellInfoList.get(i) instanceof CellInfoCdma) {
                                CellInfoCdma cellInfoCdma = (CellInfoCdma) telephonyManager.getAllCellInfo().get(0);
                                CellSignalStrengthCdma cellSignalStrengthCdma = cellInfoCdma.getCellSignalStrength();
                                signalStrength = cellSignalStrengthCdma.getDbm();
                            }
                        }
                    }
                }
            }
        }

        /**
         *  get radio type
         */

        switch (telephonyManager.getPhoneType()) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                radioType = "CDMA";
                break;

            case (TelephonyManager.PHONE_TYPE_GSM):
                radioType = "GSM";
                break;

            case (TelephonyManager.PHONE_TYPE_SIP):
                radioType = "SIP";
                break;

            case (TelephonyManager.PHONE_TYPE_NONE):
                radioType = "NONE";
                break;
        }

        Object[] returnObject = new Object[3];

        returnObject[0] = mobileNetworkConnection;
        returnObject[1] = signalStrength;
        returnObject[2] = radioType;

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "getMobileNetworkInfo -- ");

        return returnObject;
    }

    public Object[] getLocationFromCellTower(Context context) {
        if (AppConstants.LOG)
            AppComponent.getInstance().writeLog(TAG, "getLocationFromCellTower ++");
        int mobileCountryCode;
        int mobileNetworkCode;

//        int cellId;
//        int cellLoc;

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
//                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//
//        GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
//
//        /**
//         * you need GPS on for the below code to work on 8.0 and above. But it has been substituted with
//         * the methods in CustomPhoneStateListener Class which doesn't require GPS to be on.
//         */
//
////        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
////                AppComponent.getInstance().isGpsEnabled() &&
////                cellLocation != null) {
////
////            cellId = cellLocation.getCid();
////            cellLoc = cellLocation.getLac();
////
////            if (AppConstants.LOG)
////                AppComponent.getInstance().writeLog(TAG, "networkDetails --> " +
////                        " cell location-> " + cellLocation.toString() + " GSM Cell ID-> " + cellId +
////                        " GSM location code " + cellLoc);
////        } else {

        mobileCountryCode = Integer.parseInt(telephonyManager.getNetworkOperator().substring(0,3));
        mobileNetworkCode = Integer.parseInt(telephonyManager.getNetworkOperator().substring(3));

        //}

        if (AppConstants.LOG)
            AppComponent.getInstance().writeLog(TAG, "networkOperatorInfo ** - > " +
                    " MobileCountryCode - > " + mobileCountryCode + " MobileNetworkCode -> " + mobileNetworkCode + " carrier-> " +
                    telephonyManager.getNetworkOperatorName() + " networkType-> " + telephonyManager.getNetworkType());

        Object[] returnObject = new Object[3];
        returnObject[0] = mobileCountryCode;
        returnObject[1] = mobileNetworkCode;
        returnObject[2] = telephonyManager.getNetworkOperatorName();

        if (AppConstants.LOG)
            AppComponent.getInstance().writeLog(TAG, "getLocationFromCellTower -- ");

        return returnObject;
    }

}
