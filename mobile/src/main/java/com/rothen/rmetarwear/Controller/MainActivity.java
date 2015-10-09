package com.rothen.rmetarwear.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.rothen.rmetarwear.R;
import com.rothen.rmetarwear.Service.sqlite.DBBuilder;
import com.rothen.rmetarwear.View.MainActivityFragment;
import com.rothen.rmetarwear.View.MainActivityFragmentCallback;

public class MainActivity extends BaseActivity implements MainActivityFragmentCallback {

    private FloatingActionButton btnAdd;

    private MainActivityFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.mainFrag);

        btnAdd = (FloatingActionButton) findViewById(R.id.main_FloatingButton);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), AddAirportActivity.class);
                startActivity(i);
            }
        });

        DBBuilder builder = new DBBuilder(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        fragment.setListContent(databaseHelper.getListFollowedAirports());
    }

    @Override
    public void onItemClickListener(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "ItemClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClickListener(String OACICode) {
        Intent i = new Intent(this, AirportDetailActivity.class);
        i.putExtra("OACI", OACICode);
        startActivity(i);
        return true;
    }
}


/*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
 */