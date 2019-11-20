/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

public class CityDTO {
    
     private String title;
   private int woeid;

    public CityDTO(String title, String woeid) {
        this.title = title;
        this.woeid = Integer.parseInt(woeid);
    }

    public String getName() {
        return title;
    }

    public int getCityCode() {
        return woeid;
    }

    @Override
    public String toString() {
        return "CityDTO{" + "name=" + title + ", cityCode=" + woeid + '}';
    }
    
    
    
    
}
