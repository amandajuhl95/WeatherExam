package rest;

import DTO.CountryDTO;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
@Disabled
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

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/weather").then().statusCode(200);
    }

    /**
     * Test of getCountries method, of class WeatherResource.
     *
     */
    @Test
    public void testGetCountries() {

        CountryDTO[] countries = given()
                .contentType("application/json")
                .get("weather/countries").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract().as(CountryDTO[].class);
        
        assertTrue(countries.length >= 58);

    }

    /**
     * Test of getCountry method, of class WeatherResource.
     *
     */
    @Test
    public void testGetCountry() {

        given()
                .contentType("application/json")
                .get("weather/country/" + 23424781).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("cityCode", hasSize(10), "name", hasSize(10));

    }

    /**
     * Test of NegativeGetCountry method, of class WeatherResource.Test the
     * error message 400, if the given countrycode is outside the range from 7 -
     * 8 digits.
     *
     */
    @Test
    public void testNegativeGetCountry() {

        given()
                .contentType("application/json")
                .get("weather/country/" + 23).then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
                .body("code", equalTo(400), "message", equalTo("Countrycodes must be between 7 and 8 digits"));

    }

    /**
     * Test of getCityWeatherByName method, of class WeatherResource.
     *
     */
    @Test
    public void testGetCityWeatherByName() {
        given()
                .contentType("application/json")
                .get("weather/city/london").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("windDirection", hasSize(5), "funnyAdvice", hasSize(5));
    }

    /**
     * Test of getCityWeatherByCityCode method, of class WeatherResource.
     *
     */
    @Test
    public void testGetCityWeatherCityCode() {
        given()
                .contentType("application/json")
                .get("weather/city/" + 2151330).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("predictability", hasSize(5), "funnyAdvice", hasSize(5));
    }

    /**
     * Test of getNegativeCityWeather method, of class WeatherResource.Test the
     * error message 400, if the given countrycode is outside the range from 7 -
     * 8 digits.
     *
     */
    @Test
    public void testNegativeGetCityWeather() {

        given()
                .contentType("application/json")
                .get("weather/city/" + 215354244).then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
                .body("code", equalTo(400), "message", equalTo("Citycodes must be between 4 and 7 digits"));

    }

    /**
     * Test of getCityWeatherByDate method, of class WeatherResource.
     *
     */
    @Test
    public void testGetCityWeatherByDate() {
        given()
                .contentType("application/json")
                .get("weather/city/" + 368148 + "/" + 2013 + "/" + 12 + "/" + 12).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("funnyAdvice", hasItems("Not a good day for an umbrella, neither for a sweather nor shorts. Our advice... Don't go outside, don't answer your phone, take a you day! You deserve it you beautiful bastard!"), "humidity", hasSize(5));
    }

    /**
     * Test of getNegativeCityWeatherByDate method, of class
     * WeatherResource.Test the error message 400, if the given citycode is
     * outside the range of 4 to 7 digits.
     *
     */
    @Test
    public void testGetNegativeCityWeatherByDate() {
        given()
                .contentType("application/json")
                .get("weather/city/" + 368 + "/" + 2013 + "/" + 12 + "/" + 12).then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
                .body("code", equalTo(400), "message", equalTo("Citycodes must be between 4 and 7 digits"));
    }

    /**
     * Test of getCityWeatherByNegativeDate method, of class
     * WeatherResource.Test the error message 400, if the given date doesn't
     * exsist in the calendar.
     *
     */
    @Test
    public void testGetCityWeatherByNegativeDate() {
        given()
                .contentType("application/json")
                .get("weather/city/" + 368148 + "/" + 2010 + "/" + 1 + "/" + 12).then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
                .body("code", equalTo(400), "message", equalTo("I dont think we use the same calendar"));
    }

    /**
     * Test of getCityWeatherByNotFoundDate method, of class
     * WeatherResource.Test the error message 400, if the given year > current
     * year + 1.
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

    /**
     * Test of getCityNegativeWeatherByDate method, of class
     * WeatherResource.Test the error message 400, if there is no
     * weatherforecast for the given date.
     *
     */
    @Test
    public void testGetCityNegativeWeatherByDate() {
        given()
                .contentType("application/json")
                .get("weather/city/" + 2151330 + "/" + 2020 + "/" + 12 + "/" + 12).then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
                .body("code", equalTo(400), "message", equalTo("No weatherforecast found for the date"));
    }

}
