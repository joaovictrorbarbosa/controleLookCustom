package com.example.novo_spring_boot.exception;

public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
