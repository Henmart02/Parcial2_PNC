package com.example.parcialdos.service.impl;



import com.example.parcialdos.dto.LibroRequestDto;
import com.example.parcialdos.dto.LibroResponseDto;

import com.example.parcialdos.entities.libro;
import com.example.parcialdos.exception.BusinessRuleException;
import com.example.parcialdos.exception.DuplicateIsbnException;
import com.example.parcialdos.exception.NotFoundException;

import com.example.parcialdos.repository.libroRepository;
import com.example.parcialdos.service.LibroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;


@Service("libroService")
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService {

    private final libroRepository repo;



    private LibroResponseDto toDto(libro l) {
        return LibroResponseDto.builder()
                .id(l.getId())
                .title(l.getTitle())
                .author(l.getAuthor())
                .isbn(l.getIsbn())
                .publicationYear(l.getPublicationYear())
                .language(l.getLanguage())
                .pages(l.getPages())
                .genre(l.getGenre())
                .build();
    }


    private void validar(LibroRequestDto dto) {
        if (dto.getPublicationYear() != null) {
            int anio = dto.getPublicationYear();
            int anioActual = Year.now().getValue();
            if (anio < 1900 || anio > anioActual) {
                throw new BusinessRuleException("Año de publicación inválido");
            }
        }
        if (dto.getPages() != null && dto.getPages() <= 10) {
            throw new BusinessRuleException("El libro debe tener más de 10 páginas");
        }
        if (dto.getTitle() != null &&
                !dto.getTitle().matches(".*[A-Za-z].*")) {
            throw new BusinessRuleException("El título no puede ser sólo números");
        }
    }


    @Override
    @Transactional
    public LibroResponseDto crear(LibroRequestDto dto) throws Throwable {
        if (repo.existsByIsbn(dto.getIsbn())) {
            throw new Throwable("ISBN ya registrado");
        }
        validar(dto);

        libro book = libro.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .isbn(dto.getIsbn())
                .publicationYear(dto.getPublicationYear())
                .language(dto.getLanguage())
                .pages(dto.getPages())
                .genre(dto.getGenre())
                .build();

        return toDto(repo.save(book));
    }


    @Override
    @Transactional(readOnly = true)
    public List<LibroResponseDto> listar() {
        return repo.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public LibroResponseDto buscarPorIsbn(String isbn) {
        libro libro = repo.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Libro no encontrado"));
        return toDto(libro);
    }


    @Override
    @Transactional
    public LibroResponseDto actualizar(String isbn, LibroRequestDto dto) {
        libro libro = repo.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Libro no encontrado"));

        if (dto.getTitle()           != null) libro.setTitle(dto.getTitle());
        if (dto.getAuthor()          != null) libro.setAuthor(dto.getAuthor());
        if (dto.getPublicationYear() != null) libro.setPublicationYear(dto.getPublicationYear());
        if (dto.getLanguage()        != null) libro.setLanguage(dto.getLanguage());
        if (dto.getPages()           != null) libro.setPages(dto.getPages());
        if (dto.getGenre()           != null) libro.setGenre(dto.getGenre());

        validar(dto);

        return toDto(repo.save(libro));
    }
    @Override
    @Transactional
    public void eliminar(String isbn) {
        libro libro = repo.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Libro no encontrado"));
        repo.delete(libro);
    }



    @Override
    @Transactional
    public LibroResponseDto updateFull(String isbn, LibroRequestDto dto) {
        libro libro = repo.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Libro no encontrado"));

        validar(dto);

        libro.setTitle(dto.getTitle());
        libro.setAuthor(dto.getAuthor());
        libro.setPublicationYear(dto.getPublicationYear());
        libro.setLanguage(dto.getLanguage());
        libro.setPages(dto.getPages());
        libro.setGenre(dto.getGenre());

        return toDto(repo.save(libro));
    }
}



/*
import java.awt.print.Book;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

import com.example.parcialdos.dto.LibroRequestDto;
import com.example.parcialdos.dto.LibroResponseDto;
import com.example.parcialdos.entities.libro;
import com.example.parcialdos.exception.BusinessRuleException;
import com.example.parcialdos.exception.NotFoundException;
import com.example.parcialdos.repository.libroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public abstract class BookServiceImpl implements libroService {

    private final libroRepository repo;

    private LibroResponseDto toDto(libro b) {
        return LibroResponseDto.builder()
                .id(b.getId())
                .title(b.getTitle())
                .author(b.getAuthor())
                .isbn(b.getIsbn())
                .publicationYear(b.getPublicationYear())
                .language(b.getLanguage())
                .pages(b.getPages())
                .genre(b.getGenre())
                .build();
    }

    @Override
    @Transactional
    public LibroResponseDto create(LibroRequestDto dto) {
        if (repo.existsByIsbn(dto.getIsbn()))
            throw new BusinessRuleException("ISBN ya registrado");

        validateBusiness(dto);

        libro book = libro.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .isbn(dto.getIsbn())
                .publicationYear(dto.getPublicationYear())
                .language(dto.getLanguage())
                .pages(dto.getPages())
                .genre(dto.getGenre())
                .build();

        return toDto(repo.save(book));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LibroResponseDto> list(String author, String language, String genre,
                                      Integer minPages, Integer maxPages) {

        if (author != null)   return repo.findByAuthorContainingIgnoreCase(author)
                .stream().map(this::toDto).toList();

        if (language != null) return repo.findByLanguageIgnoreCase(language)
                .stream().map(this::toDto).toList();

        if (genre != null)    return repo.findByGenreIgnoreCase(genre)
                .stream().map(this::toDto).toList();

        if (minPages != null && maxPages != null)
            return repo.findByPagesBetween(minPages, maxPages)
                    .stream().map(this::toDto).toList();

        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LibroResponseDto findByIsbn(String isbn) {
        libro b = repo.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Libro no encontrado"));
        return toDto(b);
    }

    @Override
    @Transactional
    public LibroResponseDto updateTitleAndLanguage(String isbn, LibroRequestDto dto) {
        libro b = repo.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Libro no encontrado"));

        if (dto.getTitle() != null) {
            if (!dto.getTitle().matches(".*[A-Za-z].*"))
                throw new BusinessRuleException("El título no puede ser sólo números");
            b.setTitle(dto.getTitle());
        }
        if (dto.getLanguage() != null) b.setLanguage(dto.getLanguage());

        return toDto(repo.save(b));
    }

    @Override
    @Transactional
    public void delete(String isbn) {
        libro b = repo.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Libro no encontrado"));
        repo.delete(b);
    }


    @Override
    @Transactional
    public LibroResponseDto updateFull(String isbn, LibroRequestDto dto) {
        libro b = repo.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Libro no encontrado"));

        validateBusiness(dto);

        b.setTitle(dto.getTitle());
        b.setAuthor(dto.getAuthor());
        b.setPublicationYear(dto.getPublicationYear());
        b.setLanguage(dto.getLanguage());
        b.setPages(dto.getPages());
        b.setGenre(dto.getGenre());

        return toDto(repo.save(b));
    }

    // -------------------------------------------
    private void validateBusiness(LibroRequestDto dto) {
        if (dto.getPublicationYear() < 1900 || dto.getPublicationYear() > Year.now().getValue())
            throw new BusinessRuleException("Año de publicación inválido");
        if (dto.getPages() <= 10)
            throw new BusinessRuleException("El libro debe tener más de 10 páginas");
        if (!dto.getTitle().matches(".*[A-Za-z].*"))
            throw new BusinessRuleException("El título no puede ser sólo números");
    }
}*/
