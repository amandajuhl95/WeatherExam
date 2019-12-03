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
 * @author sofieamalielandt
 */
public class Event {

    private final String eventAddress;
    private final Date eventDate;
    private final String eventName;
    private final String eventURL;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public Event(String eventAddress, String eventDate, String eventName, String eventURL) throws ParseException {
        this.eventAddress = eventAddress;
        this.eventDate = formatter.parse(eventDate);
        this.eventName = eventName;
        this.eventURL = eventURL;
    }

    public String getEventAddress() {
        return eventAddress;
    }
    
    public Date getEventDate() {
        return eventDate;
    }

    public String getEventName() {
        return eventName;
    }
    
    public String getEventURL() {
        return eventURL;
    }


}
