package com.example.rent_read.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Book {

    @Id
    private String id;

    private String title;
    private String author;
    private boolean available = true; // by default true;
}
