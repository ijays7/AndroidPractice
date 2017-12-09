package com.ijays.apolo;

import android.os.Environment;

/**
 * Created by ijaysdev on 25/11/2017.
 */

public class AppConstants {
    public static final String ROOT_PATH = Environment.getExternalStorageDirectory().getPath();
    public static final String MOCHA_PATH = ROOT_PATH + "/mochaCache/";
}
