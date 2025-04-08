package com.example.rent_read.repository;

import com.example.rent_read.entities.Book;
import com.example.rent_read.entities.Rental;
import com.example.rent_read.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    int countByUserAndReturnedFalse(User user);

    Rental findByUserAndBookAndReturnedFalse(User user, Book book);

    List<Rental> findByUser(User user);

    List<Rental> findByBookId(Long bookId);
}
