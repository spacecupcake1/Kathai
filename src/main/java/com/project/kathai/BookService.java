package com.project.kathai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
