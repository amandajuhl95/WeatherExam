/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.EventDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errorhandling.NotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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

    public EventFacade() {
    }

    public static EventFacade getEventFacade() {
        if (instance == null) {

            instance = new EventFacade();
        }
        return instance;
    }

    private String fetchEvents(String start, String end, String country, String city) throws MalformedURLException, IOException {

        URL url = new URL("https://runivn.dk/3SEMPROJECT/api/resource/events?startdate=" + start + "&enddate=" + end + "&country=" + country + "&city=" + city);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        scan.close();
        return jsonStr;

    }

    public List<EventDTO> getEvents(String start, String end, String country, String city) throws NotFoundException {

        try {
            List<EventDTO> eventsList = new ArrayList();
            String JsonEvents = fetchEvents(start, end, country, city);
            Event[] events = GSON.fromJson(JsonEvents, Event[].class);

            if (events == null || events.length <= 0) {
                throw new WebApplicationException("No Events was found", 400);
            }

            for (Event event : events) {
                eventsList.add(new EventDTO(event));
            }

            return eventsList;
        } catch (IOException e) {
            throw new NotFoundException("There are no events in that city for the given date");
        }
    }
}
