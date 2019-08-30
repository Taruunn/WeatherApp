package com.example.weather;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    Button getWeatherButton, clear;
    EditText searchCity;
    TextView sample;
    String baseURl = "https://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&units=metric&appid=8d0bdb77f649f492cff271d0eb05dd46";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWeatherButton = findViewById(R.id.getWeather);
        clear = findViewById(R.id.clear);
        searchCity = findViewById(R.id.searchCity);
        sample = findViewById(R.id.sample);
        getWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchCity.getText().toString().trim().length() <= 0) {
                    Toast.makeText(MainActivity.this, "Please Enter City", Toast.LENGTH_LONG).show();
                } else {
                    final String myURL = baseURl + searchCity.getText().toString() + API;

                    final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {

                                    try {
                                        //get temp & humidity
                                        String main = jsonObject.getString("main");
                                        JSONObject Temp = new JSONObject(main);
                                        String mTemp = Temp.getString("temp");
                                        String humidity = Temp.getString("humidity");


                                        //get sunset,sunrise time
                                        String sys = jsonObject.getString("sys");
                                        JSONObject day = new JSONObject(sys);
                                        String msunrise = day.getString("sunrise");
                                        String msunset = day.getString("sunset");
                                        String CountryName = day.getString("country");


                                        //Convert into Long
                                        Long mSunrise = Long.parseLong(msunrise);
                                        long mSunset = Long.parseLong(msunset);

                                        Log.i("sunset", "fromapi" + mSunset);

                                        //Convert UTC time to Indian Standerd Time
                                        Date sunrise = new Date(mSunrise * 1000);
                                        SimpleDateFormat simpleDateFormatsunrise = new SimpleDateFormat("HH:mm");
                                        simpleDateFormatsunrise.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
                                        String finalSunrise = simpleDateFormatsunrise.format(sunrise);

                                        Date sunset = new Date(mSunset * 1000);
                                        SimpleDateFormat simpleDateFormatsunset = new SimpleDateFormat("HH:mm");
                                        simpleDateFormatsunset.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
                                        String finalSunset = simpleDateFormatsunset.format(sunset);

                                        //get city name
                                        String Name = jsonObject.getString("name");

                                        //getWindSpeed
                                        String wind = jsonObject.getString("wind");
                                        JSONObject mwind = new JSONObject(wind);
                                        String mWind = mwind.getString("speed");

                                        //getMain Weather info
                                        String allinfo = jsonObject.getString("weather");
                                        JSONArray weatherinfo = new JSONArray(allinfo);
                                        for (int i = 0; i < weatherinfo.length(); i++) {
                                            JSONObject finalWatherInfo = weatherinfo.getJSONObject(i);
                                            String WeatherInfo = finalWatherInfo.getString("description");
                                            String Weatherinfosecond = finalWatherInfo.getString("main");

                                            //getlong*lat info
                                            String coord = jsonObject.getString("coord");
                                            JSONObject location = new JSONObject(coord);
                                            String lon = location.getString("lon");
                                            String lat = location.getString("lat");

                                            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                                            intent.putExtra("mTemp", mTemp);
                                            intent.putExtra("humidity", humidity);
                                            intent.putExtra("finalSunrise", finalSunrise);
                                            intent.putExtra("finalSunset", finalSunset);
                                            intent.putExtra("Name", Name);
                                            intent.putExtra("mWind", mWind);
                                            intent.putExtra("CountryName", CountryName);
                                            intent.putExtra("WeatherInfo", WeatherInfo);
                                            intent.putExtra("Weatherinfosecond", Weatherinfosecond);
                                            intent.putExtra("lon", lon);
                                            intent.putExtra("lat", lat);
                                            startActivity(intent);
                                            //finish();
                                        }

                                    } catch (JSONException e) {

                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
                    );

                    MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchCity.setText("");
            }
        });


    }
}
