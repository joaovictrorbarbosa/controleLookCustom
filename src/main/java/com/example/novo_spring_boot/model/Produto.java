package com.example.novo_spring_boot.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario user; // Importe sua classe de usuário (User ou Usuario)

    private String nome;
    private Double preco;
    private Integer quantidade;
    private String tamanho;

    public Produto(){}

    public Produto(String nome, Double preco, int quantidade, String tamanho) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.tamanho = tamanho;
    }

    // Construtor para Testes e Update (produto existente)
    public Produto(Long id, String nome, Double preco) {
    this.id = id;
    this.nome = nome;
    this.preco = preco;
    }

    public Long getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    public String getTamanho() {
        return tamanho;
    }
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Usuario getUser() {
    return user;
}

public void setUser(Usuario user) {
    this.user = user;
}
}

