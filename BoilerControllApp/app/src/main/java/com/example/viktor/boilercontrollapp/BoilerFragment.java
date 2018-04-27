package com.example.viktor.boilercontrollapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skumar.flexibleciruclarseekbar.CircularSeekBar;

public class BoilerFragment extends Fragment {

    CircularSeekBar mTemperatureBar;
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
        mTemperatureBar = getView().findViewById(R.id.temperature_seek_bar);

        mTemperatureBar.setDrawMarkings(true);
        //mTemperatureBar.setDotMarkers(true);
        mTemperatureBar.setRoundedEdges(true);
        mTemperatureBar.setIsGradient(true);
        mTemperatureBar.setPopup(true);
        mTemperatureBar.setSweepAngle(270);
        mTemperatureBar.setArcRotation(225);
        mTemperatureBar.setArcThickness(30);
        mTemperatureBar.setMin(10);
        mTemperatureBar.setMax(50);
        mTemperatureBar.setProgress(20);
        mTemperatureBar.setIncreaseCenterNeedle(20);
        mTemperatureBar.setValueStep(1);
        mTemperatureBar.setNeedleFrequency(1);
        mTemperatureBar.setNeedleDistanceFromCenter(32);
        mTemperatureBar.setNeedleLengthInDP(12);
        mTemperatureBar.setIncreaseCenterNeedle(24);
        mTemperatureBar.setNeedleThickness(1);
        mTemperatureBar.setHeightForPopupFromThumb(10);
    }
}
