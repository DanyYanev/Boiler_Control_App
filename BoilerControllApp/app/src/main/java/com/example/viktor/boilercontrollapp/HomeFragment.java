package com.example.viktor.boilercontrollapp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.viktor.boilercontrollapp.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import static java.lang.Thread.sleep;


public class HomeFragment extends Fragment {

    ExtendedButton bBoiler;
    ExtendedButton bHeating;
    ExtendedButton bPool;

    HashMap<String, String> getRequestValues = null;

    GifDrawable gifBackgroundController;
    GifImageView gifBackground;

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
        initGifBackground();

    }

    private void initButtons() {
        bBoiler = new ExtendedButton(false, "Boiler", "BoilerPic", (Button) getView().findViewById(R.id.boiler_button));
        bPool = new ExtendedButton(false, "Pool", "PoolPump", (Button) getView().findViewById(R.id.pool_button));
        bHeating = new ExtendedButton(false, "Heating", "HeatingPic", (Button) getView().findViewById(R.id.heating_button));

        setDataFromServer();
        if (getRequestValues != null) {
            bBoiler.setState(!getRequestValues.get("BoilerPic").equals("0"));
            bPool.setState(!getRequestValues.get("PoolPump").equals("0"));
            bHeating.setState(!getRequestValues.get("HeatingPic").equals("0"));

        }
    }
    void setDataFromServer(){
        URL apiURL = NetworkUtils.buildUrl("12345.json");
        new ServerGetRequestTask().execute(apiURL);
        return;
    }

    void setButtonStates(Button button, boolean state){
        if(state) button.setBackgroundResource(R.drawable.button_on_off_transition);
        else button.setBackgroundResource(R.drawable.button_off_on_transition);
    }

    private void initGifBackground(){
        new SetBackgroundThread().start();
    }

    class ServerGetRequestTask extends AsyncTask<URL, Void, HashMap<String, String>> {
//        @Override
//        protected void onPreExecute() {
//            mLoadingIndicator.setVisibility(View.VISIBLE);
//        }

        @Override
        protected HashMap<String, String> doInBackground(URL... urls) {
            URL apiURL = urls[0];
            HashMap<String, String> values = new HashMap<>();

            try {
                values = NetworkUtils.getResponseFromHttpUrl(apiURL);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return values;
        }

        @Override
        protected void onPostExecute(HashMap<String, String> values) {
//            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(!values.isEmpty()){
                getRequestValues = new HashMap<>(values);
            }else{
                Toast.makeText(getActivity(), "Connection Error Occurred", Toast.LENGTH_LONG).show();
            }
        }
    }

    class SetBackgroundThread extends Thread{
        public void run(){
            gifBackground = (GifImageView) getView().findViewById(R.id.background_gif);
            gifBackgroundController = (GifDrawable) gifBackground.getDrawable();

            int durationGif = gifBackgroundController.getDuration();
            Calendar calendar = Calendar.getInstance();
            double gifMinuteChange = durationGif / (24 * 60);
            double curTime = (calendar.get(Calendar.MINUTE) +  (calendar.get(Calendar.HOUR_OF_DAY) * 60 ) + 3*60) % (24 * 60);
            Log.e("Time: ", (Integer.toString(calendar.get(Calendar.HOUR))) + ":" + Integer.toString(calendar.get(Calendar.MINUTE)));
            gifBackgroundController.setSpeed(4.0f);
            while(gifBackgroundController.getCurrentPosition() / 50 != (int)(gifMinuteChange * curTime) / 50);
            //gifBackgroundController.seekTo((int)(gifMinuteChange * curTime));
            gifBackgroundController.setSpeed(0.001f);
        }
    }
}
