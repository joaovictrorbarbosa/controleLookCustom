package com.example.novo_spring_boot.controller;

import com.example.novo_spring_boot.model.Usuario;
import com.example.novo_spring_boot.security.JwtUtil;
import com.example.novo_spring_boot.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para registro e login de usuários na API")
public class AuthController {
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
    this.usuarioService = usuarioService;
    this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Registra um novo usuário", description = "Cria uma nova conta na plataforma enviando username e pa                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        ssword.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados em formato incorreto"),
        @ApiResponse(responseCode = "500", description = "Erro interno ao cadastrar usuário")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Dados para cadastro do novo usuário",
        required = true,
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"username\": \"swagger\",\n  \"password\": \"1234\"\n}"
            )
        )
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        Usuario usuario  = usuarioService.registrarUsuario(request.get("username"), request.get("password"));
        return ResponseEntity.ok(usuario);
        }
    
    @Operation(summary = "Realiza o login", description = "Valida as credenciais e retorna o Token JWT para autenticação das demais requisições.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso. Retorna o token JWT."),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas (usuário ou senha incorretos)")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Credenciais de acesso",
        required = true,
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"username\": \"swagger\",\n  \"password\": \"1234\"\n}"
            )
        )
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        Optional<Usuario> usuario = usuarioService.buscarPorUsername(request.get("username"));
            if(usuario.isPresent() && passwordEncoder.matches(request.get("password"), usuario.get().getPassword())) {
                String token = JwtUtil.generateToken(usuario.get().getUsername());
                return ResponseEntity.ok(Map.of("token", token));
    } else {
        return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }
}

