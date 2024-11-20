package pl.news.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.news.demo.model.City;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByCityNameIgnoreCase(String cityName);
    Optional<City> findByCityNameIgnoreCaseAndStateNameIgnoreCase(String cityName, String stateName);

}