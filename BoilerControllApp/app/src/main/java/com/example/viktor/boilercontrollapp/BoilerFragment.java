package com.example.viktor.boilercontrollapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viktor.boilercontrollapp.utilities.NetworkUtils;
import com.skumar.flexibleciruclarseekbar.CircularSeekBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import io.ghyeok.stickyswitch.widget.StickySwitch;

public class BoilerFragment extends Fragment {

    CircularSeekBar mTemperatureBar;
    CircularSeekBar mHysteresisBar;
    StickySwitch stickySwitch;

    TextView tvTemperature;
    TextView tvHysteresis;
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
        mHysteresisBar = getView().findViewById(R.id.hysteresis_seek_bar);

        tvTemperature = getView().findViewById(R.id.temperature_text_view);
        tvHysteresis = getView().findViewById(R.id.hysteresis_text_view);

        stickySwitch = getView().findViewById(R.id.sticky_switch);

        setSeekBar(mTemperatureBar);
        setSeekBar(mHysteresisBar);

        new ServerGetRequestTask().execute(NetworkUtils.buildUrl("12345.json"));

        mTemperatureBar.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar CircularSeekBar, float progress, boolean fromUser) {
                tvTemperature.setText(Integer.toString((int)progress) + "\u00B0" + "C");
                new ServerPutRequestTask().execute(new String[]{"12345", "BTempSet", Integer.toString((int) progress)});
            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar CircularSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar CircularSeekBar) {

            }
        });

        mHysteresisBar.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar CircularSeekBar, float progress, boolean fromUser) {
                tvHysteresis.setText(Integer.toString((int)progress) + "\u00B0" + "C");
                Log.d("Swiper progress", Float.toString(progress));
                new ServerPutRequestTask().execute(new String[]{"12345", "HTempSet", Integer.toString((int) progress)});
            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar CircularSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar CircularSeekBar) {

            }
        });

        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {

            @Override
            public void onSelectedChange(StickySwitch.Direction direction, String s) {
                new ServerPutRequestTask().execute(new String[]{"12345", "BoilerSource",
                        (direction.equals(StickySwitch.Direction.LEFT) ? "1" : "0")});
                new ServerPutRequestTask().execute(new String[]{"12345", "HeatingSource",
                        (direction.equals(StickySwitch.Direction.LEFT) ? "0" : "1")});
            }
        });
    }

    void setSeekBar(CircularSeekBar seekBar){
        seekBar.setDrawMarkings(true);
        //seekBar.setDotMarkers(true);
        seekBar.setRoundedEdges(true);
        seekBar.setIsGradient(true);
        seekBar.setSweepAngle(270);
        seekBar.setArcRotation(225);
        seekBar.setArcThickness(30);
        seekBar.setMin(10);
        seekBar.setMax(50);
        seekBar.setProgress(20);
        seekBar.setIncreaseCenterNeedle(20);
        seekBar.setValueStep(1);
        seekBar.setNeedleFrequency(1);
        seekBar.setNeedleDistanceFromCenter(32);
        seekBar.setNeedleLengthInDP(12);
        seekBar.setIncreaseCenterNeedle(24);
        seekBar.setNeedleThickness(1);
        seekBar.setHeightForPopupFromThumb(10);
    }

    class ServerGetRequestTask extends AsyncTask<URL, Void, HashMap<String, String>> {
//        @Override
//        protected void onPreExecute() {
//            mLoadingIndicator.setVisibility(View.VISIBLE);
//        }

        @Override
        protected HashMap<String, String> doInBackground(URL... urls) {
            URL apiURL = urls[0];
            HashMap<String, String> values = new HashMap<>();

            try {
                values = NetworkUtils.getResponseFromHttpUrl(apiURL);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return values;
        }

        @Override
        protected void onPostExecute(HashMap<String, String> values) {
//            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(!values.isEmpty()){
                tvTemperature.setText(values.get("BTempSet") + "\u00B0" + "C");
                tvHysteresis.setText(values.get("HTempSet") + "\u00B0" + "C");
                mTemperatureBar.setProgress(Integer.parseInt(values.get("BTempSet")));
                mHysteresisBar.setProgress(Integer.parseInt(values.get("HTempSet")));
                stickySwitch.setDirection(values.get("BoilerSource").equals("1") ?
                        StickySwitch.Direction.LEFT : StickySwitch.Direction.RIGHT, false, false);
            }else{
                Toast.makeText(getActivity(), "Connection Error Occurred", Toast.LENGTH_LONG).show();
            }
        }
    }

    class ServerPutRequestTask extends AsyncTask<String[], Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            mLoadingIndicator.setVisibility(View.VISIBLE);
//        }

        @Override
        protected Void doInBackground(String[]... strings) {
            String data[] = strings[0];

            try {

                JSONObject dataJson = new JSONObject();
                dataJson.put("token", data[0]);
                dataJson.put("controller_token", "testing");
                JSONObject values = new JSONObject();
                values.put("key", data[1]);
                values.put("value", data[2]);

                dataJson.put("values_attributes", new JSONArray().put(values));
                try {
                    NetworkUtils.sendPostRequestToServer(dataJson, NetworkUtils.buildUrl(data[0]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
