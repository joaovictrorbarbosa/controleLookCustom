package com.example.novo_spring_boot.repository;

import com.example.novo_spring_boot.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Força o Spring a filtrar pelo username do usuário vinculado
    @Query("SELECT p FROM Produto p WHERE p.user.username = :username")
    List<Produto> findByUserUsername(@Param("username") String username);

    // Força o filtro para busca por ID (essencial para o Delete e Update)
    @Query("SELECT p FROM Produto p WHERE p.id = :id AND p.user.username = :username")
    Optional<Produto> findByIdAndUserUsername(@Param("id") Long id, @Param("username") String username);

    @Query("SELECT p FROM Produto p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND p.user.username = :username")
    List<Produto> findByNomeContainingIgnoreCaseAndUserUsername(@Param("nome") String nome, @Param("username") String username);

    @Query("SELECT p FROM Produto p WHERE p.tamanho = :tamanho AND p.user.username = :username")
    List<Produto> findByTamanhoAndUserUsername(@Param("tamanho") String tamanho, @Param("username") String username);
}





