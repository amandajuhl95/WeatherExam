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
import java.net.MalformedURLException;
import java.util.List;
import javax.persistence.EntityManagerFactory;

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

    public WeatherForecastDTO getWeatherForecast(int citycode, int year, int month, int day) throws NotFoundException {

        try {
            String jsonForecasts = super.getData((citycode + "/" + year + "/" + month + "/" + day));
            WeatherForecast[] weatherForecasts = GSON.fromJson(jsonForecasts, WeatherForecast[].class);
            
            return new WeatherForecastDTO(weatherForecasts[0]);

        } catch (IOException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
