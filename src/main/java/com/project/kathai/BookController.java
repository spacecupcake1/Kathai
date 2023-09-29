package com.project.kathai;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.kathai.model.Book;
import com.project.kathai.model.User;
import com.project.kathai.repository.BookRepository;
import com.project.kathai.repository.UserRepository;

@Controller
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    BookService bookService;

    private final Logger LOG = LoggerFactory.getLogger(BookController.class);

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute @Validated User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
			LOG.debug("errors on /newPerson: {}", result.getAllErrors());
			return "login";
		}
        LOG.debug("saving {}", user); // to check before saving a person
		user = this.userRepo.save(user);
        LOG.debug("saving {}", user); // tocheck after saving a person
        return "login";
    }

    @GetMapping("/signin")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/signin")
    public String processLogin(@RequestParam String userName, @RequestParam String password, Model model) {
        User user = userRepo.findByuserName(userName);

        if (user != null && user.getPassword().equals(password)) {
            return "redirect:/api/books"; // Successful login, redirect to dashboard
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Failed login, show error message
        }
    }

    @GetMapping("/all")
    public String getAllBooksMapping(Model model, Book book) {
        LOG.info("Getting All Books");

        List<Book> books = bookRepository.findAll();
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

    @GetMapping("/genre")
    public String getGenreAnalysis(Model model) {
        Map<String, Long> genreCounts = bookService.getGenreCounts();

        // Convert bookshelfCounts to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String genreCountsJson;
        try {
            genreCountsJson = objectMapper.writeValueAsString(genreCounts);
        } catch (Exception e) {
            LOG.error("Error converting bookshelfCounts to JSON: {}", e.getMessage());
            return "errorPage"; // Handle error appropriately
        }

        model.addAttribute("genreCountsJson", genreCountsJson);
        LOG.info("Getting the Genre Count: {}", genreCountsJson);
        return "GenreAnalysis";
    }

    @GetMapping("/read")
    public String getReadAnalysis(Model model) {
        Map<String, Integer> readCounts = bookService.getReadCounts();
        Map<String, Integer> pageCounts = bookService.getPageCounts();

        model.addAttribute("readCounts", readCounts);
        LOG.info("Getting the Read Count: {}", readCounts);

        model.addAttribute("pageCounts", pageCounts);
        LOG.info("Getting the page Count: {}", pageCounts);

        return "ReadAnalysis";
    }

    @GetMapping("/rating")
    public String getRatingAnalysis(Model model) {
        Map<String, Long> ratingCounts = bookService.getRatingCounts();

        // Convert bookshelfCounts to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String ratingCountsJson;
        try {
            ratingCountsJson = objectMapper.writeValueAsString(ratingCounts);
        } catch (Exception e) {
            LOG.error("Error converting ratingCounts to JSON: {}", e.getMessage());
            return "errorPage"; // Handle error appropriately
        }

        model.addAttribute("ratingCountsJson", ratingCountsJson);
        LOG.info("Getting the Rating Count: {}", ratingCountsJson);
        return "RatingAnalysis";
    }

    @GetMapping("")
    public String getAllBooksMapping(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        LOG.info("Getting All Books");

        Page<Book> booksPage = bookRepository.findAllByOrderByTitleAsc(PageRequest.of(page, size));
        List<Book> books = booksPage.getContent();

        int previousPage = page - 1;
        int nextPage = page + 1;
        int lastPage = booksPage.getTotalPages() - 1;
        int before = page - 4;
        int after = page + 4;

        model.addAttribute("previousPage", previousPage);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("before", before);
        model.addAttribute("after", after);

        model.addAttribute("bookList", books);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", booksPage.getTotalPages());

        return "bookList";
    }

}
