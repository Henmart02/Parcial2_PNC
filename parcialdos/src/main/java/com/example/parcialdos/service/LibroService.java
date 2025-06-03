package com.example.parcialdos.service;


import com.example.parcialdos.dto.LibroRequestDto;
import com.example.parcialdos.dto.LibroResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LibroService {

    @Transactional
    LibroResponseDto crear(LibroRequestDto dto) throws Throwable;

    @Transactional(readOnly = true)
    List<LibroResponseDto> listar();

    @Transactional(readOnly = true)
    LibroResponseDto buscarPorIsbn(String isbn);

    @Transactional
    LibroResponseDto actualizar(String isbn, LibroRequestDto dto);

    @Transactional
    void eliminar(String isbn);


    @Transactional
    LibroResponseDto updateFull(String isbn, LibroRequestDto dto);
}


/*
import com.example.parcialdos.dto.LibroRequestDto;
import com.example.parcialdos.dto.LibroResponseDto;

import java.util.List;



import com.example.parcialdos.dto.LibroRequestDto;
import com.example.parcialdos.dto.LibroResponseDto;
import java.util.List;
public interface libroService {

    LibroResponseDto create(LibroRequestDto dto);
    List<LibroResponseDto> list(String author, String language, String genre,
                               Integer minPages, Integer maxPages);
    LibroResponseDto findByIsbn(String isbn);
    LibroResponseDto updateTitleAndLanguage(String isbn, LibroRequestDto dto);
    void delete(String isbn);
    LibroResponseDto updateFull(String isbn, LibroRequestDto dto);
}*/
