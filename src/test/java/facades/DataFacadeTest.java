/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class DataFacadeTest {

    private static DataFacade facade;

    public DataFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {

        facade = DataFacade.getDataFacade();
    }

    @AfterAll
    public static void tearDownClass() {
    }

    /**
     * Test of getFacade method, of class DataFacade.
     */
    @Test
    public void testGetFacade() {
        System.out.println("getDataFacade");
        DataFacade expResult = facade;
        DataFacade result = DataFacade.getDataFacade();
        assertEquals(expResult, result);
    }

    /**
     * Test of getData method, of class DataFacade.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetData() throws Exception {
        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("getData");

        double expResult = 9.85;
        String weatherJson = facade.getData("44418/2013/4/27/");
        WeatherForecast[] weather = GSON.fromJson(weatherJson, WeatherForecast[].class);

        assertEquals(expResult, weather[0].getWind_speed());

    }

}
