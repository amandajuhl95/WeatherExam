package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.RenameMe;
import utils.EMF_Creator;
import facades.FacadeExample;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("weather")
public class WeatherResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final FacadeExample FACADE = FacadeExample.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("/countries")
    @Produces({MediaType.APPLICATION_JSON})
    public String getCountries() {
         return "{\"msg\":\"Countries\"}";
    }
    
    @GET
    @Path("/country/{country}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getCountry(@PathParam("country") int countrycode) {
         return "{\"msg\":\"Cities in " + countrycode + "\"}";
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
