package com.project.kathai.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mytable")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Book_Id")
    private int id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Authors")
    private String author;

    @Column(name = "Page_Count")
    private String page;

    @Column(name = "My_Rating")
    private String rating;

    @Column(name = "Image_URL")
    private String imageUrl;

    // Constructors, getters, and setters for Title and Authors

    public Book() {
        // Default constructor with no arguments
    }

    public int getId() {
        return id;
    }

    public void setBook_Id(Integer id) {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String authors) {
        this.author = authors;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page_count) {
        this.page = page_count;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String my_Rating) {
        this.rating = my_Rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String image_url) {
        this.imageUrl = image_url;
    }

}
