package com.project.kathai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.kathai.model.Book;
import com.project.kathai.repository.BookRepository;

import groovy.util.logging.Slf4j;

@Service
@Slf4j
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public Map<String, Long> getGenreCounts() {
        List<Object[]> genreOccurrences = bookRepository.countGenreOccurrences();
        Map<String, Long> genreCounts = new HashMap<>();

        for (Object[] genreOcc : genreOccurrences) {
            String bookshelf = (String) genreOcc[0];
            Long count = (Long) genreOcc[1];
            genreCounts.put(bookshelf, count);
        }

        return genreCounts;
    }

    public Map<String, Integer> getReadCounts() {
        Map<String, Integer> readCounts = new HashMap<>();

        int readCount = bookRepository.countByReadEquals("1");
        int notReadCount = bookRepository.countByReadEquals("0");

        readCounts.put("Read", readCount);
        readCounts.put("Not Read", notReadCount);

        return readCounts;
    }

    public int getPagesRead(String pgread) {
        return pgread != null ? Integer.parseInt(pgread) : 0;
    }
    
    public int getPageCount(String page) {
        return page != null ? Integer.parseInt(page) : 0;
    }
    

    public Map<String, Integer> getPageCounts() {
        List<Book> books = bookRepository.findAll();
        int totalRead = 0;
        int totalUnread = 0;

        for (Book book : books) {
            totalRead += getPagesRead(book.getPgread());
            totalUnread += (getPageCount(book.getPage()) - getPagesRead(book.getPgread()));
        }

        Map<String, Integer> pageCounts = new HashMap<>();
        pageCounts.put("read", totalRead);
        pageCounts.put("unread", totalUnread);

        return pageCounts;
    }

    public Map<String, Long> getRatingCounts() {
        List<Object[]> ratingOccurrences = bookRepository.countRatingOccurrences();
        Map<String, Long> ratingCounts = new HashMap<>();
    
        for (Object[] ratingOcc : ratingOccurrences) {
            String rating = (String) ratingOcc[0];
            Long count = (Long) ratingOcc[1];
    
            if (rating == null) {
                rating = "Not Rated";
            }
    
            ratingCounts.put(rating, count);
        }
    
        return ratingCounts;
    }

}
