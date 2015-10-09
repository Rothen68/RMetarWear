package com.rothen.rmetarwear.Controller;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.rothen.rmetarwear.Model.FollowedAirport;
import com.rothen.rmetarwear.R;

public class AirportDetailActivity extends BaseActivity {

    public static String OACI = "OACI";

    TextView txtOACI;
    TextView txtCity;
    TextView txtState;
    TextView txtCountry;
    TextView txtLat;
    TextView txtLon;
    TextView txtElev;
    TextView txtUpdateDelay;
    Button btnDeleteFollowed;

    ToggleButton tbMetar;
    ToggleButton tbTaf;
    SeekBar skUpdateDelay;
    private FollowedAirport currentAirport = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            currentAirport = databaseHelper.getFollowedAirport(extras.getString("OACI"));
        }

        txtOACI = (TextView) findViewById(R.id.contentAirport_txtOACI);
        txtCity = (TextView) findViewById(R.id.contentAirport_txtCity);
        txtState = (TextView) findViewById(R.id.contentAirport_txtState);
        txtCountry = (TextView) findViewById(R.id.contentAirport_txtCountry);
        txtLat = (TextView) findViewById(R.id.contentAirport_txtLat);
        txtLon = (TextView) findViewById(R.id.contentAirport_txtLon);
        txtElev = (TextView) findViewById(R.id.contentAirport_txtElev);
        txtUpdateDelay = (TextView) findViewById(R.id.contentAirport_txtUpdateDelay);
        tbMetar = (ToggleButton) findViewById(R.id.contentAirport_tbMetar);
        tbTaf = (ToggleButton) findViewById(R.id.contentAirport_tbTaf);
        skUpdateDelay = (SeekBar) findViewById(R.id.contentAirport_sbUpdateDelay);
        btnDeleteFollowed = (Button) findViewById(R.id.contentAirport_deleteFollowed);
        btnDeleteFollowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.removeFollowedAirport(currentAirport);
                currentAirport = null;
                finish();
            }
        });


        if (currentAirport != null) {
            txtOACI.setText(currentAirport.airport.OACICode);
            txtCity.setText(currentAirport.airport.city);
            txtState.setText(currentAirport.airport.state);
            txtCountry.setText(currentAirport.airport.country);
            txtLat.setText(Float.toString(currentAirport.airport.latitude));
            txtLon.setText(Float.toString(currentAirport.airport.longitude));
            txtElev.setText(Float.toString(currentAirport.airport.elevation));
            tbMetar.setChecked(currentAirport.notifMetar);
            tbMetar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    currentAirport.notifMetar = isChecked;

                }
            });
            tbTaf.setChecked(currentAirport.notifTaf);
            tbTaf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    currentAirport.notifTaf = isChecked;

                }
            });

            skUpdateDelay.setProgress(currentAirport.updateFrequency);
            skUpdateDelay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    currentAirport.updateFrequency = seekBar.getProgress();
                    txtUpdateDelay.setText(" " + getStringFrequencyBySeekBarValue(currentAirport.updateFrequency));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {


                }
            });
            txtUpdateDelay.setText(" " + getStringFrequencyBySeekBarValue(currentAirport.updateFrequency));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (currentAirport != null)
            databaseHelper.updateFollowedAirport(currentAirport);
    }

    private String getStringFrequencyBySeekBarValue(int value) {
        String frequency = "";
        if (value == 0) {
            frequency = " 30" + getString(R.string.minutes);
        } else if (value == 1) {
            frequency = value + " " + getString(R.string.hour);
        } else {
            frequency = value + " " + getString(R.string.hours);
        }
        return frequency;
    }


}
