package com.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface OpenWeatherMapService {
    @GET("/data/2.5/weather")
    Call<WeatherResponse> getWeather(@Query("q")String queryData, @Query("appid")String apiKey);
    @GET("/data/2.5/weather")
    Call<WeatherResponse> getWeatherByLatLang(@Query("lat")double lat,@Query("lon")double lon, @Query("appid")String apiKey);
}
