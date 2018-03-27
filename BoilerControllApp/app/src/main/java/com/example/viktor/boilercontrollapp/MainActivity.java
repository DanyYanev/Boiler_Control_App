package com.example.viktor.boilercontrollapp;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viktor.boilercontrollapp.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView mBoilerTemperature;
    ProgressBar mLoadingIndicator;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBoilerTemperature = findViewById(R.id.tv_boiler_temperature);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mSwipeRefreshLayout = findViewById(R.id.sr_refresh_layout);
        setDataFromServer();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setDataFromServer();
            }
        });
    }



    void setDataFromServer(){
        URL apiURL = NetworkUtils.buildUrl("12345.json");
        new ServerRequestTask().execute(apiURL);
        mSwipeRefreshLayout.setRefreshing(false);
        return;
    }



    class ServerRequestTask extends AsyncTask<URL, Void, String>{
        @Override
        protected void onPreExecute() {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

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
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(s != null && !s.equals("")){
                mBoilerTemperature.setText(s);
            }else{
                Toast.makeText(MainActivity.this, "Connection Error Occurred", Toast.LENGTH_LONG).show();
            }
        }
    }

}
