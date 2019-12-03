/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import facades.Event;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EventDTO {
    
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


    
    
    
    
}
