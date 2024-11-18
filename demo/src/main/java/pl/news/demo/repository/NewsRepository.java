package pl.news.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.news.demo.model.News;

public interface NewsRepository extends JpaRepository<News, Long> {
    
}
