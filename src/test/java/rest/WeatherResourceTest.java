package rest;

import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class WeatherResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {

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

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/weather").then().statusCode(200);
    }

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

    /**
     * Test of getNegativCountry method, of class WeatherResource. Test the
     * error message 400, if the given countrycode is outside the range from 7 -
     * 8 digits.
     */
    @Test
    public void testGetNegativCountry() {

        given()
                .contentType("application/json")
                .get("weather/country/" + 23).then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
                .body("code", equalTo(400), "message", equalTo("Countrycodes must be between 7 and 8 digits"));

    }

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
    /**
     * Test of getCityWeatherByDate method, of class WeatherResource.
     */
    @Test
    public void testGetCityWeatherByDate() {
        given()
                .contentType("application/json")
                .get("weather/city/" + 368148 + "/" + 2013 + "/" + 12 + "/" + 12).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("funnyAdvice", hasItem("Remember your umbrella today!"), "humidity", hasSize(6));
    }

    /**
     * Test of getNegativCityWeatherByDate method, of class WeatherResource.
     * Test the error message 400, if the given citycode is outside the range of
     * 4 to 7 digits.
     */
    @Test
    public void testGetNegativCityWeatherByDate() {
        given()
                .contentType("application/json")
                .get("weather/city/" + 368 + "/" + 2013 + "/" + 12 + "/" + 12).then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
                .body("code", equalTo(400), "message", equalTo("Citycodes must be between 4 and 7 digits"));
    }

    /**
     * Test of getCityWeatherByNegativDate method, of class WeatherResource.
     * Test the error message 400, if the given date doesn't exsist in the
     * calendar.
     */
    @Test
    public void testGetCityWeatherByNegativDate() {
        given()
                .contentType("application/json")
                .get("weather/city/" + 368148 + "/" + 2010 + "/" + 1 + "/" + 12).then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
                .body("code", equalTo(400), "message", equalTo("I dont think we use the same calendar"));
    }

    /**
     * Test of getCityWeatherByNegativDate method, of class WeatherResource.
     * Test the error message 400, if the given year is to far out in the
     * future.
     *
     */
    @Test
    public void testGetCityWeatherByNotFoundDate() {
        given()
                .contentType("application/json")
                .get("weather/city/" + 368148 + "/" + 2021 + "/" + 12 + "/" + 12).then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
                .body("code", equalTo(400), "message", equalTo("Obviously we cant predict the weather THAT far ahead"));
    }
    
    

}
