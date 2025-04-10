package com.example.rent_read.controllers;

import com.example.rent_read.entities.Book;
import com.example.rent_read.entities.Rental;
import com.example.rent_read.services.BookService;
import com.example.rent_read.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private BookService bookService;

    @Autowired
    private RentalService rentalService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Rental> list = rentalService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserSpecificRentals(@PathVariable Long userId, @RequestParam String userEmail){
        return new ResponseEntity<>(rentalService.getUserRentals(userEmail), HttpStatus.OK);
    }

//    @PostMapping("/{bookId}/rent")
//    public ResponseEntity<?> rentABook(@PathVariable String bookId, @RequestParam String userEmail){
//        return rentalService.rentBook(bookId, userEmail);
//    }
//
//    @PostMapping("/{bookId}/return")
//    public ResponseEntity<?> returnABook(@PathVariable String bookId, @RequestParam String userEmail){
//        return rentalService.returnBook(bookId, userEmail);
//    }

    @DeleteMapping("/{rentalId}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long rentalId){
        return rentalService.deleteEntry(rentalId);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll(){
        return rentalService.deleteAll();
    }
}
