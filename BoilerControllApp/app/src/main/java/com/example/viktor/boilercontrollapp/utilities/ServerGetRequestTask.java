package com.example.viktor.boilercontrollapp.utilities;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

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

    public ServerGetRequestTask(Extended[] buttons){
        this.buttons = buttons;
    }

    public ServerGetRequestTask(Extended[] buttons, SwipeRefreshLayout refreshLayout){
        this.buttons = buttons;
        this.refreshLayout = refreshLayout;
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
                button.setState(Integer.parseInt(values.get(button.getPropName())));
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