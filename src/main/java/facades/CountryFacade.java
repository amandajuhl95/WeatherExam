package facades;

import DTO.CityDTO;
import DTO.CountryDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Country;
import errorhandling.NotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;


public class CountryFacade extends DataFacade{

    private static CountryFacade instance;
    private static EntityManagerFactory emf;
    Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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
    
    public List<CityDTO> getCities(int countryCode) throws IOException
    {
        CountryDTO country = GSON.fromJson(super.getData(Integer.toString(countryCode)), CountryDTO.class);
        return country.getCities();
    } 

}


