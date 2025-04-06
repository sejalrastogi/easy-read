package com.example.rent_read.services;

import com.example.rent_read.entities.Book;
import com.example.rent_read.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public ResponseEntity<?> getBookById(String bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if(book == null){
            return new ResponseEntity<>("Book Not Found!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    public ResponseEntity<?> saveEntry(Book book) {
        bookRepository.save(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    public ResponseEntity<?> delete(String bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);

        if(book == null){
            return new ResponseEntity<>("Book Not Found!", HttpStatus.NOT_FOUND);
        }

        bookRepository.deleteById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    public ResponseEntity<?> updateEntry(String bookId, Book newBook) {
        Book existingBook = bookRepository.findById(bookId).orElse(null);
        if(existingBook == null){
            return new ResponseEntity<>("Book Not Found!", HttpStatus.NOT_FOUND);
        }

        existingBook.setId(newBook.getId());
        existingBook.setTitle(newBook.getTitle());
        existingBook.setAuthor(newBook.getAuthor());
        existingBook.setAvailable(newBook.isAvailable());

        bookRepository.save(existingBook);

        return new ResponseEntity<>(existingBook, HttpStatus.OK);
    }
}
