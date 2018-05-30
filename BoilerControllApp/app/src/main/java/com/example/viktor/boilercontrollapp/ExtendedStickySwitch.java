package com.example.viktor.boilercontrollapp;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.animation.BounceInterpolator;

import io.ghyeok.stickyswitch.widget.StickySwitch;

/**
 * Created by viktor on 5/29/18.
 */

public class ExtendedStickySwitch extends Extended{
    StickySwitch stickySwitch; // state == 1 => BoilerOn ; state == 0 => HeaterOn

    public ExtendedStickySwitch(Integer state, String name, String propName, StickySwitch stickySwitch) {
        super(state, name, propName);
        this.stickySwitch = stickySwitch;

        setState(this.state);
        initSetOnSelectedChangeListener(stickySwitch);
    }

    @Override
    public void setState(Integer state) {
        prevState = state;
        this.state = state;
        stickySwitch.setDirection(state == 1 ?
                StickySwitch.Direction.LEFT : StickySwitch.Direction.RIGHT, true, false);

    }

    public void initSetOnSelectedChangeListener(StickySwitch stickySwitch) {
        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {

            @Override
            public void onSelectedChange(StickySwitch.Direction direction, String s) {
                prevState = state;
                state = (state == 0) ? 1 : 0;
               sendDataToServer(propName, state.toString());
            }
        });
    }

    @Override
    protected void asyncOnPreExecute() {
        stickySwitch.setDirection(state == 0 ?
                StickySwitch.Direction.LEFT : StickySwitch.Direction.RIGHT, false, false);
    }

    @Override
    protected void asyncOnPostExecute() {
        if(state == prevState){
            ObjectAnimator animation = ObjectAnimator.ofFloat(stickySwitch, "translationX", -10f, 10f);
            animation.setDuration(100);
            animation.setRepeatCount(2);
            animation.start();
        }
        setState(state);
    }
}
