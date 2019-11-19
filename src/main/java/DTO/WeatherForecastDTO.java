/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author benja
 */
public class WeatherForecastDTO {
   

private String weatherIcon;
private String temp;
private String windDirection;
private String windSpeed;
private String humidity;
private String predictability;
private String funnyAdvice;

    public WeatherForecastDTO(String weather_state_abbr, String temp, String wind_direction_compass, String windSpeed, String humidity, String predictability, String funnyAdvice) {
        this.weatherIcon = weather_state_abbr;
        this.temp = temp;
        this.windDirection = wind_direction_compass;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.predictability = predictability;
        this.funnyAdvice = funnyAdvice;
    }


    
}
 