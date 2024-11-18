package pl.news.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pl.news.demo.model.News;
import pl.news.demo.service.NewsService;


@RestController
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

}
