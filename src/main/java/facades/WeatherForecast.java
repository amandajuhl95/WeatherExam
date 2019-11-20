/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author aamandajuhl
 */
public class WeatherForecast {
    
    private final String weather_state_abbr;
    private final String weather_state_name;
    private final double the_temp;
    private final String wind_direction_compass;
    private final double wind_speed;
    private final int humidity;
    private final int predictability;
    private final Date created;
    SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss.SSS");

    public WeatherForecast(String weather_state_abbr, String weather_state_name, String the_temp, String wind_direction_compass, String wind_speed, String humidity, String predictability, String created) throws ParseException {
        this.weather_state_abbr = weather_state_abbr;
        this.weather_state_name = weather_state_name;
        this.the_temp = Double.parseDouble(the_temp);
        this.wind_direction_compass = wind_direction_compass;
        this.wind_speed = Double.parseDouble(wind_speed);
        this.humidity = Integer.parseInt(humidity);
        this.predictability = Integer.parseInt(predictability);
        this.created = formatter.parse(created);
    }

    public String getWeather_state_abbr() {
        return weather_state_abbr;
    }

    public String getWeather_state_name() {
        return weather_state_name;
    }

    public double getThe_temp() {
        return the_temp;
    }

    public String getWind_direction_compass() {
        return wind_direction_compass;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPredictability() {
        return predictability;
    }

    public Date getCreated() {
        return created;
    }

    
    
}
