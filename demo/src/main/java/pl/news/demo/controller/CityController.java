package pl.news.demo.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pl.news.demo.model.City;
import pl.news.demo.service.CityService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/all")
    public List<City> getCities() {
        return cityService.getAllCities(); 
    }

    @PostMapping("/import")
    public String importCities(@RequestParam("file") MultipartFile file) {
        cityService.importCities(file);
        return "Cities imported successfully!";
    }

    @GetMapping("exist")
    public ResponseEntity<Boolean> getCityByCityNameAndStateName(
        @RequestParam String cityName,
        @RequestParam String stateName
    ) {
        
        Optional<City> city = cityService.getCityByCityNameAndStateName(cityName, stateName);
        boolean exists = city.isPresent();
        
        return ResponseEntity.ok(exists);
    }

}
