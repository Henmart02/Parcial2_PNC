package com.example.parcialdos.repository;


import java.util.List;
import java.util.Optional;

import com.example.parcialdos.entities.libro;
import org.springframework.data.jpa.repository.JpaRepository;


public interface libroRepository extends JpaRepository<libro, Long> {

    boolean existsByIsbn(String isbn);
    Optional<libro> findByIsbn(String isbn);

    List<libro> findByAuthorContainingIgnoreCase(String author);
    List<libro> findByLanguageIgnoreCase(String language);
    List<libro> findByGenreIgnoreCase(String genre);
    List<libro> findByPagesBetween(Integer minPages, Integer maxPages);
}

