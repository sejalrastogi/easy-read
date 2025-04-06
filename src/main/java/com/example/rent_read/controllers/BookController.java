package com.example.rent_read.controllers;

import com.example.rent_read.entities.Book;
import com.example.rent_read.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Book> books = bookService.getAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable String bookId){
        return bookService.getBookById(bookId);
    }

    @PostMapping
    public ResponseEntity<?> saveEntry(@RequestBody Book book){
        return bookService.saveEntry(book);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateEntry(@PathVariable String bookId, @RequestBody Book updatedBook) {
        return bookService.updateEntry(bookId, updatedBook);
    }
    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteEntry(@PathVariable String bookId){
        return bookService.delete(bookId);
    }

}
