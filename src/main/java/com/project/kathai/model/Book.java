package com.project.kathai.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "2023sep")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Book_Id")
    private int id;

    @Column(name= "ISBN")
    private String isbn;

    @Column(name = "Bookshelf")
    private String shelf;

    @Column(name = "Tags")
    private String tag;

    @Column(name = "Started_Reading_On")
    private Date start;

    @Column(name = "ended_reading_on")
    private String end;

    @Column(name = "pages_read")
    private String pgread;

    @Column(name = "have_finished")
    private String read;

    @Column(name = "my_rating")
    private String rating;

    @Column(name = "Title")
    private String title;

    @Column(name = "Authors")
    private String author;

    @Column(name = "Page_Count")
    private String page;

    @Column(name = "Image_URL")
    private String imageUrl;

    /* 
    @Column(name = "")
    private String ;
    */

    // Constructors, getters, and setters for Title and Authors

    public Book() {
        // Default constructor with no arguments
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getPgread() {
        return pgread;
    }

    public void setPgread(String pgread) {
        this.pgread = pgread;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String image_url) {
        this.imageUrl = image_url;
    }

}
