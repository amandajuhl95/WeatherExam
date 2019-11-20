package facades;

import DTO.CountryDTO;
import utils.EMF_Creator;
import errorhandling.NotFoundException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);
        facade = CountryFacade.getFacade(emf);
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
        assertEquals(countries.size(), 118);
    }

}
