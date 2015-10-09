package com.rothen.rmetarwear.Service.web;

import android.util.Log;

import com.rothen.rmetarwear.Model.Airport;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by stéphane on 09/10/2015.
 */
public class AirportWebService {

    private static String webServiceUrl = "https://www.aviationweather.gov/adds/dataserver_current/httpparam?dataSource=stations&requestType=retrieve&format=xml&stationString=";


    XmlPullParser parser;

    public AirportWebService() {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            parser = factory.newPullParser();
        } catch (Exception e) {
            Log.d("XML", "Error initialising parser");
        }
    }

    public ArrayList<Airport> loadAirportsWithOACICode(String OACICode) throws Exception {
        URL url = new URL(webServiceUrl + OACICode);
        InputStream is = url.openConnection().getInputStream();
        parser.setInput(is, null);
        ArrayList<Airport> lstAirport = new ArrayList<>();

        while (parser.nextTag() != XmlPullParser.END_DOCUMENT) {
            if (parser.getName().equals("Station") && !parser.isEmptyElementTag()) {
                lstAirport.add(readAirportData(parser));
            }
        }
    }

    private Airport readAirportData(XmlPullParser parser) throws Exception {
        String OACI = "";
        String city = "";
        String state = "";
        String country = "";
        float lat = 0;
        float lon = 0;
        float elev = 0;
        boolean metar = false;
        boolean taf = false;


        while (!((parser.nextTag() == XmlPullParser.END_TAG) && (parser.getName().equals("Station")))) {
            if (parser.getName().equals("station_id")) {
                OACI = parser.nextText();
            }
            if (parser.getName().equals("site")) {
                city = parser.nextText();
            }
            if (parser.getName().equals("state")) {
                state = parser.nextText();
            }
            if (parser.getName().equals("country")) {
                country = parser.nextText();
            }
            if (parser.getName().equals("latitude")) {
                lat = Float.parseFloat(parser.nextText());
            }
            if (parser.getName().equals("longitude")) {
                lon = Float.parseFloat(parser.nextText());
            }
            if (parser.getName().equals("elevation_m")) {
                elev = Float.parseFloat(parser.nextText());
            }
            if (parser.getName().equals("METAR")) {
                metar = true;
            }
            if (parser.getName().equals("TAF")) {
                taf = true;
            }
        }
        return new Airport(0, OACI, city, state, country, lat, lon, elev, metar, taf);
    }


}
