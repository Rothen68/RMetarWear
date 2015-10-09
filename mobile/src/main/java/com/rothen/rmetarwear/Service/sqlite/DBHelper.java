package com.rothen.rmetarwear.Service.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rothen.rmetarwear.Exceptions.SQLiteInsertException;
import com.rothen.rmetarwear.Model.Airport;
import com.rothen.rmetarwear.Model.FollowedAirport;

import java.util.ArrayList;

/**
 * Created by stéphane on 09/10/2015.
 */
public class DBHelper {

    private SQLiteDatabase database = null;
    private DBBuilder builder = null;


    public DBHelper(Context context) {
        builder = new DBBuilder(context);
    }

    public SQLiteDatabase open() {
        database = builder.getReadableDatabase();
        return database;
    }

    public void close() {
        if (database != null)
            database.close();
        database = null;
    }

    public ArrayList<FollowedAirport> getListFollowedAirports() {
        String request = "SELECT * FROM " + DBBuilder.TABLE_FOLLOWED + " INNER JOIN " + DBBuilder.TABLE_AIRPORT + " ON " +
                DBBuilder.TABLE_FOLLOWED + "." + DBBuilder.FOL_AIRPORT_ID + "=" +
                DBBuilder.TABLE_AIRPORT + "." + DBBuilder.AIR_ID +
                " ORDER BY " + DBBuilder.TABLE_FOLLOWED + "." + DBBuilder.FOL_ACT_NOTIF + "," +
                DBBuilder.TABLE_AIRPORT + "." + DBBuilder.AIR_OACI + ";";

        Cursor c;
        ArrayList<FollowedAirport> arrayList = new ArrayList<>();

        if (database == null)
            open();
        c = database.rawQuery(request, null);
        while (c.moveToNext()) {
            arrayList.add(getFollowedAirportByCursor(c));
        }
        c.close();

        return arrayList;
    }

    private FollowedAirport getFollowedAirportByCursor(Cursor c) {
        Airport airport = new Airport(
                c.getInt(c.getColumnIndex(DBBuilder.TABLE_AIRPORT + "." + DBBuilder.AIR_ID)),
                c.getString(c.getColumnIndex(DBBuilder.TABLE_AIRPORT + "." + DBBuilder.AIR_OACI)),
                c.getString(c.getColumnIndex(DBBuilder.TABLE_AIRPORT + "." + DBBuilder.AIR_CITY)),
                c.getString(c.getColumnIndex(DBBuilder.TABLE_AIRPORT + "." + DBBuilder.AIR_STATE)),
                c.getString(c.getColumnIndex(DBBuilder.TABLE_AIRPORT + "." + DBBuilder.AIR_COUNTRY)),
                c.getFloat(c.getColumnIndex(DBBuilder.TABLE_AIRPORT + "." + DBBuilder.AIR_LAT)),
                c.getFloat(c.getColumnIndex(DBBuilder.TABLE_AIRPORT + "." + DBBuilder.AIR_LON)),
                c.getFloat(c.getColumnIndex(DBBuilder.TABLE_AIRPORT + "." + DBBuilder.AIR_ELEV)),
                c.getInt(c.getColumnIndex(DBBuilder.TABLE_AIRPORT + "." + DBBuilder.AIR_SITETYPE)));
        FollowedAirport followed = new FollowedAirport(
                c.getInt(c.getColumnIndex(DBBuilder.TABLE_FOLLOWED + "." + DBBuilder.FOL_ID)),
                airport,
                (c.getInt(c.getColumnIndex(DBBuilder.TABLE_FOLLOWED + "." + DBBuilder.FOL_ACT_NOTIF)) > 0),
                (c.getInt(c.getColumnIndex(DBBuilder.TABLE_FOLLOWED + "." + DBBuilder.FOL_NOTIF_METAR)) > 0),
                (c.getInt(c.getColumnIndex(DBBuilder.TABLE_FOLLOWED + "." + DBBuilder.FOL_NOTIF_TAF)) > 0),
                c.getInt(c.getColumnIndex(DBBuilder.TABLE_FOLLOWED + "." + DBBuilder.FOL_UPDATE_FREQ)));
        return followed;
    }


