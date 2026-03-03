package com.example.novo_spring_boot.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import com.example.novo_spring_boot.model.Usuario;
import com.example.novo_spring_boot.service.UsuarioService;


@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false) // <--- ISSO AQUI resolve o erro 403 (Forbidden)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc; //Irá simular as requisições HTTP

    @MockitoBean
    private UsuarioService usuarioService; //Service em mock para isolar controller
    
    @Test
    @DisplayName("Deve retornar status 200 quando registrar um usuario")
    void testRegistrarUsuario() throws Exception{
        //Preparando dados
        Usuario usuario = new Usuario("Joao", "senha_criptografada");
        
        //mock
        when(usuarioService.registrarUsuario(anyString(), anyString())).thenReturn(usuario);
        
        //Json para a requisição
        String jsonRequest = "{\"username\": \"Joao\", \"password\": \"12345\"}";

        mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonRequest))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("Joao"));
    }

    @Test
    @DisplayName("Deve retornar 401 quando login falhar")
    void testLoginInvalido() throws Exception{
        //Simulando usuario nao encontrado
        when(usuarioService.buscarPorUsername("usuario_errado")).thenReturn(Optional.empty());

        String jsonRequest = "{\"username\": \"usuario_errado\", \"password\": \"123\"}";

         mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonRequest))
        .andExpect(status().isUnauthorized());
    }
}

