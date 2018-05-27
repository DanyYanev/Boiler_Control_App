package com.example.viktor.boilercontrollapp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.viktor.boilercontrollapp.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by viktor on 5/27/18.
 */

public class ExtendedButton {
    Boolean state;
    String name;
    String propName;
    Button button;

    public ExtendedButton(Boolean state, String name, String propName, Button button) {
        this.state = state;
        this.name = name;
        this.propName = propName;
        this.button = button;

        if(!state)
            button.setBackgroundResource(R.drawable.button_off_on_transition);
        else
            button.setBackgroundResource(R.drawable.button_on_off_transition);
        initOnClickListener(button);

    }

    public void setState(Boolean state) {
        this.state = state;
    }

    void initOnClickListener(final Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                state = !state;
                sendDataToServer(propName, state.toString());

            }
        });
    }

    void sendDataToServer(String field, String value){
        String data_arr[] = {"12345", field, value};
        new ServerPutRequestTask().execute(data_arr);
    }

    class ServerPutRequestTask extends AsyncTask<String[], Void, Void> {
        int duration = 1000;

        @Override
        protected void onPreExecute() {
            ObjectAnimator animation = ObjectAnimator.ofFloat(button, "translationX", 1000f);
            animation.setDuration(duration);
            animation.start();
            if(state)
                button.setBackgroundResource(R.drawable.button_off_on_transition);
            else
                button.setBackgroundResource(R.drawable.button_on_off_transition);
            TransitionDrawable transition = (TransitionDrawable) button.getBackground();
            transition.startTransition(duration);
        }

        @Override
        protected Void doInBackground(String[]... strings) {
            String data[] = strings[0];

            try {

                JSONObject dataJson = new JSONObject();
                dataJson.put("token", data[0]);
                dataJson.put("controller_token", "testing");
                JSONObject values = new JSONObject();
                values.put("key", data[1]);
                values.put("value", data[2].equals("true") ? 1 : 0);

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
            ObjectAnimator animation = ObjectAnimator.ofFloat(button, "translationX", 0f);
            animation.setDuration(1000);
            animation.start();
        }
    }
}
