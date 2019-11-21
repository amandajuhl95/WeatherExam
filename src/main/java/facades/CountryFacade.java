package facades;

import DTO.CityDTO;
import DTO.CountryDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import entities.Country;
import errorhandling.NotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class CountryFacade extends DataFacade {

    private static ExecutorService executor = Executors.newCachedThreadPool();
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

            countries.forEach((country) -> {
                countryDTO.add(new CountryDTO(country));
            });

            // All states in USA is added as countries in the list
            // This makes it possible to get the individual cities in the states.
            CountryDTO USA = GSON.fromJson(super.getData("23424977"), CountryDTO.class);

            USA.getCities().forEach((state) -> {
                countryDTO.add(new CountryDTO(state));
            });

            return countryDTO;

        } catch (JsonSyntaxException | IOException e) {
            throw new NotFoundException("Requested countries could not be found");
        } finally {
            em.close();
        }
    }

    public List<CityDTO> getCities(int countryCode) throws NotFoundException {
        try {
            if (countryCode == 23424977) {
                return getCitiesUSA();
            }
            CountryDTO country = GSON.fromJson(super.getData(Integer.toString(countryCode)), CountryDTO.class);
            return country.getCities();

        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new NotFoundException(e.getMessage());

        }
    }

    // This method is implemented to get all cities in USA from every state, this method is only 
    // implemented because the option to select USA is still possible
    private List<CityDTO> getCitiesUSA() throws IOException, InterruptedException, ExecutionException {
        CountryDTO USA = GSON.fromJson(super.getData("23424977"), CountryDTO.class);
        List<CityDTO> cities = new ArrayList();
        List<CountryDTO> states = new ArrayList();

        Queue<Future<CountryDTO>> queue = new ArrayBlockingQueue(USA.getCities().size());

        for (int i = 0; i < USA.getCities().size(); i++) {
            String stateId = Integer.toString(USA.getCities().get(i).getCityCode());
            Future<CountryDTO> future = executor.submit(() -> {

                CountryDTO state = GSON.fromJson(super.getData(stateId), CountryDTO.class);
                return state;
            });

            queue.add(future);
        }

        while (!queue.isEmpty()) {
            Future<CountryDTO> state = queue.poll();
            if (state.isDone()) {
                states.add(state.get());
            } else {
                queue.add(state);
            }
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);

        states.forEach((state) -> {
            state.getCities().forEach((city) -> {
                cities.add(city);
            });
        });

        return cities;
    }

}
