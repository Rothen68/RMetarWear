package com.rothen.rmetarwear.Controller;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rothen.rmetarwear.CustomApplication;
import com.rothen.rmetarwear.Service.sqlite.DBHelper;

/**
 * Created by stéphane on 09/10/2015.
 */
public class BaseActivity extends AppCompatActivity {
    protected CustomApplication application;
    protected DBHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application app = this.getApplication();
        application = (CustomApplication) app;
        databaseHelper = application.databaseHelper;
    }
}
