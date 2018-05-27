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
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String SERVER_URL =
                "http://192.168.1.140:3000/users/";
    //"http://178.169.176.184:8000/users/";

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
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//            urlConnection.setRequestMethod("PUT");
//            //
//
//            urlConnection.setDoInput(true);
//            //urlConnection.setFixedLengthStreamingMode(data.toString().length());
//
//            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
//            Log.d("JsonData", data.toString());
//            out.write(data.toString());
//            out.close();
//
//            urlConnection.disconnect();
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("PUT");
        httpCon.setRequestProperty("Content-Type", "application/json");
        OutputStreamWriter out = new OutputStreamWriter(
                httpCon.getOutputStream());
        out.write(data.toString());
        out.close();
        httpCon.getInputStream();

    }

    public static HashMap<String, String> getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String jsonServerResponse = null;
        HashMap<String, String> values = new HashMap<>();

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


            JSONObject response = new JSONObject(jsonServerResponse);
            JSONArray jsonValues = response.getJSONArray("values");

            for(int i = 0 ;  i < jsonValues.length() ; ++ i){
                JSONObject jsonObject = jsonValues.getJSONObject(i);
                values.put(jsonObject.getString("key"), jsonObject.getString("value"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return values;
    }

}