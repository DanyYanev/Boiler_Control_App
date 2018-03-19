package com.example.viktor.boilercontrollapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.viktor.boilercontrollapp.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView mBoilerTemperature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBoilerTemperature = findViewById(R.id.tv_boiler_temperature);
        setDataFromServer();
    }

    void setDataFromServer(){
        URL apiURL = NetworkUtils.buildUrl("12345.json");
        new ServerRequestTask().execute(apiURL);

    }

    class ServerRequestTask extends AsyncTask<URL, Void, String>{
        @Override
        protected String doInBackground(URL... urls) {
            URL apiURL = urls[0];
            String hTemp = null;

            try {
                hTemp = NetworkUtils.getResponseFromHttpUrl(apiURL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return hTemp;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null && !s.equals("")){
                mBoilerTemperature.setText(s);
            }
        }
    }

}
