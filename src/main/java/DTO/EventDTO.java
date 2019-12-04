/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import facades.Event;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventDTO implements Comparable<EventDTO> {
    
   private String eventAddress;
   private String eventDate;
   private String eventName;
   private String eventURL;
   DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    public EventDTO(Event event) {
        this.eventAddress = event.getEventAddress();
        this.eventDate = formatter.format(event.getEventDate());
        this.eventName = event.getEventName();
        this.eventURL = event.getEventURL();
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventURL() {
        return eventURL;
    }

    public void setEventURL(String eventURL) {
        this.eventURL = eventURL;
    }

   @Override
    public int compareTo(EventDTO event) {
       try {
           if(event == null) return -1;
           
           if(formatter.parse(this.getEventDate()).after(formatter.parse(event.getEventDate())))  return 1;
           
           if(formatter.parse(this.getEventDate()).equals(formatter.parse(event.getEventDate())))
           {
               return Character.compare(this.eventName.toLowerCase().charAt(0),event.eventName.toLowerCase().charAt(0));
           }
           
       } catch (ParseException ex) {
           Logger.getLogger(EventDTO.class.getName()).log(Level.SEVERE, null, ex);
       }
       return -1;
    }
}
