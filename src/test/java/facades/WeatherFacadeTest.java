/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.WeatherForecastDTO;
import errorhandling.NotFoundException;
import java.text.ParseException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Uncomment the line below, to temporarily disable this test
//@Disabled
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

    /**
     * Test of getFacade method, of class CountryFacade.
     */
    @Test
    public void testGetFacade() {
        System.out.println("getWeatherFacade");

        WeatherFacade expResult = facade;
        WeatherFacade result = WeatherFacade.getFacade();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWeatherForecast method, of class WeatherFacade.
     *
     * @throws errorhandling.NotFoundException
     */
    @Test
    public void testGetWeatherForecast() throws NotFoundException, ParseException {
        System.out.println("getWeatherForecast");

        double expResult = 9.85;
        List<WeatherForecastDTO> result = facade.getWeatherForecast(44418, 2013, 4, 27);

        assertEquals(expResult, result.get(0).getWindSpeed());
        assertEquals(5, result.size());

    }

    /**
     * Test of notFoundGetWeatherForecast method, of class WeatherFacade. Test
     * error message 400, if the countrycode doesn't exsist.
     *
     * @throws errorhandling.NotFoundException
     */
    @Test
    public void testNotFoundGetWeatherForecast() throws NotFoundException, ParseException {
        System.out.println("Negative countrycode for 1 day prognosis");
        try {
            facade.getWeatherForecast(368147, 2020, 12, 12);
            fail();
        } catch (NotFoundException ex) {
            assertEquals("The city doesnt exsist", ex.getMessage());
        }
    }

    /**
     * Test of getWeatherForecasts method, of class WeatherFacade.
     *
     * @throws errorhandling.NotFoundException
     */
    @Test
    public void testGetWeatherForecasts() throws NotFoundException {
        System.out.println("getWeatherForecasts");

        String expResult = "It's cold outside, were a sweater... or die";
        List<WeatherForecastDTO> result = facade.getWeatherForecasts(2151330);

        assertEquals(expResult, result.get(0).getFunnyAdvice());
        assertEquals(5, result.size());

    }

    /**
     * Test of notFoundGetWeatherForecasts method, of class WeatherFacade. Test
     * error message 400, if the countrycode doesn't exsist.
     *
     * @throws errorhandling.NotFoundException
     */
    @Test
    public void testNotFoundGetWeatherForecasts() throws NotFoundException {
        System.out.println("Negative countrycode for 5 day prognose");
        try {
            facade.getWeatherForecasts(2151331);
            fail();
        } catch (NotFoundException ex) {
            assertEquals("The city doesnt exsist", ex.getMessage());
        }
    }

}
