package com.example.parcialdos;



import com.example.parcialdos.dto.LibroRequestDto;
import com.example.parcialdos.dto.LibroResponseDto;
import com.example.parcialdos.service.LibroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;

    @PostMapping
    public ResponseEntity<LibroResponseDto> crear(
            @Validated @RequestBody LibroRequestDto dto) throws Throwable {

        LibroResponseDto creado = libroService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }


    @GetMapping
    public ResponseEntity<List<LibroResponseDto>> listar() {
        return ResponseEntity.ok(libroService.listar());
    }


    @GetMapping("/{isbn}")
    public ResponseEntity<LibroResponseDto> buscarPorIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(libroService.buscarPorIsbn(isbn));
    }


    @PutMapping("/{isbn}")
    public ResponseEntity<LibroResponseDto> actualizar(
            @PathVariable String isbn,
            @Validated @RequestBody LibroRequestDto dto) {

        return ResponseEntity.ok(libroService.actualizar(isbn, dto));
    }


    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> eliminar(@PathVariable String isbn) {
        libroService.eliminar(isbn);
        return ResponseEntity.noContent().build();
    }
}
