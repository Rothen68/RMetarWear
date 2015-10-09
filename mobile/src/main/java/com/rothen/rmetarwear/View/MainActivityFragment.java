package com.rothen.rmetarwear.View;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rothen.rmetarwear.Model.FollowedAirport;
import com.rothen.rmetarwear.R;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ListView lstFollowedAirport;

    private MainActivityFragmentCallback callback;

    private MainCustomAdapter adapter;

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (MainActivityFragmentCallback) getActivity();
        } catch (ClassCastException ex) {
            throw new ClassCastException(getActivity().toString() + " must implements MainActivityFragmentCallback");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        lstFollowedAirport = (ListView) view.findViewById(R.id.frag_main_listView);
        lstFollowedAirport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callback.onItemClickListener(parent, view, position, id);
            }
        });

        lstFollowedAirport.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return callback.onItemLongClickListener(adapter.getOACIFormPosition(position));
            }
        });

        return view;
    }

    public void setListContent(ArrayList<FollowedAirport> content) {
        if (adapter == null) {
            adapter = new MainCustomAdapter(getContext(), content);
            lstFollowedAirport.setAdapter(adapter);
        } else {
            adapter.setContent(content);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
