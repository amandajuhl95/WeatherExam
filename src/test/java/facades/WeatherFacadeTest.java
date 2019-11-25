/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.WeatherForecastDTO;
import errorhandling.NotFoundException;
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

    private static WeatherFacade facade;

    public WeatherFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        facade = WeatherFacade.getFacade();
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
        double expResult = 9.85;
        List<WeatherForecastDTO> result = facade.getWeatherForecast(citycode, year, month, day);
        assertEquals(expResult, result.get(0).getWindSpeed());

    }

    /**
     * Test of notFoundGetWeatherForecast method, of class WeatherFacade. Test
     * error message 400, if the countrycode doesn't exsist.
     *
     * @throws errorhandling.NotFoundException
     */
    @Test
    public void testNotFoundGetWeatherForecast() throws NotFoundException {
        System.out.println("Negative countrycode");
        try {
            facade.getWeatherForecast(368147, 2020, 12, 12);
            fail();
        } catch (NotFoundException ex) {

            assertEquals("The city doesnt exsist", ex.getMessage());

        }
    }

    /**
     * Test of negativeGetWeatherForecast method, of class WeatherFacade. Test
     * error message 400, if the weather forecast for the given city and date
     * doesn't exsist.
     *
     * @throws errorhandling.NotFoundException
     */
    @Test
    public void testNegativeGetWeatherForecast() throws NotFoundException {
        System.out.println("Negative getWeatherForecast");
        try {
            facade.getWeatherForecast(368148, 2020, 12, 12);
            fail();
        } catch (NotFoundException ex) {

            assertEquals("No weatherforecast found for the date", ex.getMessage());

        }
    }

}
