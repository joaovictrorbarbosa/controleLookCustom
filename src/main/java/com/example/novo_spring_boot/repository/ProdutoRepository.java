package com.example.novo_spring_boot.repository;

import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.novo_spring_boot.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {


    Optional<Produto> findByIdAndUserUsername(Long id, String username);

    List<Produto> findByUserUsername(String username);
    List<Produto> findByNomeContainingIgnoreCaseAndUserUsername(String nome, String username);
    List<Produto> findByTamanhoAndUserUsername(String tamanho, String username);
}

