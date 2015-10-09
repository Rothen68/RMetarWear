package com.rothen.rmetarwear.Controller;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.rothen.rmetarwear.R;

public class AddAirportActivity extends BaseActivity {

    TextView txtOACICode;

    ListView lstAirports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_airport);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtOACICode = (TextView) findViewById(R.id.addAirport_txtOACI);
        lstAirports = (ListView) findViewById(R.id.addAirport_lstAirports);
        lstAirports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finish();
            }
        });

        txtOACICode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (txtOACICode.getText().length() > 2)
                    updateListView(txtOACICode.getText().toString());
            }
        });

    }

    private void updateListView(String OACICode) {
        // https://www.aviationweather.gov/adds/dataserver_current/httpparam?dataSource=stations&requestType=retrieve&format=xml&stationString=L
    }

}
