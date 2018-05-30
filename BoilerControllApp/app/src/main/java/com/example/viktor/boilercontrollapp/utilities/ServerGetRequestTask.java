package com.example.viktor.boilercontrollapp.utilities;

import android.os.AsyncTask;
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

    public ServerGetRequestTask(Extended[] buttons){
        this.buttons = buttons;
    }
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
        if (!values.isEmpty()) {
            for(Extended button : buttons){
                button.setState(Integer.parseInt(values.get(button.getPropName())));
            }
        }
    }
}