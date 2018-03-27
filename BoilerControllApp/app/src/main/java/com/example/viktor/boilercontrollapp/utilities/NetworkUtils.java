/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.viktor.boilercontrollapp.utilities;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the weather servers.
 */
public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String SERVER_URL =
            "http://178.169.176.184:8000/users/";

    //private static final String USER_ID = null;


    public static URL buildUrl(String userId) {
        Uri builtUri = Uri.parse(SERVER_URL).buildUpon()
                .appendPath(userId)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "Built URI " + url);

        return url;
    }

    public static void sendPostRequestToServer(JSONObject data, URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setRequestProperty("Content-Type", "text/json; charset=utf-8");

            urlConnection.setDoInput(true);
            urlConnection.setFixedLengthStreamingMode(data.toString().length());

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());

            out.write(data.toString().getBytes());
        }finally {
            urlConnection.disconnect();
        }

    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String jsonServerResponse = null;
        String bTemp = null;
        String hTemp = null;
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                jsonServerResponse = scanner.next();
            } else {
                return null;
            }


            JSONObject responce = new JSONObject(jsonServerResponse);
            JSONArray values = responce.getJSONArray("values");

            for(int i = 0 ;  i < values.length() ; ++ i){
                if(values.getJSONObject(i).getString("key").equals("BTemp")){
                    bTemp = values.getJSONObject(i).getString("value");
                }
            }



            //hTemp = values.getJSONObject(12).getString("HTemp");

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return bTemp;
    }
}