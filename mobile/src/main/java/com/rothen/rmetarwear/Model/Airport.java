package com.rothen.rmetarwear.Model;

/**
 * Created by stéphane on 07/10/2015.
 */
public class Airport {
    private static final int METAR = 0x01;
    private static final int TAF = 0x02;

    public int id;
    public String OACICode;
    public String city;
    public String state;
    public String country;
    public float latitude;
    public float longitude;
    public float elevation;
    public boolean metarAviable;
    public boolean tafAviable;

    public Airport(int id, String OACICode, String city, String state, String country, float latitude, float longitude, float elevation, int siteType) {
        this.id = id;
        this.OACICode = OACICode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        setSiteType(siteType);

    }

    public Airport(int id, String OACICode, String city, String state, String country, float latitude, float longitude, float elevation, boolean metar, boolean taf) {
        this.id = id;
        this.OACICode = OACICode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.metarAviable = metar;
        this.tafAviable = taf;

    }

    private void setSiteType(int siteType) {
        metarAviable = (siteType & METAR) > 0;
        tafAviable = (siteType & TAF) > 0;
    }

    private void setSiteType(int siteType) {
        metarAviable = (siteType & METAR) > 0;
        tafAviable = (siteType & TAF) > 0;
    }

    public int getSiteType() {
        int r = 0;
        r = r + ((metarAviable) ? METAR : 0);
        r = r + ((tafAviable) ? TAF : 0);
        return r;
    }
}
