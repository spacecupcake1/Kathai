package com.project.kathai;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Book> getPaginatedBooks(int pageNo, int pageSize) {
        return bookRepository.findAll(PageRequest.of(pageNo, pageSize)).getContent();
    }
    
}

