package com.example.viktor.boilercontrollapp;

import io.ghyeok.stickyswitch.widget.StickySwitch;

/**
 * Created by viktor on 5/29/18.
 */

public class ExtendedStickySwitch extends Extended{
    StickySwitch stickySwitch; // state == 1 => BoilerOn ; state == 0 => HeaterOn
    String secondPropName;

    public ExtendedStickySwitch(Integer state, String name, String propName, String secondPropName, StickySwitch stickySwitch) {
        super(state, name, propName);
        this.secondPropName = secondPropName;
        this.stickySwitch = stickySwitch;

        setState(this.state);
        initSetOnSelectedChangeListener(stickySwitch);
    }

    @Override
    public void setState(Integer state) {
        prevState = state;
        this.state = state;
        stickySwitch.setDirection(state == 1 ?
                StickySwitch.Direction.LEFT : StickySwitch.Direction.RIGHT, false, false);;
    }

    public void initSetOnSelectedChangeListener(StickySwitch stickySwitch) {
        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {

            @Override
            public void onSelectedChange(StickySwitch.Direction direction, String s) {
                prevState = state;
                state = (state == 0) ? 1 : 0;
               sendDataToServer(propName,
                        (direction.equals(StickySwitch.Direction.LEFT) ? "1" : "0"));
               sendDataToServer(secondPropName,
                        (direction.equals(StickySwitch.Direction.LEFT) ? "0" : "1"));
            }
        });
    }

    @Override
    protected void asyncOnPreExecute() {

    }

    @Override
    protected void asyncOnPostExecute() {
        setState(state);
    }
}
