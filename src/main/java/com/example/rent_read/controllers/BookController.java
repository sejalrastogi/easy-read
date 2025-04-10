package com.example.rent_read.controllers;

import com.example.rent_read.entities.Book;
import com.example.rent_read.services.BookService;
import com.example.rent_read.services.RentalService;
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

    @Autowired
    private RentalService rentalService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Book> books = bookService.getAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAllAvailable(){
        return bookService.getAllAvailable();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Long bookId){
        return bookService.getBookById(bookId);
    }

    @PostMapping
    public ResponseEntity<?> saveEntry(@RequestBody Book book){
        return bookService.saveEntry(book);
    }

//    @PutMapping("/{bookId}")
//    public ResponseEntity<?> updateEntry(@PathVariable Long bookId, @RequestBody Book updatedBook) {
//        return bookService.updateEntry(bookId, updatedBook);
//    }

    @PostMapping("/{bookId}/rent")
    public ResponseEntity<?> rentABook(@PathVariable Long bookId, @RequestParam String userEmail){
        return rentalService.rentBook(bookId, userEmail);
    }

    @PostMapping("/{bookId}/return")
    public ResponseEntity<?> returnABook(@PathVariable Long bookId, @RequestParam String userEmail){
        return rentalService.returnBook(bookId, userEmail);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllBooks(){
        return bookService.deleteAll();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long bookId){
        return bookService.delete(bookId);
    }

}
