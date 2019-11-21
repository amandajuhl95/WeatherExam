package facades;

import DTO.CityDTO;
import DTO.CountryDTO;
import entities.Country;
import utils.EMF_Creator;
import errorhandling.NotFoundException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
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
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = CountryFacade.getFacade(emf);
        EntityManager em = emf.createEntityManager();
        
        Country c1 = new Country("Spain", 23424950);
        Country c2 = new Country("Denmark", 23424796);
        Country c3 = new Country("North Korea", 23424865);
        
         try {
            em.getTransaction().begin();
            em.createNamedQuery("Country.deleteAllRows").executeUpdate();

            em.persist(c1);
            em.persist(c2);
            em.persist(c3);

            em.getTransaction().commit();

        } finally {
            em.close();
        }

    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
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

    /**
     * Test of getCountries method, of class CountryFacade.
     * @throws errorhandling.NotFoundException
     */
    @Test
    public void testGetCountries() throws NotFoundException {
        System.out.println("getCountries");
        List<CountryDTO> countries = facade.getCountries();
        assertEquals(58, countries.size());
    }

    /**
     * Test of getCities method, of class CountryFacade.
     * @throws errorhandling.NotFoundException
     */
    @Test
    public void testGetCities() throws NotFoundException {
        System.out.println("getCities");
        List<CityDTO> cities = facade.getCities(23424848);
        assertEquals(9, cities.size());
    }
    
    /**
     * Test of getCities method, of class CountryFacade.
     * @throws errorhandling.NotFoundException
     */
    @Test
    public void testGetCitiesUK() throws NotFoundException {
        System.out.println("getCities in UK");
        List<CityDTO> cities = facade.getCities(23424975);
        assertEquals(52, cities.size());
    }
    
    /**
     * Test of getCities method, of class CountryFacade.
     * @throws errorhandling.NotFoundException
     */
    @Test
    public void testGetCitiesUSA() throws NotFoundException {
        System.out.println("getCities in USA");
        List<CityDTO> cities = facade.getCities(23424977);
        assertEquals(78, cities.size());
    }
    
    /**
     * Test of getCities method, of class CountryFacade.
     * @throws errorhandling.NotFoundException
     */
    @Test
    public void testNegativeGetCities() throws NotFoundException {
        System.out.println("Negative getCities");
        try {
            facade.getCities(34543534);
            fail();
        } catch (NotFoundException ex) {

            assertEquals("Requested cities could not be found", ex.getMessage());

        }
    }
}
