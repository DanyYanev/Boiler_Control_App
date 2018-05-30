package com.example.viktor.boilercontrollapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

    SwipeRefreshLayout refreshLayout;

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

        refreshLayout = getView().findViewById(R.id.refresh_layout);
        initOnRefreshListener(refreshLayout);

        mTemperatureBar = new ExtendedCircularSeekBar(40, "TemperatureBar", "BTempSet",
                (CircularSeekBar) getView().findViewById(R.id.temperature_seek_bar),
                (TextView) getView().findViewById(R.id.temperature_text_view), (TextView) getView().findViewById(R.id.temperature_text_field),
                getActivity(), refreshLayout);

        mHysteresisBar = new ExtendedCircularSeekBar(40, "HysteresisBar", "HTempSet",
                (CircularSeekBar) getView().findViewById(R.id.hysteresis_seek_bar),
                (TextView) getView().findViewById(R.id.hysteresis_text_view), (TextView) getView().findViewById(R.id.hysteresis_text_field),
                getActivity(), refreshLayout);

        stickySwitch = new ExtendedStickySwitch(1, "BoilerHeatingSwitch", "BoilerSource",
                (StickySwitch)getView().findViewById(R.id.sticky_switch));

        setDataFromServer();
    }

    void initOnRefreshListener(final SwipeRefreshLayout refreshLayout){
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                URL apiURL = NetworkUtils.buildUrl("12345.json");
                Extended[] buttons = {mTemperatureBar, mHysteresisBar, stickySwitch};
                new ServerGetRequestTask(buttons, refreshLayout).execute(apiURL);
            }
        });
    }

    void setDataFromServer(){
        URL apiURL = NetworkUtils.buildUrl("12345.json");
        Extended[] buttons = {mTemperatureBar, mHysteresisBar, stickySwitch};
        new ServerGetRequestTask(buttons).execute(apiURL);
    }
}
