package pl.news.demo.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import jakarta.transaction.Transactional;
import pl.news.demo.model.City;
import pl.news.demo.model.News;
import pl.news.demo.repository.CityRepository;
import pl.news.demo.repository.NewsRepository;
;


@Service
public class NewsService {
    
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private OpenAiService openAiService;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private LocationMatcher locationMatcher;

    public void importNews( MultipartFile file ) {
       try (InputStream inputStream = file.getInputStream();
            Reader reader = new InputStreamReader(inputStream);
            CSVReader csvReader = new CSVReaderBuilder(reader)
                                .withCSVParser(new CSVParserBuilder()
                                .withSeparator(';')
                                .withQuoteChar('"')
                                .build())
                                .build()) {

            List<News> news_list = new ArrayList<>();
            String[] data;

            csvReader.readNext(); // Skip header

            while ((data = csvReader.readNext()) != null) {
                News news = new News();

                System.out.println("Title: " + data[0]);
                System.out.println("Content: " + data[1]);
                System.out.println("Author: " + data[2]);
                System.out.println("Date: " + data[3]);
                System.out.println("Image URL: " + data[4]);
                System.out.println("Source URL: " + data[5]);

                news.setTitle(data[0]);
                news.setContent(data[1]);
                news.setAuthor(data[2]);
                news.setDate(data[3]);
                news.setImageUrl(data[4]);
                news.setSourceUrl(data[5]);
                news_list.add(news);
            }
            newsRepository.saveAll(news_list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Transactional
    public void classifyAndLinkNews() {
        // Fetch unclassified news
        List<News> unclassifiedNews = newsRepository.findAllByClassification(null);

        if (!unclassifiedNews.isEmpty()) {
            News newsToClassify = unclassifiedNews.get(4); // Access the second news item
            String classificationResponse = openAiService.classifyNews(newsToClassify.getTitle(), newsToClassify.getContent());
            
            System.out.println("Classification Response: " + classificationResponse);
            boolean isGlobal = classificationResponse.contains("Global");
            newsToClassify.setClassification(isGlobal ? "Global" : "Local");

            if (!isGlobal){
                System.out.println("News is not global");
               
                String location = classificationResponse.split("Location:")[1].trim();
                System.out.println("Location: "+ location);
                String[] locationParts = location.split(",");
                System.out.println(locationParts[0]);

                if (locationParts.length == 2) {
                    String city = locationParts[0].trim();  
                    String state = locationParts[1].trim(); 

                    System.out.println("Checking city and state: " + city + ", " + state);
                    
                    String matchedCityName = locationMatcher.matchLocation(city, state);
                    System.out.println("Matched City: " + matchedCityName);

                    if (matchedCityName != null) {
                        System.out.println("City matched");
                        Optional<City> city2 = cityRepository.findByCityNameAndStateName(matchedCityName, state);
                        city2.ifPresent(newsToClassify::setCity);
                    }
                } else {
                    System.out.println("Location format is invalid. Expected 'City, State'.");
                }
            }
            newsRepository.save(newsToClassify);

        } else {
            System.out.println("No unclassified news found.");
        }
/* 
        for (News news : unclassifiedNews) {
            String classificationResponse = openAiService.classifyNews(news.getTitle(), news.getContent());
            System.out.println(classificationResponse);
            
            //Parse classification
            boolean isGlobal = classificationResponse.contains("Global");
            news.setClassification(isGlobal ? "Global" : "Local");

            if (!isGlobal) {
                // Extract and match city
                String location = classificationResponse.split("Location:")[1].trim();
                String matchedCityName = locationMatcher.matchLocation(location);

                if (matchedCityName != null) {
                    Optional<City> city = cityRepository.findByCityNameIgnoreCase(matchedCityName);
                    city.ifPresent(news::setCity);
                }
            }

             newsRepository.save(news);
        } */
    }

    public List<News> getAllNews() {
        return newsRepository.findAll(); 
    }

}
