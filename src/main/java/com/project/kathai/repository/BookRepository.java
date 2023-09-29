package com.project.kathai.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.kathai.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b.shelf AS bookshelf, COUNT(b.shelf) AS count FROM Book b GROUP BY b.shelf")
    List<Object[]> countGenreOccurrences();

    @Query("SELECT b.rating AS rating, COUNT(b.rating) AS count FROM Book b GROUP BY b.rating")
    List<Object[]> countRatingOccurrences();

    Page<Book> findAllByOrderByTitleAsc(Pageable pageable);

    int countByReadEquals(String read);



}
