package com.example.novo_spring_boot.repository;

import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.novo_spring_boot.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Retorna todos os produtos que pertencem ao usuário logado
    List<Produto> findByUserUsername(String username);

    // Busca por nome filtrando apenas os produtos do usuário logado
    List<Produto> findByNomeContainingIgnoreCaseAndUserUsername(String nome, String username);
    
    // Busca por tamanho filtrando apenas os produtos do usuário logado
    List<Produto> findByTamanhoAndUserUsername(String tamanho, String username);
}
