package facades;

import DTO.CityDTO;
import DTO.CountryDTO;
import utils.EMF_Creator;
import errorhandling.NotFoundException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class CountryFacadeTest {

    private static EntityManagerFactory emf;
    private static CountryFacade facade;

    public CountryFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);
        facade = CountryFacade.getFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    /**
     * Test of getFacade method, of class CountryFacade.
     */
    @Test
    public void testGetFacade() {
        System.out.println("getCountryFacade");
        CountryFacade expResult = facade;
        CountryFacade result = CountryFacade.getFacade(emf);
        assertEquals(expResult, result);
    }

//    /**
//     * Test of getCountries method, of class CountryFacade.
//     */
//    @Test
//    public void testGetCountries() throws Exception {
//        System.out.println("getCountries");
//        CountryFacade instance = null;
//        List<CountryDTO> expResult = null;
//        List<CountryDTO> result = instance.getCountries();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

//    /**
//     * Test of getCities method, of class CountryFacade.
//     */
//    @Test
//    public void testGetCities() throws Exception {
//        System.out.println("getCities");
//        int countryCode = 0;
//        CountryFacade instance = null;
//        List<CityDTO> expResult = null;
//        List<CityDTO> result = instance.getCities(countryCode);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
 
    /**
     * Test of getCountries method, of class CountryFacade.
     *
     * @throws errorhandling.NotFoundException
     */
//    @Test
//    public void testGetCountries() throws NotFoundException {
//        System.out.println("getCountries");
//        List<CountryDTO> countries = facade.getCountries();
//        assertEquals(countries.size(), 118);
//    }

}
