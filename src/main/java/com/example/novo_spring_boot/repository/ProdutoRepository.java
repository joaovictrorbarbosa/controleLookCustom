package com.example.novo_spring_boot.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.novo_spring_boot.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    List<Produto> findByNome(String nome);
    
    List<Produto> findByTamanho(String tamanho);
}
