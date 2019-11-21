/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.WeatherForecastDTO;
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
     * Test of getFacade method, of class WeatherFacade.
     */
    @Test
    public void testGetFacade() {
        System.out.println("getFacade");
        WeatherFacade expResult = null;
        WeatherFacade result = WeatherFacade.getFacade();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWeatherForecast method, of class WeatherFacade.
     */
    @Test
    public void testGetWeatherForecast() throws Exception {
        System.out.println("getWeatherForecast");
        int citycode = 0;
        int year = 0;
        int month = 0;
        int day = 0;
        WeatherFacade instance = null;
        List<WeatherForecastDTO> expResult = null;
        List<WeatherForecastDTO> result = instance.getWeatherForecast(citycode, year, month, day);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
