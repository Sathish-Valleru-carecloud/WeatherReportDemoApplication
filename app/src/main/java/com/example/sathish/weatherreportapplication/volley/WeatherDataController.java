package com.example.sathish.weatherreportapplication.volley;

import android.util.Log;

import com.example.sathish.weatherreportapplication.model.WeatherResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by Sathish on 5/3/2018.
 */

public class WeatherDataController {

    private static final String WEATHER_API = "http://api.openweathermap.org/data/2.5/weather?zip=%s&APPID=ea0cce16de65fb9a61612ee4fc5f017d";


    public static void getWeatherInfo(String zipCode, final OnWeatherResponseListner listner){
        AppVolleyApiManager.instance().getJsonObjectResponse(String.format(WEATHER_API, zipCode), new AppVolleyNetWorkResponse() {
            @Override
            public void onError(String error) {

                listner.onFailure(error);
            }

            @Override
            public void onSuccessResponse(JSONObject responseObject) {

                if (responseObject != null) {
                    WeatherResponse response = new Gson().fromJson(responseObject.toString(), WeatherResponse.class);
                    listner.OnSuccess(response);
                }else{
                    listner.onFailure("No data");
                }
            }
        });
    }
}
