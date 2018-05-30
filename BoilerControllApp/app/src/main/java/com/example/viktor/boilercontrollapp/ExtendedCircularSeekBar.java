package com.example.viktor.boilercontrollapp;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.skumar.flexibleciruclarseekbar.CircularSeekBar;

import org.w3c.dom.Text;

/**
 * Created by viktor on 5/28/18.
 */

public class ExtendedCircularSeekBar extends Extended {
    CircularSeekBar seekBar;
    TextView tvProgress;
    TextView tvName;
    SwipeRefreshLayout refreshLayout;
    SharedPreferences sharedPreferences;
    Context context;
    int min_val;
    int max_val;

    public ExtendedCircularSeekBar(Integer state, String name, String propName, CircularSeekBar seekBar, TextView tvProgress,
                                   TextView tvName, Context context) {
        super(state, name, propName);
        this.seekBar = seekBar;
        this.tvProgress = tvProgress;
        this.tvName = tvName;
        this.context = context;
        min_val = 30;
        max_val = 70;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String degree_system = sharedPreferences.getString("Degree System", "");

        if(degree_system.equals("Fahrenheit")){
            min_val = convertToFahrenheit(min_val);
            max_val = convertToFahrenheit(max_val);
            this.state = convertToFahrenheit(this.state);
        }

        setProperties(seekBar);
        setState(this.state);
        initOnCircilarSeekBarChangeListener(seekBar);

    }

    public ExtendedCircularSeekBar(Integer state, String name, String propName, CircularSeekBar seekBar, TextView tvProgress,
                                   TextView tvName, Context context, SwipeRefreshLayout refreshLayout) {
        super(state, name, propName);
        this.seekBar = seekBar;
        this.tvProgress = tvProgress;
        this.tvName = tvName;
        this.context = context;
        this.refreshLayout = refreshLayout;
        min_val = 30;
        max_val = 70;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String degree_system = sharedPreferences.getString("Degree System", "");

        if(degree_system.equals("Fahrenheit")){
            min_val = convertToFahrenheit(min_val);
            max_val = convertToFahrenheit(max_val);
            this.state = convertToFahrenheit(this.state);
        }

        setProperties(seekBar);
        setState(this.state);
        initOnCircilarSeekBarChangeListener(seekBar);
    }

    @Override
    public void setState(Integer state){
        prevState = this.state;
        this.state = state;
        seekBar.setProgress(state);
        tvProgress.setText(state + "\u00B0" + "C");
    }

    public void setRefreshLayout(SwipeRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }

    void setProperties(CircularSeekBar seekBar){
        seekBar.setDrawMarkings(true);
        //seekBar.setDotMarkers(true);
        seekBar.setRoundedEdges(true);
        seekBar.setIsGradient(true);
        seekBar.setSweepAngle(270);
        seekBar.setArcRotation(225);
        seekBar.setArcThickness(30);
        seekBar.setMin(min_val);
        seekBar.setMax(max_val);
        seekBar.setProgress(50);
        seekBar.setIncreaseCenterNeedle(20);
        seekBar.setValueStep(1);
        seekBar.setNeedleFrequency(1);
        seekBar.setNeedleDistanceFromCenter(32);
        seekBar.setNeedleLengthInDP(12);
        seekBar.setIncreaseCenterNeedle(24);
        seekBar.setNeedleThickness(1);
        seekBar.setHeightForPopupFromThumb(10);
    }

    void initOnCircilarSeekBarChangeListener(CircularSeekBar seekBar) {
        seekBar.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar CircularSeekBar, float progress, boolean fromUser) {
                tvProgress.setText(Integer.toString((int) progress) + "\u00B0" + "C");
            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar CircularSeekBar) {
                if(refreshLayout != null)refreshLayout.setEnabled(false);
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar CircularSeekBar) {
                prevState = state;
                state = (int) CircularSeekBar.getProgress();
                if(sharedPreferences.getString("Degree System", "").equals("Fahrenheit")){
                    state = convertToCelsius(state);
                }
                new ServerPutRequestTask().execute(new String[]{"12345", propName, state.toString()});

                if(refreshLayout != null)refreshLayout.setEnabled(true);
            }
        });
    }

    int convertToCelsius(int val){
        return (int) ((val - 32) / 1.8);
    }

    int convertToFahrenheit(int val){
        return (int) (val * 1.8 + 32);
    }

    @Override
    protected void asyncOnPreExecute() {

    }

    @Override
    protected void asyncOnPostExecute() {
        if(state == prevState){
            setState(state);
            ObjectAnimator[] animations = {ObjectAnimator.ofFloat(seekBar, "translationX", -10f, 10f),
                    ObjectAnimator.ofFloat(tvProgress, "translationX", -10f, 10f),
                    ObjectAnimator.ofFloat(tvName, "translationX", -10f, 10f)};
            for(ObjectAnimator anim : animations){
                anim.setDuration(100);
                anim.setRepeatCount(2);
                anim.start();
            }
        }else if(sharedPreferences.getString("Degree System", "").equals("Fahrenheit")){
            state = convertToFahrenheit(state);
        }

    }
}
