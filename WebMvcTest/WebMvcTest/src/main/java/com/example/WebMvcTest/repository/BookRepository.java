package com.example.WebMvcTest.repository;

import com.example.WebMvcTest.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
