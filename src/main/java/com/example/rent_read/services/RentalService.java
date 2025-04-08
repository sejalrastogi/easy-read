package com.example.rent_read.services;

import com.example.rent_read.entities.Book;
import com.example.rent_read.entities.Rental;
import com.example.rent_read.entities.User;
import com.example.rent_read.repository.BookRepository;
import com.example.rent_read.repository.RentalRepository;
import com.example.rent_read.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<?> rentBook(Long bookId, String userEmail) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if(book == null){
            return new ResponseEntity<>("Book Not Found!", HttpStatus.NOT_FOUND);

      }

        User user = userRepository.findByEmail(userEmail).orElse(null);
        if(user == null){
            return new ResponseEntity<>("User Not Found!", HttpStatus.NOT_FOUND);
        }

        if(!book.isAvailable()){
            return new ResponseEntity<>("Book is Not Available!", HttpStatus.BAD_REQUEST);
        }

        // count the number of rentals this user currently have
        int activeRentals = rentalRepository.countByUserAndReturnedFalse(user);
        if(activeRentals >= 2){
            return new ResponseEntity<>("You already have 2 Active Rentals.", HttpStatus.BAD_REQUEST);
        }

        Rental rental = new Rental();
        rental.setBook(book);
        rental.setUser(user);
        rental.setRentDate(LocalDate.now());
        rental.setReturned(false);
        rentalRepository.save(rental);

        // update the book availability
        book.setAvailable(false);
        bookRepository.save(book);

        return new ResponseEntity<>(rental, HttpStatus.CREATED);
    }

    public ResponseEntity<?> returnBook(Long bookId, String userEmail) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if(book == null){
            return new ResponseEntity<>("Book Not Found!", HttpStatus.NOT_FOUND);
        }

        User user = userRepository.findByEmail(userEmail).orElse(null);
        if(user == null){
            return new ResponseEntity<>("User Not Found!", HttpStatus.NOT_FOUND);
        }

        Rental rental = rentalRepository.findByUserAndBookAndReturnedFalse(user, book);
        if(rental == null){
            return new ResponseEntity<>("Rental Not Found!", HttpStatus.NOT_FOUND);
        }

        // return
        rental.setReturned(true);
        rental.setReturnDate(LocalDate.now());
        rentalRepository.save(rental);

        // update book's availability
        book.setAvailable(true);
        bookRepository.save(book);

        return new ResponseEntity<>(rental + "\n Book Returned Successfully!", HttpStatus.OK);
    }

    public ResponseEntity<?> getUserRentals(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElse(null);
        if(user == null){
            return new ResponseEntity<>("User Not Found!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rentalRepository.findByUser(user), HttpStatus.OK);
    }

    public List<Rental> getAll() {
        return rentalRepository.findAll();
    }
}
