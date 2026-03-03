package com.example.novo_spring_boot.repository;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.novo_spring_boot.model.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {


    @Query("SELECT p FROM Produto p WHERE p.user.username = :username")
    List<Produto> findByUserUsername(@Param("username") String username);

    @Query("SELECT p FROM Produto p WHERE p.id = :id AND p.user.username = :username")
    Optional<Produto> findByIdAndUserUsername(@Param("id") Long id, @Param("username") String username);

    List<Produto> findByNomeContainingIgnoreCaseAndUserUsername(String nome, String username);
    List<Produto> findByTamanhoAndUserUsername(String tamanho, String username);
}





