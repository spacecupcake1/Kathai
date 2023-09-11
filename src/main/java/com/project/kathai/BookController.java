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
import org.springframework.web.bind.annotation.RequestParam;

import com.project.kathai.model.Book;
import com.project.kathai.repository.BookRepository;

@Controller
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    BookService bookService;

    private final Logger LOG = LoggerFactory.getLogger(BookController.class);

    @GetMapping("")
    public String getAllBooks(Model model, Book book) {
        LOG.info("Getting All Books");

        List<Book> books = bookRepository.findAll();
        model.addAttribute("bookList", books);
        return "bookList";

    }
    
    @GetMapping("/")
    public String getAllBooksMapping(Model model, @RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "50") int pageSize) {
        LOG.info("Getting All Books");

        List<Book> books = this.bookService.getPaginatedBooks(pageNo, pageSize);
        model.addAttribute("bookList", books);
        return "bookList";
    }

    @GetMapping("/{id}")
    public String getBookByIdMapping(Model model, @PathVariable int id) {
        LOG.info("Getting Book with ID: {}", id);

        // Find the book by ID
        Optional<Book> optionalBook = bookRepository.findById(id);

        // Check if the book exists
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            model.addAttribute("bookDesc", book);

            // You can also add the placeholder URL here in case the image is not found.
            model.addAttribute("placeholderImageUrl", "placeholder-image-url.jpg");

            return "bookDesc";
        } else {
            return "bookNotFound"; // Create a "bookNotFound.html" template for this case
        }
    }

}
