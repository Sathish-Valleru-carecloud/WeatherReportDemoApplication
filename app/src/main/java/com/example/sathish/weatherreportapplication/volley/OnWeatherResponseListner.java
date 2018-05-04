package com.example.sathish.weatherreportapplication.volley;

import com.example.sathish.weatherreportapplication.model.WeatherResponse;

/**
 * Created by Sathish on 5/3/2018.
 */

public interface OnWeatherResponseListner {

    void OnSuccess(WeatherResponse response);

    void onFailure(String error);
}
