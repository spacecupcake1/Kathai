package com.project.kathai;

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

}
