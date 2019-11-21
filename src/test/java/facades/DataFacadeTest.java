/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class DataFacadeTest {
    
    public DataFacadeTest() {
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
     * Test of getData method, of class DataFacade.
     */
    @Test
    public void testGetData() throws Exception {
        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("getData");
        String endpoint = "44418/2013/4/27/";
        DataFacade instance = new DataFacade();
        double expResult = 9.85;
        String weatherJson = instance.getData(endpoint);
        WeatherForecast[] weather = GSON.fromJson(weatherJson, WeatherForecast[].class);
        
       
        assertEquals(expResult, weather[0].getWind_speed());
     
    }
    
}
