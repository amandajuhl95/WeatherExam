package rest;

import DTO.CityDTO;
import DTO.CountryDTO;
import DTO.WeatherForecastDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errorhandling.NotFoundException;
import utils.EMF_Creator;
import facades.CountryFacade;
import facades.WeatherFacade;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("weather")
public class WeatherResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final CountryFacade CF = CountryFacade.getFacade(EMF);
    private static final WeatherFacade WF = WeatherFacade.getFacade();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello to TheCloud\"}";
    }

    @GET
    @Path("/countries")
    @Produces({MediaType.APPLICATION_JSON})
    public List<CountryDTO> getCountries() {

        try {
            return CF.getCountries();

        } catch (NotFoundException ex) {

            throw new WebApplicationException(ex.getMessage(), 400);
        }
    }

    @GET
    @Path("/country/{country}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<CityDTO> getCountry(@PathParam("country") int countrycode) {

        if (countrycode > 99999999 || countrycode < 1000000) {
            throw new WebApplicationException("Countrycodes must be between 7 and 8 digits", 400);
        }
        try {
            return CF.getCities(countrycode);

        } catch (NotFoundException ex) {

            throw new WebApplicationException(ex.getMessage(), 400);
        }
    }

    @GET
    @Path("/city/{city}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<WeatherForecastDTO> getCityWeather(@PathParam("city") String city) {
        int citycode;

        try {
            
            if (!city.matches("[0-9]+")) {

                CityDTO c = CF.getCity(city);
                citycode = c.getCityCode();

            } else {
                citycode = Integer.parseInt(city);
            }

            if (citycode > 9999999 || citycode < 1000) {
                throw new WebApplicationException("Citycodes must be between 4 and 7 digits", 400);
            }

            return WF.getWeatherForecasts(citycode);

        } catch (NotFoundException | NumberFormatException | WebApplicationException ex) {

            throw new WebApplicationException(ex.getMessage(), 400);
        }

    }

    @GET
    @Path("/city/{city}/{year}/{month}/{day}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<WeatherForecastDTO> getCityWeatherByDate(@PathParam("city") String city, @PathParam("year") int year, @PathParam("month") int month, @PathParam("day") int day) {

        int citycode;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR) + 1;

        try {
            if (!city.matches("[0-9]+")) {

                CityDTO c = CF.getCity(city);
                citycode = c.getCityCode();

            } else {
                citycode = Integer.parseInt(city);
            }

            if (citycode > 9999999 || citycode < 1000) {
                throw new WebApplicationException("Citycodes must be between 4 and 7 digits", 400);
            }

            if (year < 2013 || month < 1 || month > 12 || day > 31 || day < 1) {
                throw new WebApplicationException("I dont think we use the same calendar", 400);
            }

            if (year > currentYear) {
                throw new WebApplicationException("Obviously we cant predict the weather THAT far ahead", 400);
            }

            return WF.getWeatherForecast(citycode, year, month, day);

        } catch (Exception ex) {

            throw new WebApplicationException(ex.getMessage(), 400);
        }

    }

}
