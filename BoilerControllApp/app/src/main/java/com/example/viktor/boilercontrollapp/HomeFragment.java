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

    Button bBoiler;
    Button bHeating;
    Button bPool;

    Boolean bBoilerState = true;
    Boolean bHeatingState = false;
    Boolean bPoolState = true;

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
        bBoiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int durationMillis = 1000;
                bBoilerState = !bBoilerState;
                ValueAnimator boilerButtonAnim = ValueAnimator.ofInt(0, 100);
                float defX = bBoiler.getX();
                ObjectAnimator animation = ObjectAnimator.ofFloat(bBoiler, "translationX", 1000f);
                animation.setDuration(1000);
                animation.start();
                sendDataToServer("BoilerPic", bBoilerState.toString());


                if(bBoilerState)
                    bBoiler.setBackgroundResource(R.drawable.button_off_on_transition);
                else
                    bBoiler.setBackgroundResource(R.drawable.button_on_off_transition);
                Date currentTime = Calendar.getInstance().getTime();
                TransitionDrawable transition = (TransitionDrawable) bBoiler.getBackground();
                transition.startTransition(durationMillis);

            }
        });

        initGifBackground();

    }

    private void initButtons(){
        bBoiler = getView().findViewById(R.id.boiler_button);
        bHeating = getView().findViewById(R.id.heating_button);
        bPool = getView().findViewById(R.id.pool_button);

        setDataFromServer();
    }

    void setDataFromServer(){
        URL apiURL = NetworkUtils.buildUrl("12345.json");
        new ServerGetRequestTask().execute(apiURL);
        return;
    }

    void sendDataToServer(String field, String value){
        String data_arr[] = {"12345", field, value};
        new ServerPutRequestTask().execute(data_arr);
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
                bBoilerState = !values.get("BoilerPic").equals("0");
                bHeatingState = !values.get("HeatingPic").equals("0");
                bPoolState = !values.get("PoolPump").equals("0");

                setButtonStates(bBoiler, bBoilerState);
                setButtonStates(bHeating, bHeatingState);
                setButtonStates(bPool, bPoolState);

            }else{
                Toast.makeText(getActivity(), "Connection Error Occurred", Toast.LENGTH_LONG).show();
            }
        }
    }

    class ServerPutRequestTask extends AsyncTask<String[], Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            mLoadingIndicator.setVisibility(View.VISIBLE);
//        }

        @Override
        protected Void doInBackground(String[]... strings) {
            String data[] = strings[0];

            try {

                JSONObject dataJson = new JSONObject();
                dataJson.put("token", data[0]);
                dataJson.put("controller_token", "testing");
                JSONObject values = new JSONObject();
                values.put("key", data[1]);
                values.put("value", data[2].equals("true") ? 1 : 0);

                dataJson.put("values_attributes", new JSONArray().put(values));
                try {
                    NetworkUtils.sendPostRequestToServer(dataJson, NetworkUtils.buildUrl(data[0]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getActivity(), "Connection Error Occurred", Toast.LENGTH_LONG).show();
            ObjectAnimator animation = ObjectAnimator.ofFloat(bBoiler, "translationX", 0f);
            animation.setDuration(1000);
            animation.start();
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
            gifBackgroundController.seekTo((int)(gifMinuteChange * curTime));
            gifBackgroundController.setSpeed(0.001f);
        }
    }
}
