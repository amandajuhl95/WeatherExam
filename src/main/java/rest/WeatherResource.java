package rest;

import DTO.CityDTO;
import DTO.CountryDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errorhandling.NotFoundException;
import utils.EMF_Creator;
import facades.CountryFacade;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("weather")
public class WeatherResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final CountryFacade CF = CountryFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("/countries")
    @Produces({MediaType.APPLICATION_JSON})
    public List<CountryDTO> getCountries() throws NotFoundException {
         return CF.getCountries();
    }
    
    @GET
    @Path("/country/{country}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<CityDTO> getCountry(@PathParam("country") int countrycode) throws IOException {
         return CF.getCities(countrycode);
    }
    
    @GET
    @Path("/city/{city}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getCityWeather(@PathParam("city") int citycode) {
         return "{\"msg\":\"Weather prognosis for " + citycode + "\"}";
    }
    
    @GET
    @Path("/city/{city}/{date}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getCityWeatherByDate(@PathParam("city") int citycode, @PathParam("date") String date) {
         return "{\"msg\":\"Weather prognosis for " + citycode + " on the " + date + "\"}";
    }

}
