package com.example.viktor.boilercontrollapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;

import com.example.viktor.boilercontrollapp.Extended;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by viktor on 5/30/18.
 */

public class ServerGetRequestTask extends AsyncTask<URL, Void, HashMap<String, String>> {
    Extended[] buttons;
    SwipeRefreshLayout refreshLayout;
    Integer responseCode;
    Context context;

    public ServerGetRequestTask(Extended[] buttons,Context context){
        this.buttons = buttons;
        this.context = context;
    }

    public Integer getResponceCode() {
        return responseCode;
    }

    public ServerGetRequestTask(Extended[] buttons, SwipeRefreshLayout refreshLayout, Context context){
        this.buttons = buttons;
        this.refreshLayout = refreshLayout;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        asyncOnPreExecute();
    }

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
        if (!values.isEmpty()) {
            for(Extended button : buttons){
                if((button.getPropName().equals("BTempSet") || button.getPropName().equals("HTempSet") ||
                        button.getPropName().equals("BHistSet")) &&
                        PreferenceManager.getDefaultSharedPreferences(context).getString("Degree System", "").equals("Fahrenheit")){
                    button.setState((int)(Integer.parseInt(values.get(button.getPropName())) *1.8 + 32));
                }else {
                    button.setState(Integer.parseInt(values.get(button.getPropName())));
                }
            }
            responseCode = Integer.parseInt(values.get("response"));
            if(responseCode != 200){
                Toast.makeText(context, "No connection", Toast.LENGTH_LONG);
            }
        }
        if(refreshLayout != null){
            refreshLayout.setRefreshing(false);
            Log.d("SwipeRefresh", "works");
        }

        asyncOnPostExecute();
    }

    protected void asyncOnPreExecute(){}
    protected void asyncOnPostExecute(){}
}