    public void addFollowedAirport(FollowedAirport item) throws SQLiteInsertException {

        if (database == null)
            open();
        ContentValues map = new ContentValues();
        Airport airport = item.airport;
        map.put(DBBuilder.AIR_OACI, airport.OACICode);
        map.put(DBBuilder.AIR_CITY, airport.city);
        map.put(DBBuilder.AIR_STATE, airport.state);
        map.put(DBBuilder.AIR_COUNTRY, airport.country);
        map.put(DBBuilder.AIR_LAT, airport.latitude);
        map.put(DBBuilder.AIR_LON, airport.longitude);
        map.put(DBBuilder.AIR_ELEV, airport.elevation);
        map.put(DBBuilder.AIR_SITETYPE, airport.getSiteType());
        long result = 0;
        try {
            result = database.insertOrThrow(
                    DBBuilder.TABLE_AIRPORT,
                    null,
                    map);
        } catch (Exception ex) {

        }
        if (result == -1) {
            throw new SQLiteInsertException(DBBuilder.TABLE_AIRPORT + " : " + map.toString());
        }

        Cursor cursor = database.rawQuery("SELECT " + DBBuilder.AIR_ID +
                " FROM " + DBBuilder.TABLE_AIRPORT +
                " WHERE " + DBBuilder.AIR_OACI + " = '" + airport.OACICode + "' ;", null);
        cursor.moveToNext();
        int indexAirport = cursor.getInt(cursor.getColumnIndex(DBBuilder.AIR_ID));

        map = new ContentValues();
        map.put(DBBuilder.FOL_AIRPORT_ID, indexAirport);
        map.put(DBBuilder.FOL_ACT_NOTIF, (item.isNotifActive ? 1 : 0));
        map.put(DBBuilder.FOL_NOTIF_METAR, (item.notifMetar ? 1 : 0));
        map.put(DBBuilder.FOL_NOTIF_TAF, (item.notifTaf ? 1 : 0));
        map.put(DBBuilder.FOL_UPDATE_FREQ, item.updateFrequency);
        result = database.insert(
                DBBuilder.TABLE_FOLLOWED,
                null,
                map);
        if (result == -1) {
            throw new SQLiteInsertException(DBBuilder.TABLE_FOLLOWED + " : " + map.toString());
        }
    }

    public FollowedAirport getFollowedAirport(String OACICode) {

        if (database == null)
            open();
        String request = "SELECT * FROM " + DBBuilder.TABLE_FOLLOWED + " INNER JOIN " + DBBuilder.TABLE_AIRPORT + " ON " +
                DBBuilder.TABLE_FOLLOWED + "." + DBBuilder.FOL_AIRPORT_ID + "=" +
                DBBuilder.TABLE_AIRPORT + "." + DBBuilder.AIR_ID +
                " WHERE " + DBBuilder.TABLE_AIRPORT + "." + DBBuilder.AIR_OACI +
                " = '" + OACICode + "';";

        Cursor c;
        ArrayList<FollowedAirport> arrayList = new ArrayList<>();

        if (database == null)
            open();
        c = database.rawQuery(request, null);
        c.moveToNext();
        FollowedAirport followed = getFollowedAirportByCursor(c);
        c.close();

        return followed;
    }

    public void removeFollowedAirport(FollowedAirport followedAirport) {
        int number = database.delete(DBBuilder.TABLE_AIRPORT, DBBuilder.AIR_ID + "='" + followedAirport.airport.id + "'", null);

        number = database.delete(DBBuilder.TABLE_FOLLOWED, DBBuilder.FOL_ID + "='" + followedAirport.id + "'", null);

    }

    public void updateFollowedAirport(FollowedAirport followedAirport) {

        removeFollowedAirport(followedAirport);

        addFollowedAirport(followedAirport);
    }

}
