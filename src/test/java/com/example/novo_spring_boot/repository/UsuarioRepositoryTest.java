package com.example.novo_spring_boot.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.novo_spring_boot.model.Usuario;

@DataJpaTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
   
    @Autowired
    private UsuarioRepository usuarioRepository;
   
   
    @Test    
    @DisplayName("Deve retornar um usuário por username")
    void testFindByUsernameSucess() {
        // CRIANDO UM USUARIO DE TESTES
        Usuario usuario = criaUsuario("testuser", "password123");
        usuarioRepository.save(usuario);
        
        // ACT
        Optional<Usuario> resultado = usuarioRepository.findByUsername("testuser");
        
        // ASSERT  -CONFERINDO SE DEU CERTO
        assertTrue(resultado.isPresent());
        assertEquals("testuser", resultado.get().getUsername());
        assertEquals("password123", resultado.get().getPassword());
    }
    
    @Test
    @DisplayName("Deve retornar vazio quando username não existe")
    void testFindByUsernameNaoEncontrado() {
        // ACT
        Optional<Usuario> resultado = usuarioRepository.findByUsername("usuarioNaoExiste");
        
        // ASSERT
        assertTrue(resultado.isEmpty());
    }
    

    
    
    // Método auxiliar para criar usuários de teste
    private Usuario criaUsuario(String username, String password) {
        return new Usuario(username, password);
    }

}
 