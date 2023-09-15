package com.project.kathai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.project.kathai.model.Book;
import com.project.kathai.repository.BookRepository;

import groovy.util.logging.Slf4j;

@Service
@Slf4j
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public Page<Book> getAllBooksPaged(int page, int size) {
        // Pageable pageable = PageRequest.of(page, size);
        PageRequest pr = PageRequest.of(page,size);
        return bookRepository.findAll(pr);
    }

    public Map<String, Long> getBookshelfCounts() {
    List<Object[]> bookshelfOccurrences = bookRepository.countBookshelfOccurrences();
    Map<String, Long> bookshelfCounts = new HashMap<>();

    for (Object[] occurrence : bookshelfOccurrences) {
        String bookshelf = (String) occurrence[0];
        Long count = (Long) occurrence[1];
        bookshelfCounts.put(bookshelf, count);
    }

    return bookshelfCounts;
}

    

}
