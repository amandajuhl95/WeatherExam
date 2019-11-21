package rest;

import entities.Country;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class WeatherResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Country c1, c2, c3, c4;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {

        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        
        EntityManager em = emf.createEntityManager();
        
        c1 = new Country("Denmark", 23424796);
        c2 = new Country("Vietnam", 23424984);
        c3 = new Country("Australia", 23424748);
        c4 = new Country("Russia", 23424936);
                
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Country.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(c4);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/weather").then().statusCode(200);
    }
//
    /**
     * Test of getCountries method, of class WeatherResource.
     */
    @Test
    public void testGetCountries() throws Exception {
        given()
                .contentType("application/json")
                .get("weather/countries").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("countryCode", hasItem(23424984), "name", hasItem("Vietnam"));
    }
//

    /**
     * Test of getCountry method, of class WeatherResource.
     */
    @Test
    public void testGetCountry() throws Exception {

        given()
                .contentType("application/json")
                .get("weather/country/" + 23424781).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("cityCode", hasSize(10), "name", hasSize(10));

    }
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
//         given()
//                .contentType("application/json")
//                .get("weather/city/" + 23424781).then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("cityCode", hasSize(10), "name", hasSize(10));
//    }
    
}
