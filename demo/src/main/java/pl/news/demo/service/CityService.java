package pl.news.demo.service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pl.news.demo.model.City;
import pl.news.demo.repository.CityRepository;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;
    
    public void importCities( MultipartFile file ) {
        try ( BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));) {
            String line;
            List<City> cities = new ArrayList<>();

            br.readLine();

            while (( line = br.readLine()) != null ) {
                String[] data = line.split(";");
                City city = new City();
                city.setCityName(data[0]);
                city.setStateName(data[1]);
                city.setLatitude(Double.parseDouble(data[2].replace(',', '.')));
                city.setLongitude(Double.parseDouble(data[3].replace(',', '.')));
                cities.add(city);
            }
            cityRepository.saveAll(cities);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<City> getAllCities() {
        return cityRepository.findAll(); 
    }

    public Optional<City> getCityByCityNameAndStateName (String cityName, String stateName) {
        return cityRepository.findByCityNameAndStateName(cityName, stateName);
    }

}