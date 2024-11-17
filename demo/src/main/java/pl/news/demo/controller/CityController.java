package pl.news.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pl.news.demo.model.City;
import pl.news.demo.service.CityService;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/")
    public List<City> getCities() {
        return cityService.getAllCities();  // This will return all cities in the database
    }

    @PostMapping("/import")
    public String importCities(@RequestParam("file") MultipartFile file) {
        cityService.importCities(file);
        return "Cities imported successfully!";
    }
}
