package de.kozdemir.bibliothek.controller;

import de.kozdemir.bibliothek.model.Book;
import de.kozdemir.bibliothek.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public List<Book> index() {
        return bookService.findAll();
    }

    @GetMapping("{id}")
    public Optional<Book> byId(@PathVariable("id") Long id) {
        return bookService.findById(id);
    }

    @GetMapping("search")
    public List<Book> searchByTitle(@RequestBody Book book) {
        System.out.println("T:"+book.getTitle());
        System.out.println("A:"+book.getAuthor());
        System.out.println("G:"+book.getGenera());
        return bookService.searchByTitle(book.getTitle());
    }

    @GetMapping("search/{genera}")
    public Optional<Book> searchByGenera(@PathVariable("genera") String genera) {
        return bookService.searchByGenera(genera);
    }

    @GetMapping("search/{author}")
    public Optional<Book> searchByAuthor(@PathVariable("author") String author) {
        return bookService.searchByAuthor(author);
    }



    @PostMapping("") // Speichern (INSERT)
    public Book insert(@RequestBody Book book) {
        return bookService.insert(book);
    }

    @PutMapping("{id}") // Speichern (UPDATE)
    public Book update(@PathVariable("id") Long id, @RequestBody Book book) {
       return bookService.update(id, book);
    }

    @DeleteMapping("{id}") // Löschen
    public void delete(@PathVariable("id") Long id) {
        bookService.deleteById(id);
    }


}
