package com.rothen.rmetarwear;

import android.app.Application;

import com.rothen.rmetarwear.Service.sqlite.DBHelper;

/**
 * Created by stéphane on 08/10/2015.
 */
public class CustomApplication extends Application {

    public DBHelper databaseHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseHelper = new DBHelper(getApplicationContext());
    }
}
