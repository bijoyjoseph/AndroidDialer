package com.androiddialer.utils;

public class AppConstants {

    public static final boolean LOG = true;

    public static final int MULTIPLE_PERMISSIONS = 0x01;

    // RSSI -Received Signal Strength Indicator (usually 0-60 or 0-255) signal strengths

    public interface RssiSignalStrength {
        int MAX_SIGNAL = -30;
        int EXCELLENT_SIGNAL = -50;
        int GOOD_SIGNAL = -60;
        int WEAK_SIGNAL = -70;
        int UNRELIABLE_SIGNAL = -90;
    }

    public interface GoogleAPIKey {

        /**
         * you can use google services to get your cell tower location,
         * more info here: https://developers.google.com/maps/documentation/geolocation/intro
         * or
         * you can go with OpenCellId or services from Siemens or Nokia
         */

        String API_KEY = "your API key";
    }

}
