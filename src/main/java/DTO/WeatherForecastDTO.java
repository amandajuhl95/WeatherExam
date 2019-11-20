/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import facades.WeatherForecast;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author benja
 */
public class WeatherForecastDTO {

   private String dateTime;
   private String weatherStatus;
   private String weatherIcon;
   private double temp;
   private String windDirection;
   private double windSpeed;
   private int humidity;
   private int predictability;
   private String funnyAdvice;
   DateFormat formatter =  new SimpleDateFormat("dd.MM.yyyy");

    public WeatherForecastDTO(WeatherForecast forecast) {
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.dateTime = formatter.format(forecast.getCreated());
        this.weatherStatus = forecast.getWeather_state_name();
        this.weatherIcon = "https://www.metaweather.com/static/img/weather/png/" + forecast.getWeather_state_abbr() + ".png";
        this.temp = forecast.getThe_temp();
        this.windDirection = forecast.getWind_direction_compass();
        this.windSpeed = forecast.getWind_speed();
        this.humidity = forecast.getHumidity();
        this.predictability = forecast.getPredictability();
        this.funnyAdvice = "blabla";
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public double getTemp() {
        return temp;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPredictability() {
        return predictability;
    }

    public String getFunnyAdvice() {
        return funnyAdvice;
    }



}
