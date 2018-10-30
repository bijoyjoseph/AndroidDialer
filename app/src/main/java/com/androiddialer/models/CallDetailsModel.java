package com.androiddialer.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CallDetailsModel implements Parcelable {

    private boolean isConnectedToInternet;
    private boolean isConnectedToMobileInternet;
    private boolean isConnectedToWifi;
    private String wifiSignalStrength;
    private String ssid;
    private String mobileNetworkType;
    private int mobileNetworkSignalStrength;
    private String radioType;
    private String carrier;
    private String gpsLocation;
    private String cellTowerLocation;
    private String phoneNumber;
    private String callStatus;
    private int callDuration;
    private String callDayAndTime;

    public CallDetailsModel() {
        boolean isConnectedToInternet = false;
        boolean isConnectedToMobileInternet = false;
        boolean isConnectedToWifi = false;
        String wifiSignalStrength = "";
        String ssid = "";
        String mobileNetworkType = "";
        int mobileNetworkSignalStrength = 0;
        String radioType = "";
        String carrier = "";
        String gpsLocation = "";
        String cellTowerLocation = "";
        String phoneNumber = "";
        String callStatus = "";
        int callDuration = 0;
        String callDayAndTime = "";
    }


    protected CallDetailsModel(Parcel in) {
        isConnectedToInternet = in.readByte() != 0;
        isConnectedToMobileInternet = in.readByte() != 0;
        isConnectedToWifi = in.readByte() != 0;
        wifiSignalStrength = in.readString();
        ssid = in.readString();
        mobileNetworkType = in.readString();
        mobileNetworkSignalStrength = in.readInt();
        radioType = in.readString();
        carrier = in.readString();
        gpsLocation = in.readString();
        cellTowerLocation = in.readString();
        phoneNumber = in.readString();
        callStatus = in.readString();
        callDuration = in.readInt();
        callDayAndTime = in.readString();
    }

    public static final Creator<CallDetailsModel> CREATOR = new Creator<CallDetailsModel>() {
        @Override
        public CallDetailsModel createFromParcel(Parcel in) {
            return new CallDetailsModel(in);
        }

        @Override
        public CallDetailsModel[] newArray(int size) {
            return new CallDetailsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isConnectedToInternet ? 1 : 0));
        dest.writeByte((byte) (isConnectedToMobileInternet ? 1 : 0));
        dest.writeByte((byte) (isConnectedToWifi ? 1 : 0));
        dest.writeString(wifiSignalStrength);
        dest.writeString(ssid);
        dest.writeString(mobileNetworkType);
        dest.writeInt(mobileNetworkSignalStrength);
        dest.writeString(radioType);
        dest.writeString(carrier);
        dest.writeString(gpsLocation);
        dest.writeString(cellTowerLocation);
        dest.writeString(phoneNumber);
        dest.writeString(callStatus);
        dest.writeInt(callDuration);
        dest.writeString(callDayAndTime);
    }

    public boolean isConnectedToInternet() {
        return isConnectedToInternet;
    }

    public void setConnectedToInternet(boolean connectedToInternet) {
        isConnectedToInternet = connectedToInternet;
    }

    public boolean isConnectedToMobileInternet() {
        return isConnectedToMobileInternet;
    }

    public void setConnectedToMobileInternet(boolean connectedToMobileInternet) {
        isConnectedToMobileInternet = connectedToMobileInternet;
    }

    public boolean isConnectedToWifi() {
        return isConnectedToWifi;
    }

    public void setConnectedToWifi(boolean connectedToWifi) {
        isConnectedToWifi = connectedToWifi;
    }

    public String getWifiSignalStrength() {
        return wifiSignalStrength;
    }

    public void setWifiSignalStrength(String wifiSignalStrength) {
        this.wifiSignalStrength = wifiSignalStrength;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getMobileNetworkType() {
        return mobileNetworkType;
    }

    public void setMobileNetworkType(String mobileNetworkType) {
        this.mobileNetworkType = mobileNetworkType;
    }

    public int getMobileNetworkSignalStrength() {
        return mobileNetworkSignalStrength;
    }

    public void setMobileNetworkSignalStrength(int mobileNetworkSignalStrength) {
        this.mobileNetworkSignalStrength = mobileNetworkSignalStrength;
    }

    public String getRadioType() {
        return radioType;
    }

    public void setRadioType(String radioType) {
        this.radioType = radioType;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public String getCellTowerLocation() {
        return cellTowerLocation;
    }

    public void setCellTowerLocation(String cellTowerLocation) {
        this.cellTowerLocation = cellTowerLocation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public int getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(int callDuration) {
        this.callDuration = callDuration;
    }

    public String getCallDayAndTime() {
        return callDayAndTime;
    }

    public void setCallDayAndTime(String callDayAndTime) {
        this.callDayAndTime = callDayAndTime;
    }
}
