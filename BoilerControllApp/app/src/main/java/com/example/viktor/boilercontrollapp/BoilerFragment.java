package com.example.viktor.boilercontrollapp;

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

        stickySwitch = new ExtendedStickySwitch(0, "BoilerHeatingSwitch", "BoilerSource", "HeatingSource",
                (StickySwitch)getView().findViewById(R.id.sticky_switch));

        setDataFromServer();
    }

    void setDataFromServer(){
        URL apiURL = NetworkUtils.buildUrl("12345.json");
        new ServerGetRequestTask().execute(apiURL);
        return;
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
                mTemperatureBar.setState(Integer.parseInt(values.get("BTempSet")));
                mHysteresisBar.setState(Integer.parseInt(values.get("HTempSet")));

                stickySwitch.setState(Integer.parseInt(values.get("BoilerSource")));
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
