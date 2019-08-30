package com.example.weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    TextView Humidity, name, temp, description, windspeed, Sunrise, Sunset, countryname, info, lon, lat;
    Button backhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        backhome = findViewById(R.id.backhome);
        Humidity = findViewById(R.id.humidity);
        description = findViewById(R.id.description);
        windspeed = findViewById(R.id.windspeed);
        temp = findViewById(R.id.temp);
        name = findViewById(R.id.name);
        Sunrise = findViewById(R.id.sunrise);
        Sunset = findViewById(R.id.sunset);
        countryname = findViewById(R.id.countryname);
        info = findViewById(R.id.info);
        lon = findViewById(R.id.lon);
        lat = findViewById(R.id.lat);


        Bundle bundle = getIntent().getExtras();
        final String humidity = bundle.getString("humidity");
        final String mTemp = bundle.getString("mTemp");
        final String sunrise = bundle.getString("finalSunrise");
        final String sunset = bundle.getString("finalSunset");
        final String mname = bundle.getString("Name");
        final String wind = bundle.getString("mWind");
        final String Countryname = bundle.getString("CountryName");
        final String Description = bundle.getString("WeatherInfo");
        final String Descriptionsecond = bundle.getString("Weatherinfosecond");
        final String Lon = bundle.getString("lon");
        final String Lat = bundle.getString("lat");
        double mTTemp = (Double.parseDouble(mTemp));
        int finalTemp = (int) mTTemp;
        String semifinaltemp = String.valueOf(finalTemp);


        Humidity.setText(humidity);
        temp.setText(semifinaltemp);
        Sunrise.setText(sunrise);
        Sunset.setText(sunset);
        name.setText(mname);
        windspeed.setText(wind);
        countryname.setText(Countryname);
        description.setText(Description);
        info.setText(Descriptionsecond);
        lon.setText(Lon);
        lat.setText(Lat);

        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
