package com.example.novo_spring_boot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.novo_spring_boot.model.Usuario;
import com.example.novo_spring_boot.repository.UsuarioRepository;



@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    //Aqui estamos criando um usuarioRepository fake
    @Mock
    private UsuarioRepository usuarioRepository;
    
    @InjectMocks
    private UsuarioService usuarioService;
    
    @Test
    @DisplayName("Deve registrar um usuário com senha criptografada")
    void testRegistrarUsuarioSucess() {
        //Preparando os dados
        String username = "JoaoJunior";
        String password = "senha123";
    
        //Ensinando o mock: "Quando o save for chamado, retorne o próprio objeto que recebeu"
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));
    
        //ACT - Executando a ação que queremos testar
        Usuario resultado = usuarioService.registrarUsuario(username, password);

        //ASSERT verifica se funcionou
        assertNotNull(resultado);
        assertEquals("JoaoJunior", resultado.getUsername());

        //A SENHA NÃO PODE RETORNAR "SENHA123"
        assertNotEquals(password, resultado.getPassword());
        
        //Testando se a senha seguir o padrão do BCRypt(começando com $2a$)
        assertTrue(resultado.getPassword().startsWith("$2a$")); 
    }


    @Test
    @DisplayName("Deve retornar um username quando ele existir")
    void testBuscarPorUsername(){
        //Arrange
        String username = "JoaoJunior";
        Usuario usuarioMock = new Usuario(username, "senha123");

        //Mock: Quando buscar pelo username, finja  que encontrou o usuarioMock
        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(usuarioMock));

        //ACT
        Optional<Usuario> resultado = usuarioService.buscarPorUsername(username);

        //ASSERT
        assertTrue(resultado.isPresent());
        assertEquals(username, resultado.get().getUsername());

        //Verificando se o service chamou o repositorio com o nome certo
        verify(usuarioRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("Deve retornar vazio quando não existir username")
    void testBuscarPorUsernameVazio(){
        //Arrange
        String username = "usuario_inexistente";

        //Mock: "Quando buscar por esse nome, retorne vazio"
        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.empty());

        //ACT
        Optional<Usuario> resultado = usuarioService.buscarPorUsername(username);

        //ASSERT
        assertTrue(resultado.isEmpty());

        // Verifica se o repository foi consultado
        verify(usuarioRepository, times(1)).findByUsername(username);
        

    }
}
