package com.example.viktor.boilercontrollapp;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SlidingPaneLayout;
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

    Toolbar myToolbar;
    SlidingPaneLayout slidingPaneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slidingPaneLayout = findViewById(R.id.sliding_pane);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Boiler Control");
        setSupportActionBar(myToolbar);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_detail, new HomeFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onOptionSelected(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch(position){
            case 0:
                myToolbar.setTitle("Boiler Control");
                fragmentTransaction.replace(R.id.fragment_detail, new HomeFragment());
                break;
            case 1:
                myToolbar.setTitle(R.string.item3);
                fragmentTransaction.replace(R.id.fragment_detail, new BoilerFragment());
                break;
            case 2:
                myToolbar.setTitle(R.string.item2);
                fragmentTransaction.replace(R.id.fragment_detail, new HeatingFragment());
                break;
            case 3:
                myToolbar.setTitle(R.string.item4);
                fragmentTransaction.replace(R.id.fragment_detail, new SettingsFragment());
                break;
        }
        fragmentTransaction.commit();
        slidingPaneLayout.closePane();
        //Toast.makeText(MainActivity.this, Integer.toString(position), Toast.LENGTH_LONG).show();
    }

}
