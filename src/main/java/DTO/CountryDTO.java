/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Country;

/**
 *
 * @author sofieamalielandt
 */
public class CountryDTO {

   private String name;
   private int countryCode;

    public CountryDTO(Country country) {
        this.name = country.getName();
        this.countryCode = country.getCountryCode();
    }

    public String getName() {
        return name;
    }

    public int getCountryCode() {
        return countryCode;
    }
    
}
