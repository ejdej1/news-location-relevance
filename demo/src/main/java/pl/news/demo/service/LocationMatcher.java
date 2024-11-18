package pl.news.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.news.demo.model.City;
import pl.news.demo.repository.CityRepository;

@Service
public class LocationMatcher {

    @Autowired
    private CityRepository cityRepository;

     public String matchLocation(String location, String state) {
        if (location == null || location.isBlank()) {
            return null;
        }

        Optional<City> city = cityRepository.findByCityNameAndStateName(location.trim(), state.trim());
        return city.map(City::getCityName).orElse(null);
    }
}
