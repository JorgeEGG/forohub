package com.aluracursos.forohub.infraest.exceptions;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import com.aluracursos.forohub.domain.ValidacionException;

@RestControllerAdvice
public class GestorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> gestionarError404() {
        return ResponseEntity.notFound().build(); 
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DatosErrorValidacion>> gestionarError400(MethodArgumentNotValidException ex) {
        var errores = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errores.stream().map(DatosErrorValidacion::new).toList()); 
    }

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<String> gestionarErrorDeValidacion(ValidacionException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage()); 
    }

    public record DatosErrorValidacion(String campo, String mensaje) {
        public DatosErrorValidacion(FieldError error) {
             this(error.getField(), error.getDefaultMessage());
        }
    }
}
