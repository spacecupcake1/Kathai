package com.project.kathai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.kathai.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
