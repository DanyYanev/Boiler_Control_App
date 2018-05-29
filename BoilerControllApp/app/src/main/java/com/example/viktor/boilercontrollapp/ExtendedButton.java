package com.example.viktor.boilercontrollapp;

import android.animation.ObjectAnimator;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

import com.example.viktor.boilercontrollapp.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by viktor on 5/27/18.
 */

public class ExtendedButton extends Extended{
    Button button;

    public ExtendedButton(Integer state, String name, String propName, Button button) {
        super(state, name, propName);
        this.button = button;

        setCurrentBackgroundResource();

        initOnClickListener(button);

    }

    @Override
    public void setState(Integer state) {
        this.state = state;
        setCurrentBackgroundResource();
    }

    void setCurrentBackgroundResource(){
        if(state == 0)
            button.setBackgroundResource(R.drawable.button_off_on_transition);
        else
            button.setBackgroundResource(R.drawable.button_on_off_transition);
    }

    void initOnClickListener(final Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevState = state;
                state = (state == 0) ? 1 : 0;
                sendDataToServer(propName, state.toString());
            }
        });
    }

    @Override
    public void asyncOnPostExecute(){
        int duration = 1000;
        if(state != prevState){
            if(state == 1)
                button.setBackgroundResource(R.drawable.button_off_on_transition);
            else
                button.setBackgroundResource(R.drawable.button_on_off_transition);
            TransitionDrawable transition = (TransitionDrawable) button.getBackground();
            transition.startTransition(duration);
        }else{
            ObjectAnimator animation = ObjectAnimator.ofFloat(button, "translationY", -10f, 10f);
            animation.setDuration(100);
            animation.setRepeatCount(2);
            animation.start();
        }
        ObjectAnimator animation = ObjectAnimator.ofFloat(button, "translationX", 0f);
        animation.setDuration(duration);
        animation.start();
    }

    @Override
    public void asyncOnPreExecute() {
        int duration = 1000;
        ObjectAnimator animation = ObjectAnimator.ofFloat(button, "translationX", 1000f);
        animation.setDuration(duration);
        animation.start();

    }
}
