package pl.news.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pl.news.demo.model.News;
import pl.news.demo.service.NewsService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/all")
    public List<News> getNews() {
        return newsService.getAllNews(); 
    }

    @PostMapping("/import")
    public String importNews(@RequestParam("file") MultipartFile file) {
        newsService.importNews(file);
        return "News imported successfully!";
    }

    @PostMapping("/classify")
    public String classifyNews() {
        newsService.classifyAndLinkNews();
        return "News classified";
    }

    @GetMapping("/city")
    public ResponseEntity<List<News>> getNewsByCity(
        @RequestParam String cityName,
        @RequestParam String stateName
    ) {
        List<News> newsList = newsService.getNewsForCity(cityName, stateName);
        return ResponseEntity.ok(newsList);
    }

    @GetMapping("/global")
    public ResponseEntity<List<News>> getGlobalNews() {
        List<News> newsList = newsService.getGlobalNews();
        return ResponseEntity.ok(newsList);
    } 
}
