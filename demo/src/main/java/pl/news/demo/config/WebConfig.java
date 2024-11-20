package pl.news.demo.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        
        registry.addMapping("/**")  
                .allowedOrigins("http://news-frontend-ejdej.s3.eu-north-1.amazonaws.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE")  
                .allowedHeaders("*");

    }

}
