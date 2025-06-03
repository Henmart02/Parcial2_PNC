package com.example.parcialdos;
import com.example.parcialdos.exception.BusinessRuleException;
import com.example.parcialdos.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String,String>> notFound(NotFoundException ex) {
        return response(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Map<String,String>> business(BusinessRuleException ex) {
        return response(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> validation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst().orElse("Error de validación");
        return response(HttpStatus.BAD_REQUEST, msg);
    }
    private ResponseEntity<Map<String,String>> response(HttpStatus status, String msg) {
        Map<String,String> body = new HashMap<>();
        body.put("error", msg);
        return new ResponseEntity<>(body, status);
    }
}
