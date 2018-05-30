package com.example.viktor.boilercontrollapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.skumar.flexibleciruclarseekbar.CircularSeekBar;

import io.ghyeok.stickyswitch.widget.StickySwitch;


public class HeatingFragment extends Fragment {

    ExtendedButton bFloor;
    ExtendedButton bConvector;
    ExtendedButton bFloorConvector;

    ExtendedStickySwitch sourceStickySwitch;
    ExtendedCircularSeekBar mTemperatureBar;

    public HeatingFragment() {
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
        return inflater.inflate(R.layout.fragment_heating, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sourceStickySwitch = new ExtendedStickySwitch(1, "BoilerPumpSwitch", "HeatingSource",
                (StickySwitch)getView().findViewById(R.id.source_sticky_switch));

        mTemperatureBar = new ExtendedCircularSeekBar(20, "FloorTemperatureBar", "HTempSet",
                (CircularSeekBar) getView().findViewById(R.id.floor_temperature_seek_bar), (TextView) getView().findViewById(R.id.floor_temperature_text_view));

        bFloor = new ExtendedButton(0, "Floor", "FloorPump",
                (Button) getView().findViewById(R.id.floor_button));
        bConvector = new ExtendedButton(0, "Convector", "ConvPump",
                (Button) getView().findViewById(R.id.convector_button));
        bFloorConvector = new ExtendedButton(0, "FloorConvector", "FloorConvPump",
                (Button) getView().findViewById(R.id.floor_convector_button));
    }
}
