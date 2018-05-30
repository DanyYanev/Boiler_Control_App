package com.example.viktor.boilercontrollapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viktor.boilercontrollapp.utilities.NetworkUtils;
import com.example.viktor.boilercontrollapp.utilities.ServerGetRequestTask;
import com.skumar.flexibleciruclarseekbar.CircularSeekBar;

import java.net.URL;

import io.ghyeok.stickyswitch.widget.StickySwitch;

public class BoilerFragment extends Fragment {

    ExtendedStickySwitch stickySwitch;

    ExtendedCircularSeekBar mTemperatureBar;
    ExtendedCircularSeekBar mHysteresisBar;

    public BoilerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_boiler, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTemperatureBar = new ExtendedCircularSeekBar(20, "TemperatureBar", "BTempSet",
                (CircularSeekBar) getView().findViewById(R.id.temperature_seek_bar), (TextView) getView().findViewById(R.id.temperature_text_view));

        mHysteresisBar = new ExtendedCircularSeekBar(20, "HysteresisBar", "HTempSet",
                (CircularSeekBar) getView().findViewById(R.id.hysteresis_seek_bar), (TextView) getView().findViewById(R.id.hysteresis_text_view));

        stickySwitch = new ExtendedStickySwitch(0, "BoilerHeatingSwitch", "BoilerSource",
                (StickySwitch)getView().findViewById(R.id.sticky_switch));

        setDataFromServer();
    }

    void setDataFromServer(){
        URL apiURL = NetworkUtils.buildUrl("12345.json");
        Extended[] buttons = {mTemperatureBar, mHysteresisBar, stickySwitch};
        new ServerGetRequestTask(buttons).execute(apiURL);
    }
}
