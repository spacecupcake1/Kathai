package com.project.kathai;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.kathai.model.Book;
import com.project.kathai.repository.BookRepository;

@Controller
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    private final Logger LOG = LoggerFactory.getLogger(BookController.class);

    // Get all books
    @GetMapping("/")
    public String getAllBooks(Model model, Book book) {
        LOG.info("Getting All Books");

        List<Book> books = bookRepository.findAll();
        model.addAttribute("bookList", books);
        return "bookList";

    }

    @GetMapping("/{id}")
    public String getBookById(Model model, @PathVariable int id) {
        LOG.info("Getting Book with ID: {}", id);

        // Find the book by ID
        Optional<Book> optionalBook = bookRepository.findById(id);

        // Check if the book exists
        if (optionalBook.isPresent()) {
            // Get the book object
            Book book = optionalBook.get();

            // Add the book to the model
            model.addAttribute("bookDesc", book);

            return "bookDesc"; // Return the view for displaying book details
        } else {
            // Handle the case where the book with the given ID is not found
            return "bookNotFound"; // Create a "bookNotFound.html" template for this case
        }
    }
}
