package com.weather;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherViewModel extends ViewModel {
    String result="";
    private MutableLiveData<WeatherResponse> weatherResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();

    public LiveData<WeatherResponse> getWeatherResponseLiveData() {
        return weatherResponseLiveData;
    }

    public LiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }

    public String getWeatherDetail(Context context, String queryData) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherMapService service = retrofit.create(OpenWeatherMapService.class);

        Call<WeatherResponse> call = service.getWeather(queryData, "cc2400d1a2455bf3e013287c5a03c818");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    weatherResponseLiveData.setValue(weatherResponse);
                    Log.d("CheckLiveData1",weatherResponse.toString());
                    result=new Gson().toJson(response.body());
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        errorMessageLiveData.setValue(errorBody);
                        Log.d("CheckLiveData2",errorBody.toString());
                        result=errorBody;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                errorMessageLiveData.setValue(t.getMessage());
                Log.d("CheckLiveData3",t.getMessage());
                result=t.getMessage();
            }
        });
        return result;
    }
    public String getWeatherDetailByLatLang(Context context, double lat, double lang) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherMapService service = retrofit.create(OpenWeatherMapService.class);

        Call<WeatherResponse> call = service.getWeatherByLatLang(lat,lang, "cc2400d1a2455bf3e013287c5a03c818");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    weatherResponseLiveData.setValue(weatherResponse);
                    Log.d("CheckLiveData1",weatherResponse.toString());
                    result=new Gson().toJson(response.body());
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        errorMessageLiveData.setValue(errorBody);
                        Log.d("CheckLiveData2",errorBody.toString());
                        result=errorBody;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                errorMessageLiveData.setValue(t.getMessage());
                Log.d("CheckLiveData3",t.getMessage());
                result=t.getMessage();
            }
        });
        return result;
    }
}