package com.example.sathish.weatherreportapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sathish.weatherreportapplication.model.WeatherResponse;
import com.example.sathish.weatherreportapplication.volley.AppVolleyApiManager;
import com.example.sathish.weatherreportapplication.volley.OnWeatherResponseListner;
import com.example.sathish.weatherreportapplication.volley.WeatherDataController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherInfoActivity extends AppCompatActivity {

    private static final String tag = WeatherInfoActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forcast);

     final     TextView humidityValue = findViewById(R.id.humidity_value_text);
     final     TextView  cloudy = findViewById(R.id.cloudy);
     final     TextView  weatherDescription = findViewById(R.id.weather_info);
     final     TextView  sunriseTime = findViewById(R.id.sunrise_value_text);
     final     TextView  temperatureValue = findViewById(R.id.temperature);
     final     TextView  pressureValue = findViewById(R.id.pressure_value_text);
     final     TextView  windValue = findViewById(R.id.speed_text);
     final     TextView  city_country = findViewById(R.id.city_country);
        final LinearLayout linearLayout = findViewById(R.id.weather_desc);

        String zipCode = getIntent().getStringExtra("zipCode");
        AppVolleyApiManager.initVolley(getApplicationContext());

// get the response and setting to the particular texts
        WeatherDataController.getWeatherInfo(zipCode, new OnWeatherResponseListner() {
            @Override
            public void OnSuccess(WeatherResponse response) {
                if(response!=null){

                    if(response.getMain()!=null) {
                        String humidity = Integer.toString(response.getMain().getHumidity());
                        String temperture = Double.toString(response.getMain().getTemp());
                        String pressure= Double.toString(response.getMain().getPressure());
                        humidityValue.setText(humidity + "%");
                        temperatureValue.setText(temperture +  (char) 0x00B0+"F");
                        pressureValue.setText(pressure + " " +"hpa");
                    }
                    if(response.getWeather()!=null) {
                        weatherDescription.setText(response.getWeather().get(0).getDescription());
                        cloudy.setText(response.getWeather().get(0).getMain());
                    }
                    if(response.getWind()!=null) {
                        String wind = Double.toString(response.getWind().getSpeed());
                        windValue.setText(wind + " " +"m/s");
                    }
                    if(response.getSys()!=null) {
                        sunriseTime.setText(convertMsToTIme(response.getSys().getSunrise()).toString());
                        city_country.setText(response.getName() + ", "+ response.getSys().getCountry());
                    }

                }
                else{
                    linearLayout.setVisibility(View.GONE);
                    findViewById(R.id.bottom_line).setVisibility(View.GONE);
                    findViewById(R.id.weather_layout).setVisibility(View.GONE);
                    findViewById(R.id.empty_info).setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onFailure(String error) {
                Log.e(tag,"Response Failed "+error);
            }
        });

    }
// converting the milliseconds to local time 12 hour format
    private String convertMsToTIme(int ms){
        Date date = new Date(ms);
        SimpleDateFormat dateFormat = new SimpleDateFormat(" hh:mm aa", Locale.US);
        return dateFormat.format(date);
    }
}
