package com.rothen.rmetarwear.Model;

/**
 * Created by stéphane on 08/10/2015.
 */
public class FollowedAirport {
    public int id;
    public Airport airport;
    public boolean isNotifActive;
    public boolean notifMetar;
    public boolean notifTaf;
    /**
     * update frequency in minute
     */
    public int updateFrequency;

    public FollowedAirport(int id, Airport airport, boolean notifActive, boolean notifMetar, boolean notifTaf, int updateFrequency) {
        this.id = id;
        this.airport = airport;
        this.isNotifActive = notifActive;
        this.notifMetar = notifMetar;
        this.notifTaf = notifTaf;
        this.updateFrequency = updateFrequency;
    }

    public FollowedAirport() {

    }

}
