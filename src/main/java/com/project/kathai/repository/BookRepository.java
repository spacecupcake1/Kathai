package com.project.kathai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.kathai.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b.shelf AS bookshelf, COUNT(b.shelf) AS count FROM Book b GROUP BY b.shelf")
    List<Object[]> countGenreOccurrences();

    int countByReadEquals(String read);

}
