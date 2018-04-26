package com.example.viktor.boilercontrollapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;


public class HomeFragment extends Fragment {

    Button bBoiler;
    Button bHeating;
    Button bPool;

    Boolean bBoilerState = true;
    Boolean bHeatingState = false;
    Boolean bPoolState = true;

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

        initButtons();
        bBoiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int durationMillis = 1000;
                if(!bBoilerState)
                    bBoiler.setBackgroundResource(R.drawable.button_off_on_transition);
                else
                    bBoiler.setBackgroundResource(R.drawable.button_on_off_transition);
                TransitionDrawable transition = (TransitionDrawable) bBoiler.getBackground();
                transition.startTransition(durationMillis);
                bBoilerState = !bBoilerState;
            }
        });


    }

    private void initButtons(){
        bBoiler = getView().findViewById(R.id.boiler_button);
        bHeating = getView().findViewById(R.id.heating_button);
        bPool = getView().findViewById(R.id.pool_button);
    }

    private void createOnClickListener(final Button button, Boolean buttonState){
        Boolean buttonSt = buttonState;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int durationMillis = 1000;
                TransitionDrawable transition = (TransitionDrawable) button.getBackground();
                transition.startTransition(durationMillis);


            }
        });
    }
}
