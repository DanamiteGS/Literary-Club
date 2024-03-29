package com.practice.library.controllers;

import com.practice.library.entities.Book;
import com.practice.library.services.AuthorService;
import com.practice.library.services.BookService;
import com.practice.library.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PublisherService publisherService;

    @GetMapping("/reader/books/all")
    public ModelAndView listBooksReader() {
        ModelAndView mav = new ModelAndView("books-list-reader");
        List<Book> books = bookService.findBooks();
        mav.addObject("books", books);
        return mav;
    }

    @GetMapping("/reader/books/{title}/{isbn}")
    public ModelAndView seeBookReader(@PathVariable String title, @PathVariable Long isbn) {
        ModelAndView mav = new ModelAndView("show-book");
        mav.addObject("book", bookService.showBook(isbn));
        return mav;
    }


    @GetMapping("/keeper/books/all")
    public ModelAndView listBooksKeeper() {
        ModelAndView mav = new ModelAndView("books-list-keeper");
        List<Book> books = bookService.findBooks();
        mav.addObject("books", books);
        return mav;
    }

    @GetMapping("/keeper/books/edit/{isbn}")
    public ModelAndView editBook(@PathVariable Long isbn) {
        ModelAndView mav = new ModelAndView("edit-book");
        Book book = bookService.showBook(isbn);
        mav.addObject("book", book);
        mav.addObject("authors", authorService.findAuthors());
        mav.addObject("publishers", publisherService.findPublishers());
        return mav;
    }

    @PostMapping("/keeper/books/done")
    public RedirectView editBook(@RequestParam Long isbn, @RequestParam String title, @RequestParam Integer pages,
                                 @RequestParam LocalDate publicationDate, @RequestParam Integer numberOfCopies,
                                 @RequestParam("author") Integer idAuthor,
                                 @RequestParam("publisher") Integer idPublisher,
                                 @RequestParam Integer idDescription, @RequestParam String bookDescription) {
        bookService.editBook(isbn, title, pages, publicationDate, numberOfCopies, idAuthor, idPublisher,
                             idDescription, bookDescription);
        return new RedirectView("/keeper/books/all");
    }

    @PostMapping("/keeper/books/delete/{isbn}")
    public RedirectView deleteBook(@PathVariable Long isbn) {
        bookService.deactivateBook(isbn);
        return new RedirectView("/keeper/books/all");
    }

    @GetMapping("/keeper/books/add")
    public ModelAndView createBook(){
        ModelAndView mav = new ModelAndView("add-book");
        Book book = new Book();
        mav.addObject("book", book);
        mav.addObject("authors", authorService.findAuthors());
        mav.addObject("publishers", publisherService.findPublishers());
        return mav;
    }

    @PostMapping("/keeper/books/save")
    public RedirectView addBook(@RequestParam Long isbn, @RequestParam String title, @RequestParam Integer pages,
                                @RequestParam LocalDate publicationDate, @RequestParam Integer numberOfCopies,
                                @RequestParam("author") Integer idAuthor,
                                @RequestParam("publisher") Integer idPublisher, @RequestParam String bookDescription) {

        bookService.createBook(isbn, title, pages, publicationDate, numberOfCopies, idAuthor, idPublisher, bookDescription);
        return new RedirectView("/keeper/books/all");
    }
}