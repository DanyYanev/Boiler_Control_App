package com.example.viktor.boilercontrollapp;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.viktor.boilercontrollapp.utilities.MasterFragment;
import com.example.viktor.boilercontrollapp.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements MasterFragment.OnMenuOptionSelectListener{
//    TextView mBoilerTemperature;
//    ProgressBar mLoadingIndicator;
//    SwipeRefreshLayout mSwipeRefreshLayout;
//    ToggleButton mBoilerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_detail, new HomeFragment());
        fragmentTransaction.commit();
//        mBoilerTemperature = findViewById(R.id.tv_boiler_temperature);
//        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
//        mSwipeRefreshLayout = findViewById(R.id.sr_refresh_layout);
//        mBoilerButton = findViewById(R.id.tb_boiler);

        setDataFromServer();

//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                setDataFromServer();
//            }
//        });
    }


    void setDataFromServer(){
        URL apiURL = NetworkUtils.buildUrl("12345.json");
        new ServerRequestTask().execute(apiURL);
//        mSwipeRefreshLayout.setRefreshing(false);
        return;
    }

    @Override
    public void onOptionSelected(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch(position){
            case 0:
                fragmentTransaction.replace(R.id.fragment_detail, new HomeFragment());
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.fragment_detail, new BoilerFragment());
                fragmentTransaction.commit();
                break;
        }
        Toast.makeText(MainActivity.this, Integer.toString(position), Toast.LENGTH_LONG).show();
    }


    class ServerRequestTask extends AsyncTask<URL, Void, HashMap<String, String>>{
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
//                mBoilerTemperature.setText(values.get("BTemp"));
            }else{
                Toast.makeText(MainActivity.this, "Connection Error Occurred", Toast.LENGTH_LONG).show();
            }
        }
    }

}
