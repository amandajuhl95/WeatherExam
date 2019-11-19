package facades;

import DTO.CountryDTO;
import entities.Country;
import errorhandling.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;


public class CountryFacade extends DataFacade{

    private static CountryFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CountryFacade() {
    }
    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CountryFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CountryFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<CountryDTO> getCountries() throws NotFoundException {
        EntityManager em = getEntityManager();
        List<CountryDTO> countryDTO = new ArrayList();

        try {
            TypedQuery<Country> query = getEntityManager().createQuery("SELECT c FROM Country c", Country.class);
            List<Country> countries = query.getResultList();
            
            for (Country country : countries) {
                
                countryDTO.add(new CountryDTO(country));
            }
            
            return countryDTO;

        } catch (Exception e) {
            throw new NotFoundException("Requested countries could not be found");
        } finally {
            em.close();
        }
    }
    
    

}
