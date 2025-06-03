package com.example.parcialdos.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "books",
        uniqueConstraints = @UniqueConstraint(columnNames = "isbn")
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true, length = 17) // ISBN-13 con guiones
    private String isbn;

    @Column(nullable = false)
    private Integer publicationYear;

    @Column
    private String language;

    @Column(nullable = false)
    private Integer pages;

    @Column
    private String genre;
}


