/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.WeatherForecastDTO;
import static facades.WeatherFacade.getFacade;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author aamandajuhl
 */
public class WeatherFacadeTest {
    
    public WeatherFacadeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

   

    /**
     * Test of getWeatherForecast method, of class WeatherFacade.
     */
    @Test
    public void testGetWeatherForecast() throws Exception {
        System.out.println("getWeatherForecast");
        int citycode = 44418;
        int year = 2013;
        int month = 4;
        int day = 27;
        WeatherFacade instance = getFacade();
        double expResult = 9.85;
        List<WeatherForecastDTO> result = instance.getWeatherForecast(citycode, year, month, day);
        assertEquals(expResult, result.get(0).getWindSpeed());
 
    }
    
}
