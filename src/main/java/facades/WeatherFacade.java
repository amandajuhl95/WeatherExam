/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.WeatherForecastDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errorhandling.NotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author aamandajuhl
 */
public class WeatherFacade extends DataFacade {

    private static WeatherFacade instance;
    Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    //Private Constructor to ensure Singleton
    private WeatherFacade() {
    }

    /**
     *
     * @return an instance of this facade class.
     */
    public static WeatherFacade getFacade() {
        if (instance == null) {
            instance = new WeatherFacade();
        }
        return instance;
    }

    public List<WeatherForecastDTO> getWeatherForecast(int citycode, int year, int month, int day) throws NotFoundException {

        try {
            List<WeatherForecastDTO> forecast = new ArrayList();
            String weatherJson = super.getData((citycode + "/" + year + "/" + month + "/" + day));
            WeatherForecast[] weather = GSON.fromJson(weatherJson, WeatherForecast[].class);

            if (weather == null || weather.length <= 0) {
                throw new WebApplicationException("No weatherforecast found for the date", 400);
            }

            if (weather.length >= 5) {
                for (int i = 0; i < 5; i++) {
                    forecast.add(new WeatherForecastDTO(weather[i]));
                }

            } else {
                for (WeatherForecast f : weather) {
                    forecast.add(new WeatherForecastDTO(f));
                }
            }

            return forecast;

        } catch (IOException e) {
            throw new NotFoundException("The city doesnt exsist");
        }
    }

    public List<WeatherForecastDTO> getWeatherForecasts(int citycode) throws NotFoundException {

        try {
            List<WeatherForecastDTO> forecast = new ArrayList();
            String weatherJson = super.getData(Integer.toString(citycode));
            String weatherArray = weatherJson.split("\"consolidated_weather\":")[1].split(",\"time\"")[0];
            WeatherForecast[] weather = GSON.fromJson(weatherArray, WeatherForecast[].class);

            if (weather == null || weather.length <= 0) {
                throw new WebApplicationException("No weatherforecast found for the date", 400);
            }

            if (weather.length >= 5) {
                for (int i = 0; i < 5; i++) {

                    WeatherForecastDTO f = new WeatherForecastDTO(weather[i]);
                    f.setDateTime(weather[i].getApplicable_date());
                    forecast.add(f);
                }

            } else {
                for (WeatherForecast f : weather) {
                    forecast.add(new WeatherForecastDTO(f));
                }
            }

            return forecast;

        } catch (IOException e) {
            throw new NotFoundException("The city doesnt exsist");
        }
    }
}
