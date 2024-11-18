package pl.news.demo.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import pl.news.demo.model.News;
import pl.news.demo.repository.NewsRepository;


@Service
public class NewsService {
    
    @Autowired
    private NewsRepository newsRepository;

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

    public List<News> getAllNews() {
        return newsRepository.findAll(); 
    }

}
