/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Country;
import java.util.List;


public class CountryDTO {

    private final String title;
    private final int woeid;
    private List<CityDTO> children;
    Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public CountryDTO(Country country) {
        this.title = country.getName();
        this.woeid = country.getCountryCode();
        this.children = null;
    }

    public CountryDTO(String title, String woeid, String children) {
        this.title = title;
        this.woeid = Integer.parseInt(woeid);
        this.children = (List<CityDTO>) GSON.fromJson(children, CityDTO.class);
    }

    public List<CityDTO> getCities() {
        return children;
    }

    public String getName() {
        return title;
    }

    public int getCountryCode() {
        return woeid;
    }

    @Override
    public String toString() {
        return "CountryDTO{" + "title=" + title + ", woeid=" + woeid + ", children=" + children + '}';
    }

}
