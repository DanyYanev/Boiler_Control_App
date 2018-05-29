package com.example.viktor.boilercontrollapp;

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

    public ExtendedCircularSeekBar(Integer state, String name, String propName, CircularSeekBar seekBar, TextView tvProgress) {
        super(state, name, propName);
        this.seekBar = seekBar;
        this.tvProgress = tvProgress;

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

    void setProperties(CircularSeekBar seekBar){
        seekBar.setDrawMarkings(true);
        //seekBar.setDotMarkers(true);
        seekBar.setRoundedEdges(true);
        seekBar.setIsGradient(true);
        seekBar.setSweepAngle(270);
        seekBar.setArcRotation(225);
        seekBar.setArcThickness(30);
        seekBar.setMin(10);
        seekBar.setMax(50);
        seekBar.setProgress(20);
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
                prevState = state;
                state = (int)progress;
                new ServerPutRequestTask().execute(new String[]{"12345", propName, state.toString()});
            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar CircularSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar CircularSeekBar) {

            }
        });
    }

    @Override
    protected void asyncOnPreExecute() {

    }

    @Override
    protected void asyncOnPostExecute() {

    }
}
