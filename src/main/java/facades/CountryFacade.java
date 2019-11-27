package facades;

import DTO.CityDTO;
import DTO.CountryDTO;
import DTO.WeatherForecastDTO;
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

            // All states in UK is added as countries in the list
            // This makes it possible to get the individual cities in the states.
            CountryDTO UK = GSON.fromJson(super.getData("23424975"), CountryDTO.class);

            UK.getCities().forEach((state) -> {
                countryDTO.add(new CountryDTO(state));
            });
            return countryDTO;

        } catch (JsonSyntaxException | IOException e) {
            throw new NotFoundException("Requested countries could not be found");
        } finally {
            em.close();
        }
    }

//    public void colorAlgorithm(List<CountryDTO> countries) throws NotFoundException {
//        try {
//            ExecutorService executor = Executors.newFixedThreadPool(countries.size());
//            Queue<Future<String>> queue = new ArrayBlockingQueue(countries.size());
//
//            WeatherFacade WF = WeatherFacade.getFacade();
//            for (CountryDTO country : countries) {
//
//                Future<String> future = executor.submit(() -> {
//                    CityDTO city = getCities(country.getCountryCode()).get(0);
//                    WeatherForecastDTO forecast = WF.getWeatherForecasts(city.getCityCode()).get(0);
//                    country.setColorCode(forecast.getTemp());
//                    return country.getName();
//                });
//                
//                queue.add(future);
//            }
//            while (!queue.isEmpty()) {
//                Future<String> task = queue.poll();
//                if (!task.isDone()) {
//                    queue.add(task);
//                } 
//            }
//            executor.shutdown();
//            executor.awaitTermination(1, TimeUnit.DAYS);
//
//        } catch (InterruptedException e) {
//            throw new NotFoundException("Colorcode could not be generated");
//        }
//    }

    public CityDTO getCity(String cityname) throws NotFoundException {

        try {
            CityDTO[] city = GSON.fromJson(super.getData("search/?query=" + cityname.toLowerCase()), CityDTO[].class);

            if (city.length == 0) {
                throw new NotFoundException("Requested city could not be found");
            }

            return city[0];

        } catch (JsonSyntaxException | IOException e) {
            throw new NotFoundException("Requested city could not be found");

        }
    }

    public List<CityDTO> getCities(int countryCode) throws NotFoundException {
        try {
            if (countryCode == 23424977 || countryCode == 23424975) {
                return getCitiesUSAorUK(countryCode);
            }
            CountryDTO country = GSON.fromJson(super.getData(Integer.toString(countryCode)), CountryDTO.class);
            return country.getCities();

        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new NotFoundException("Requested cities could not be found");

        }
    }

    // This method is implemented to get all cities in USA or UK from every state, this method is only 
    // implemented because the option to select USA or UK is still possible
    private List<CityDTO> getCitiesUSAorUK(int countrycode) throws IOException, InterruptedException, ExecutionException {
        CountryDTO USAorUK = GSON.fromJson(super.getData(Integer.toString(countrycode)), CountryDTO.class);
        List<CityDTO> cities = new ArrayList();
        List<CountryDTO> states = new ArrayList();
        ExecutorService executor = Executors.newFixedThreadPool(USAorUK.getCities().size());

        Queue<Future<CountryDTO>> queue = new ArrayBlockingQueue(USAorUK.getCities().size());

        for (int i = 0; i < USAorUK.getCities().size(); i++) {
            String stateId = Integer.toString(USAorUK.getCities().get(i).getCityCode());
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
