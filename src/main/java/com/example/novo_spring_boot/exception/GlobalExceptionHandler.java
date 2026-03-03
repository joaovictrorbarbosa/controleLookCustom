package com.example.novo_spring_boot.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Object> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex){
         LinkedHashMap<String, Object> body = new LinkedHashMap<>();
         body.put("timestamp", LocalDateTime.now());
         body.put("status", HttpStatus.NOT_FOUND.value());
         body.put("error", "Recurso não encontrado");
         body.put("message", ex.getMessage());
         return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

   @ExceptionHandler(Exception.class)
public ResponseEntity<Object> handleGenericException(Exception ex){
     LinkedHashMap<String, Object> body = new LinkedHashMap<>();
     body.put("timestamp", LocalDateTime.now());
     body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
     body.put("error", "Erro interno do servidor");
     body.put("message", ex.getMessage());
     return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
}
}
