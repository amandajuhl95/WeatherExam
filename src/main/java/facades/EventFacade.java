/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.CityDTO;
import DTO.EventDTO;
import DTO.WeatherForecastDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errorhandling.NotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author benja
 */
public class EventFacade {
    
      Gson GSON = new GsonBuilder().setPrettyPrinting().create();
     private static EventFacade instance;
     
    
    public static EventFacade getEventFacade() {
        if (instance == null) {

            instance = new EventFacade();
        }
        return instance;
    }
    
    
    private String fetchEvents(String endpoint)throws MalformedURLException, IOException{
    
        
        URL url = new URL("https://runivn.dk/3SEMPROJECT/api/resource/events"+ endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=ISO-8859-1");
        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        scan.close();
        return jsonStr;
    
    }

    public List<EventDTO> getEvents(String start, String end, String country, String city) throws NotFoundException {
         EventFacade EF = getEventFacade();


String Endpoint = "?startdate="+start+"&enddate="+end+"&country="+country+"&city="+city;
        
        
         try {
          List<EventDTO> eventsList = new ArrayList();
          String JsonEvents = EF.fetchEvents(Endpoint);
          EventDTO[] events = GSON.fromJson(JsonEvents, EventDTO[].class);

            if (events == null || events.length <= 0) {
                throw new WebApplicationException("No Events was found", 400);
            }

             
                for (EventDTO e : events) {
                  
                    eventsList.add(e);
                }
            
 return eventsList;
     
           

        } catch (IOException e) {
            if(e.getMessage()=="No events for this City exists"){
            return new ArrayList();
            }
            else{
            throw new NotFoundException("There are no events in that city for the given date");
            }
        }

    }
    

    
    
}
