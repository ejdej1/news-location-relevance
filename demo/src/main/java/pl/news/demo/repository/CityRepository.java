package pl.news.demo.repository;

import pl.news.demo.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
    
}