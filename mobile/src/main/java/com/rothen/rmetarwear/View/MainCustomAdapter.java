package com.rothen.rmetarwear.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.rothen.rmetarwear.Model.FollowedAirport;
import com.rothen.rmetarwear.R;

import java.util.ArrayList;

/**
 * Created by stéphane on 08/10/2015.
 */
public class MainCustomAdapter extends BaseAdapter {

    private ArrayList<FollowedAirport> lstFollowedAirports;
    private LayoutInflater inflater;

    public MainCustomAdapter(Context context, ArrayList<FollowedAirport> followedAirports) {
        lstFollowedAirports = followedAirports;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lstFollowedAirports.size();
    }

    @Override
    public Object getItem(int position) {
        return lstFollowedAirports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.main_list_item_layout, null);
        }
        FollowedAirport item = lstFollowedAirports.get(position);

        TextView txtOACI = (TextView) convertView.findViewById(R.id.main_list_item_txtOACICode);
        TextView txtCity = (TextView) convertView.findViewById(R.id.main_list_item_txtCity);
        TextView txtMetar = (TextView) convertView.findViewById(R.id.main_list_item_txtMetar);
        TextView txtTaf = (TextView) convertView.findViewById(R.id.main_list_item_txtTaf);
        TextView txtUpdateFreq = (TextView) convertView.findViewById(R.id.main_list_item_txtUpdateFrequency);
        CheckBox chkBox = (CheckBox) convertView.findViewById(R.id.main_list_item_chkBox);

        txtOACI.setText(item.airport.OACICode);
        txtCity.setText(item.airport.city);
        if (item.notifMetar) {
            txtMetar.setText("M");
        } else {
            txtMetar.setText(" ");
        }
        if (item.notifTaf) {
            txtTaf.setText("T");
        } else {
            txtTaf.setText(" ");
        }
        txtUpdateFreq.setText(getStringFromUpdateFrequency(item.updateFrequency));
        chkBox.setChecked(item.isNotifActive);

        return convertView;
    }

    public void setContent(ArrayList<FollowedAirport> content) {
        lstFollowedAirports = content;
        this.notifyDataSetChanged();
    }

    public String getOACIFormPosition(int position) {
        return lstFollowedAirports.get(position).airport.OACICode;
    }

    private String getStringFromUpdateFrequency(int updateFrequency) {
        if (updateFrequency == 0)
            return "30'";
        else
            return Integer.toString(updateFrequency) + " h";
    }
}
