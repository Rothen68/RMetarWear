package com.rothen.rmetarwear.Service.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by stéphane on 08/10/2015.
 */
public class DBBuilder extends SQLiteOpenHelper {

    public static final String TABLE_AIRPORT = "AIRPORT";
    public static final String AIR_ID = "AIR_ID";
    public static final String AIR_OACI = "AIR_OACI";
    public static final String AIR_CITY = "AIR_CITY";
    public static final String AIR_STATE = "AIR_STATE";
    public static final String AIR_COUNTRY = "AIR_COUNTRY";
    public static final String AIR_LAT = "AIR_LAT";
    public static final String AIR_LON = "AIR_LON";
    public static final String AIR_ELEV = "AIR_ELEV";
    public static final String AIR_SITETYPE = "AIR_SITETYPE";
    public static final String AIRPORT_TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_AIRPORT + ";";
    public static final String TABLE_MESSAGE = "MESSAGE";
    public static final String MESS_ID = "MESS_ID";
    public static final String MESS_DATE = "MESS_DATE";
    public static final String MESS_AIRPORT_ID = "MESS_AIRPORT_ID";
    public static final String MESS_DATA = "MESS_DATA";
    public static final String MESSAGE_TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_MESSAGE + ";";
    public static final String TABLE_FOLLOWED = "FOLLOWED";
    public static final String FOL_ID = "FOL_ID";
    public static final String FOL_AIRPORT_ID = "FOL_AIRPORT_ID";
    public static final String FOL_ACT_NOTIF = "FOL_ACT_NOTIF";
    public static final String FOL_NOTIF_METAR = "FOL_NOTIF_METAR";
    public static final String FOL_NOTIF_TAF = "FOL_NOTIF_TAF";
    public static final String FOL_UPDATE_FREQ = "FOL_UPDATE_FREQ";
    public static final String FOLLOWED_TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_FOLLOWED + ";";
    public static final String DB_NAME = "RMETARWEAR.db";
    public static final int DB_VERSION = 1;
    private static final String CREATE_AIRPORT =
            "CREATE TABLE " + TABLE_AIRPORT + "(" +
                    AIR_ID + " INTEGER PRIMARY KEY, " +
                    AIR_OACI + " TEXT UNIQUE, " +
                    AIR_CITY + " TEXT, " +
                    AIR_STATE + " TEXT, " +
                    AIR_COUNTRY + " TEXT, " +
                    AIR_LAT + " REAL," +
                    AIR_LON + " REAL," +
                    AIR_ELEV + " REAL," +
                    AIR_SITETYPE + " INTEGER);";
    private static final String CREATE_MESSAGE =
            "CREATE TABLE " + TABLE_MESSAGE + "(" +
                    MESS_ID + " INTEGER PRIMARY KEY, " +
                    MESS_DATE + " DATETIME, " +
                    MESS_AIRPORT_ID + " INTEGER, " +
                    MESS_DATA + " TEXT);";
    private static final String CREATE_FOLLOWED =
            "CREATE TABLE " + TABLE_FOLLOWED + "(" +
                    FOL_ID + " INTEGER PRIMARY KEY, " +
                    FOL_AIRPORT_ID + " TEXT, " +
                    FOL_ACT_NOTIF + " INTEGER, " +
                    FOL_NOTIF_METAR + " INTEGER, " +
                    FOL_NOTIF_TAF + " INTEGER, " +
                    FOL_UPDATE_FREQ + " INTEGER);";

    public DBBuilder(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBBuilder(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        String s = CREATE_AIRPORT;

        s = CREATE_FOLLOWED;

        s = CREATE_MESSAGE;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_AIRPORT);
        } catch (SQLiteException ex) {
            Log.d("SQL", "ERROR CREATE AIRPORT TABLE :" + ex.getMessage());
        }
        try {
            db.execSQL(CREATE_FOLLOWED);
        } catch (SQLiteException ex) {
            Log.d("SQL", "ERROR CREATE FOLLOWED TABLE :" + ex.getMessage());
        }
        try {
            db.execSQL(CREATE_MESSAGE);
        } catch (SQLiteException ex) {
            Log.d("SQL", "ERROR CREATE MESSAGE TABLE :" + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(AIRPORT_TABLE_DROP);
        db.execSQL(FOLLOWED_TABLE_DROP);
        db.execSQL(MESSAGE_TABLE_DROP);
        onCreate(db);
    }
}
