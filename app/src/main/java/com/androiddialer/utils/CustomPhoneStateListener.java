package com.androiddialer.utils;

import android.content.Context;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

import com.androiddialer.dialer.AppComponent;
import com.androiddialer.interfaces.PhoneStateDataTransfer;

import java.util.List;

public class CustomPhoneStateListener extends PhoneStateListener {

    private final String TAG = CustomPhoneStateListener.class.getSimpleName();

    private PhoneStateDataTransfer phoneStateDataTransfer;
    private Context mContext;

    public CustomPhoneStateListener(Context context) {
        this.mContext = context;
        phoneStateDataTransfer = (PhoneStateDataTransfer) context;
    }

    @Override
    public void onCellInfoChanged(List<CellInfo> cellInfo) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCellInfoChanged ++");
        super.onCellInfoChanged(cellInfo);

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCellInfoChanged --" + cellInfo);
    }

    @Override
    public void onDataActivity(int direction) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onDataActivity ++");
        super.onDataActivity(direction);

        switch (direction) {
            case TelephonyManager.DATA_ACTIVITY_NONE:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onDataActivity: DATA_ACTIVITY_NONE");
                break;
            case TelephonyManager.DATA_ACTIVITY_IN:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onDataActivity: DATA_ACTIVITY_IN");
                break;
            case TelephonyManager.DATA_ACTIVITY_OUT:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onDataActivity: DATA_ACTIVITY_OUT");
                break;
            case TelephonyManager.DATA_ACTIVITY_INOUT:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onDataActivity: DATA_ACTIVITY_INOUT");
                break;
            case TelephonyManager.DATA_ACTIVITY_DORMANT:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onDataActivity: DATA_ACTIVITY_DORMANT");
                break;
            default:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onDataActivity: UNKNOWN " + direction);
                break;
        }

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onDataActivity --");
    }

    @Override
    public void onServiceStateChanged(ServiceState serviceState) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onServiceStateChanged ++");
        super.onServiceStateChanged(serviceState);

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onServiceStateChanged: " + serviceState.toString());
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onServiceStateChanged: getOperatorAlphaLong "
                + serviceState.getOperatorAlphaLong());
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onServiceStateChanged: getOperatorAlphaShort "
                + serviceState.getOperatorAlphaShort());
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onServiceStateChanged: getOperatorNumeric "
                + serviceState.getOperatorNumeric());
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onServiceStateChanged: getIsManualSelection "
                + serviceState.getIsManualSelection());
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                "onServiceStateChanged: getRoaming "
                        + serviceState.getRoaming());

        switch (serviceState.getState()) {
            case ServiceState.STATE_IN_SERVICE:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                        "onServiceStateChanged: STATE_IN_SERVICE");
                break;
            case ServiceState.STATE_OUT_OF_SERVICE:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                        "onServiceStateChanged: STATE_OUT_OF_SERVICE");
                break;
            case ServiceState.STATE_EMERGENCY_ONLY:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                        "onServiceStateChanged: STATE_EMERGENCY_ONLY");
                break;
            case ServiceState.STATE_POWER_OFF:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                        "onServiceStateChanged: STATE_POWER_OFF");
                break;
        }

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onServiceStateChanged --");
    }

    @Override
    public void onCallStateChanged(int state, String phoneNumber) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCallStateChanged ++");
        super.onCallStateChanged(state, phoneNumber);

        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                        "onCallStateChanged: CALL_STATE_IDLE");
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                        "onCallStateChanged: CALL_STATE_RINGING");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                        "onCallStateChanged: CALL_STATE_OFFHOOK");
                break;
            default:
                if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                        "UNKNOWN_STATE: " + state);
                break;
        }

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCallStateChanged --");
    }

    @Override
    public void onCellLocationChanged(CellLocation location) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCellLocationChanged ++");
        super.onCellLocationChanged(location);

        if (location instanceof GsmCellLocation) {
            GsmCellLocation gsmCellLocation = (GsmCellLocation) location;
            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                    "onCellLocationChanged: GsmCellLocation "
                            + gsmCellLocation.toString());
            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                    "onCellLocationChanged: GsmCellLocation getCid "
                    + gsmCellLocation.getCid());
            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                    "onCellLocationChanged: GsmCellLocation getLac -> GSM Location area code "
                    + gsmCellLocation.getLac());
            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                    "onCellLocationChanged: GsmCellLocation getPsc"
                    + gsmCellLocation.getPsc());

            phoneStateDataTransfer.getCellLocationData(gsmCellLocation.toString(),
                    gsmCellLocation.getCid(), gsmCellLocation.getLac(), gsmCellLocation.getPsc());

        } else if (location instanceof CdmaCellLocation) {
            CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) location;
            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                    "onCellLocationChanged: CdmaCellLocation "
                            + cdmaCellLocation.toString());
            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                    "onCellLocationChanged: CdmaCellLocation getBaseStationId "
                            + cdmaCellLocation.getBaseStationId());
            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                    "onCellLocationChanged: CdmaCellLocation getBaseStationLatitude "
                            + cdmaCellLocation.getBaseStationLatitude());
            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                    "onCellLocationChanged: CdmaCellLocation getBaseStationLongitude"
                            + cdmaCellLocation.getBaseStationLongitude());
            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                    "onCellLocationChanged: CdmaCellLocation getNetworkId "
                            + cdmaCellLocation.getNetworkId());
            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                    "onCellLocationChanged: CdmaCellLocation getSystemId "
                            + cdmaCellLocation.getSystemId());
        } else {
            if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG,
                    "onCellLocationChanged: " + location.toString());
        }

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCellLocationChanged --");
    }

    @Override
    public void onCallForwardingIndicatorChanged(boolean cfi) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCallForwardingIndicatorChanged ++");
        super.onCallForwardingIndicatorChanged(cfi);

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onCallForwardingIndicatorChanged -- " +cfi);
    }

    @Override
    public void onMessageWaitingIndicatorChanged(boolean mwi) {
        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onMessageWaitingIndicatorChanged ++");
        super.onMessageWaitingIndicatorChanged(mwi);

        if (AppConstants.LOG) AppComponent.getInstance().writeLog(TAG, "onMessageWaitingIndicatorChanged --" + mwi);
    }
}
