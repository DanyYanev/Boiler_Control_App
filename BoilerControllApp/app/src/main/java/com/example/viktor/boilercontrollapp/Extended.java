package com.example.viktor.boilercontrollapp;

import android.animation.ObjectAnimator;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.viktor.boilercontrollapp.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by viktor on 5/28/18.
 */

public abstract class Extended {
    Integer state;
    Integer prevState;
    String name;
    String propName;
    Integer responseCode = 0;


    public Extended(Integer state, String name, String propName) {
        this.state = state;
        prevState = this.state;
        this.name = name;
        this.propName = propName;

    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public String getPropName() {
        return propName;
    }

    public int geResponceCode() {
        return responseCode;
    }

    void sendDataToServer(String field, String value){
        String data_arr[] = {"12345", field, value};
        new ServerPutRequestTask().execute(data_arr);
    }

    class ServerPutRequestTask extends AsyncTask<String[], Void, Void> {

        @Override
        protected void onPreExecute() {
            asyncOnPreExecute();
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
                values.put("value", data[2]);

                dataJson.put("values_attributes", new JSONArray().put(values));
                try {
                    responseCode = NetworkUtils.sendPostRequestToServer(dataJson, NetworkUtils.buildUrl(data[0] + ".json"));
                } catch (IOException e) {
                    responseCode = 0;
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(responseCode != 200){
                state = prevState;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            asyncOnPostExecute();
        }
    }

    protected abstract void asyncOnPreExecute();
    protected abstract void asyncOnPostExecute();

}
