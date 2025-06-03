package com.example.parcialdos.dto;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LibroResponseDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer publicationYear;
    private String language;
    private Integer pages;
    private String genre;
}
