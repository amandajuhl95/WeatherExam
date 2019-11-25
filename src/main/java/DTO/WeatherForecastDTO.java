/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import facades.WeatherForecast;
import java.text.DateFormat;
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
    DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy kk:mm");
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

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
        this.funnyAdvice = funnyAdvice();
    }

    private String funnyAdvice() {
          if ("Heavy Rain".equals(weatherStatus) ) {
            return "an umbrella is not going to be enough, take a cap or drown in rain";
        }
          else if ("Hail".equals(weatherStatus)){
              return "Is it snow ? ... is it rain ? ... no, its hail, so keep your head down or you will turn blind!";
          }
          else if ("Thunderstorm".equals(weatherStatus) || "Rain".contains(weatherStatus) || "Showers".equals(weatherStatus)) {
            return "Remember your umbrella today!";
        } else if (temp >= 25 && "Clear".equals(weatherStatus)) {
            return "There is no such thing as a healthy tan, use sunscreen today!";
        } else if (windSpeed >= 12 && "Clear".equals(weatherStatus)) {
            return "Perfect weather for flying with kites!";
        }
          else if (windSpeed >= 25) {
            return "if you are going on a bikeride we hope your destination is "+ windDirection +" otherwise it's going to be a tough ride!";
        }else if ("Snow".equals(weatherStatus)) {
            return "Do you wanna build a snowman?";
        } else if (temp <= 5) {
            return "It's cold outside, were a sweater... or die";
        }
        else if ("Heavy Cloud".equals(weatherStatus)) {
            return "Goodbye sunshine the clouds have taken ower today";
        }
           else if (temp < 15) {
            return "Why would humanity even set foot in a place this cold!! our advice: leave, dont look back! just get out of there";
        }
           else if (temp < 0) {
            return "snif snif... Whats that? hot coco on the stove?.. its freazing outside you better stay in and drink up!";
        }
             else if (predictability < 35) {
            return "I woudent trust so much in the weather reports today, they are probably wrong";
        }
          
            else if ("Light Cloud".equals(weatherStatus)) {
            return "I woudent trust so much in the weather reports today, they are probably wrong";
        }
        
        return "Not a good day for an umbrella, neither for a sweather nor shorts. Our advice... Don't go outside, don't answer your phone, take a you day! You deserve it you beautiful bastard!";
    }

    public String getDateTime() {
        return dateTime;
    }
    
     public void setDateTime(Date dateTime) {
        this.dateTime = dateFormatter.format(dateTime);
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
