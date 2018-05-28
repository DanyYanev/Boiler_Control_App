package com.example.viktor.boilercontrollapp;

import android.widget.Button;

/**
 * Created by viktor on 5/28/18.
 */

public class ExtendedCirclualrSeekBar extends ExtendedButton {

    public ExtendedCirclualrSeekBar(Integer state, String name, String propName, Button button) {
        super(state, name, propName, button);
    }

    @Override
    void initOnClickListener(Button button) {
        super.initOnClickListener(button);
    }
}
