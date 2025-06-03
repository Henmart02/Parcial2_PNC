package com.example.parcialdos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LibroRequestDto{

        @NotBlank
        @Pattern(regexp = ".*[A-Za-z].*", message = "El título no puede ser sólo números")
        private String title;

        @NotBlank
        private String author;

        @NotBlank
        private String isbn;

        @NotNull
        @Min(1900)
        private Integer publicationYear;

        private String language;

        @NotNull
        @Min(11)
        private Integer pages;

        private String genre;
}

