package com.gh_hitech.devicecontroller.utils;

/**
 * @author yijigu
 */
public class Constants {
//    public static final String TEST_SERVER_URL = "http://192.168.1.164:3001";
    public static final String TEST_SERVER_URL = "http://192.168.1.205:3001";
    public static final String RELEASE_SERVER_URL = "http://47.107.181.229:3001";
    public static final boolean TEST_MODE = true;
    public static final Long NO_KIOSK = -1L;
    public static final Long NO_SELECTION = -1L;
    public static final Long DELETE_DEVICE = 1L;
    public static final Long DELETE_PAVILION = 2L;
    public static final String NULL_STRING = "";
    public static String SERVER_URL;
    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;

    public static final String getServerUrl() {
        if (TEST_MODE) {
            SERVER_URL = TEST_SERVER_URL;
        } else {
            SERVER_URL = RELEASE_SERVER_URL;
        }
        return SERVER_URL;
    }

    public static final int LINE_ON = 1;
    public static final int LINE_OFF = 0;
}
