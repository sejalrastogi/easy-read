package com.example.rent_read.services;

import com.example.rent_read.entities.Book;
import com.example.rent_read.entities.Rental;
import com.example.rent_read.repository.BookRepository;
import com.example.rent_read.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentalRepository rentalRepository;

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public ResponseEntity<?> getBookById(Long bookId) {
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

    public ResponseEntity<?> delete(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);

        if(book == null){
            return new ResponseEntity<>("Book Not Found!", HttpStatus.NOT_FOUND);
        }

        List<Rental> rentals = rentalRepository.findByBookId(bookId);
        if(!rentals.isEmpty()){
            return new ResponseEntity<>("Cannot delete book as it have rental history.", HttpStatus.CONFLICT);
        }

        bookRepository.deleteById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

//    public ResponseEntity<?> updateEntry(Long bookId, Book newBook) {
//        Book existingBook = bookRepository.findById(bookId).orElse(null);
//        if(existingBook == null){
//            return new ResponseEntity<>("Book Not Found!", HttpStatus.NOT_FOUND);
//        }
//
//        existingBook.setId(newBook.getId());
//        existingBook.setTitle(newBook.getTitle());
//        existingBook.setAuthor(newBook.getAuthor());
//        existingBook.setAvailable(newBook.isAvailable());
//
//        bookRepository.save(existingBook);
//
//        return new ResponseEntity<>(existingBook, HttpStatus.OK);
//    }

    public ResponseEntity<?> getAllAvailable() {
        List<Book> allBooks = bookRepository.findAll();
        List<Book> allAvailableBooks = new ArrayList<>();
        for(Book book : allBooks){
            if(book.isAvailable()) allAvailableBooks.add(book);
        }
        return new ResponseEntity<>(allAvailableBooks, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteAll() {
        bookRepository.deleteAll();
        return new ResponseEntity<>("All Books Deleted Successfully!", HttpStatus.OK);
    }
}
