package com.weather;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.weather.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    private WeatherViewModel weatherViewModel;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        binding.buttonSearch.setOnClickListener(v -> {
            String cityName = binding.editTextCityName.getText().toString();
            String stateCode = binding.editTextStateCode.getText().toString();
            String countryCode = binding.editTextCountryCode.getText().toString();
            String result = "";
            if (!cityName.isEmpty() && !stateCode.isEmpty() && !countryCode.isEmpty()) {
                result = weatherViewModel.getWeatherDetail(getApplicationContext(), cityName + "," + stateCode + "," + countryCode);
            } else if (!cityName.isEmpty()) {
                result = weatherViewModel.getWeatherDetail(getApplicationContext(), cityName);
            } else {
                Toast.makeText(getApplicationContext(), "Please give the input data", Toast.LENGTH_SHORT).show();
            }

            //binding.resultData.setText(result);

            if (weatherViewModel.getWeatherResponseLiveData().getValue()!=null) {
                binding.setWeatherResponse(weatherViewModel.getWeatherResponseLiveData().getValue());
                String icon = weatherViewModel.getWeatherResponseLiveData().getValue().getWeatherList().get(0).getIcon();
                Glide.with(this)
                        .load("https://openweathermap.org/img/wn/" + icon + "@2x.png")
                        .centerCrop()
                        .into(binding.imageView);
            }

        });

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission is already granted
            // Get the user's location
            getUserLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                // Get the user's location
                getUserLocation();
            } else {
                // Permission is denied
                // Handle the denied permission
            }
        }
    }

    private void getUserLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                // Get the user's latitude and longitude
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                    String result = weatherViewModel.getWeatherDetailByLatLang(getApplicationContext(), latitude, longitude);
                    //binding.resultData.setText(result);

                    binding.setWeatherResponse(weatherViewModel.getWeatherResponseLiveData().getValue());
                    if (weatherViewModel.getWeatherResponseLiveData().getValue()!=null) {
                        String icon = weatherViewModel.getWeatherResponseLiveData().getValue().getWeatherList().get(0).getIcon();
                        Glide.with(this)
                                .load("https://openweathermap.org/img/wn/" + icon + "@2x.png")
                                .centerCrop()
                                .into(binding.imageView);
                    }
            }
        }
    }
}