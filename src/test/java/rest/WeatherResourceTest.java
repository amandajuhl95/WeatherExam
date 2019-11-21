package rest;

import DTO.CityDTO;
import DTO.CountryDTO;
import entities.Country;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class WeatherResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Country r1,r2;
    
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);
        
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }
    
    @AfterAll
    public static void closeTestServer(){
        //System.in.read();
         //Don't forget this, if you called its counterpart in @BeforeAll
         EMF_Creator.endREST_TestWithDB();
         httpServer.shutdownNow();
    }
    
    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
//        EntityManager em = emf.createEntityManager();
//        r1 = new Country("Some txt","More text");
//        r2 = new Country("aaa","bbb");
//        try {
//            em.getTransaction().begin();
//            em.createNamedQuery("Country.deleteAllRows").executeUpdate();
//            em.persist(r1);
//            em.persist(r2); 
//            em.getTransaction().commit();
//        } finally { 
//            em.close();
//        }
    }

    @AfterEach
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/weather").then().statusCode(200);
    }
   
    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
        .contentType("application/json")
        .get("/weather/").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("msg", equalTo("Hello World"));   
    }

//    /**
//     * Test of demo method, of class WeatherResource.
//     */
//    @Test
//    public void testDemo() {
//        System.out.println("demo");
//        WeatherResource instance = new WeatherResource();
//        String expResult = "";
//        String result = instance.demo();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getCountries method, of class WeatherResource.
//     */
//    @Test
//    public void testGetCountries() throws Exception {
//        System.out.println("getCountries");
//        WeatherResource instance = new WeatherResource();
//        List<CountryDTO> expResult = null;
//        List<CountryDTO> result = instance.getCountries();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getCountry method, of class WeatherResource.
//     */
//    @Test
//    public void testGetCountry() throws Exception {
//        System.out.println("getCountry");
//        int countrycode = 0;
//        WeatherResource instance = new WeatherResource();
//        List<CityDTO> expResult = null;
//        List<CityDTO> result = instance.getCountry(countrycode);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getCityWeather method, of class WeatherResource.
//     */
//    @Test
//    public void testGetCityWeather() {
//        System.out.println("getCityWeather");
//        int citycode = 0;
//        WeatherResource instance = new WeatherResource();
//        String expResult = "";
//        String result = instance.getCityWeather(citycode);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getCityWeatherByDate method, of class WeatherResource.
//     */
//    @Test
//    public void testGetCityWeatherByDate() {
//        System.out.println("getCityWeatherByDate");
//        int citycode = 0;
//        int year = 0;
//        int month = 0;
//        int day = 0;
//        WeatherResource instance = new WeatherResource();
//        String expResult = "";
//        String result = instance.getCityWeatherByDate(citycode, year, month, day);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
