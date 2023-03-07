package com.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("main")
    private WeatherMain weatherMain;
    @SerializedName("wind")
    private WeatherWind weatherWind;
    @SerializedName("weather")
    private List<Weather> weatherList;

    public WeatherMain getWeatherDetails() {
        return weatherMain;
    }
    public WeatherWind getWeatherWind() {
        return weatherWind;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public static class WeatherMain {
        @SerializedName("temp")
        private float temperature;
        @SerializedName("pressure")
        private float pressure;

        public float getPressure() {
            return pressure;
        }

        public float getTemperature() {
            return temperature;
        }
    }
    public static class WeatherWind {
        @SerializedName("speed")
        private float speed;

        public float getSpeed() {
            return speed;
        }

    }

    public static class Weather {
        @SerializedName("main")
        private String main;
        @SerializedName("icon")
        private String icon;

        public String getIcon() {
            return icon;
        }
        public String getMain() {
            return main;
        }
    }
}
