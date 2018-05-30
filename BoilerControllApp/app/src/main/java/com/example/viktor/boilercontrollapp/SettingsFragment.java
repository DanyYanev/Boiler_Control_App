package com.example.viktor.boilercontrollapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.ghyeok.stickyswitch.widget.StickySwitch;

public class SettingsFragment extends Fragment {
    StickySwitch degree_switch;
    SharedPreferences sharedPreferences;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        degree_switch = getView().findViewById(R.id.degree_switch);

        initFields();

        degree_switch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(StickySwitch.Direction direction, String s) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Degree System", s);
                Log.d("Direction", s);
                editor.commit();
            }
        });

    }

    void initFields(){
        String degree_system = sharedPreferences.getString("Degree System", "");
        if(!degree_system.equals("")){
            degree_switch.setDirection(degree_system.equals("Celsius") ?
                    StickySwitch.Direction.LEFT : StickySwitch.Direction.RIGHT, false, false);
        }
    }
}
