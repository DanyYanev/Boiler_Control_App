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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
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
                fragmentTransaction.replace(R.id.fragment_detail, new HomeFragment());
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentTransaction.replace(R.id.fragment_detail, new HeatingFragment());
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.fragment_detail, new BoilerFragment());
                fragmentTransaction.commit();
                break;
        }
        Toast.makeText(MainActivity.this, Integer.toString(position), Toast.LENGTH_LONG).show();
    }

}
