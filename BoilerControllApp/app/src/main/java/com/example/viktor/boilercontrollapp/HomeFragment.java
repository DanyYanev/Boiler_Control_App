package com.example.viktor.boilercontrollapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.viktor.boilercontrollapp.utilities.NetworkUtils;
import com.example.viktor.boilercontrollapp.utilities.ServerGetRequestTask;


import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


public class HomeFragment extends Fragment {

    ExtendedButton bBoiler;
    ExtendedButton bHeating;
    ExtendedButton bPool;

    HashMap<String, String> getRequestValues = null;

    GifDrawable gifBackgroundController;
    GifImageView gifBackground;

    SwipeRefreshLayout refreshLayout;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        refreshLayout = getView().findViewById(R.id.refresh_layout);

        initButtons();
        initOnRefreshListener(refreshLayout);
        new SetBackgroundThread().start();
    }

    void initOnRefreshListener(final SwipeRefreshLayout refreshLayout){
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                URL apiURL = NetworkUtils.buildUrl("12345.json");
                Extended[] buttons = {bBoiler, bPool, bHeating};
                new ServerGetRequestTask(buttons, refreshLayout, getContext()).execute(apiURL);
            }
        });
    }

    private void initButtons() {
        bBoiler = new ExtendedButton(0, "Boiler", "BoilerPic", (Button) getView().findViewById(R.id.boiler_button));
        bPool = new ExtendedButton(0, "Pool", "PoolPump", (Button) getView().findViewById(R.id.pool_button));
        bHeating = new ExtendedButton(0, "Heating", "HeatingPic", (Button) getView().findViewById(R.id.heating_button));

        setDataFromServer();

    }
    void setDataFromServer(){
        URL apiURL = NetworkUtils.buildUrl("12345.json");
        Extended[] buttons = {bBoiler, bPool, bHeating};
        ServerGetRequestTask task = new ServerGetRequestTask(buttons, getContext());
        task.execute(apiURL);
    }

    class SetBackgroundThread extends Thread{
        public void run(){

            gifBackground = getView().findViewById(R.id.background_gif);
            gifBackgroundController = (GifDrawable) gifBackground.getDrawable();

            Calendar calendar = Calendar.getInstance();
            double gifMinuteChange = gifBackgroundController.getDuration() / (24 * 60);
            double curTime = (calendar.get(Calendar.MINUTE) +  (calendar.get(Calendar.HOUR_OF_DAY) * 60 ) + 3*60) % (24 * 60);

            gifBackgroundController.setSpeed(4.0f);
            while(gifBackgroundController.getCurrentPosition() / 50 != (int)(gifMinuteChange * curTime) / 50);
            gifBackgroundController.setSpeed(0.001f);
        }
    }
}
