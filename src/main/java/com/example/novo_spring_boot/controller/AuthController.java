package com.example.novo_spring_boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.novo_spring_boot.model.Usuario;
import com.example.novo_spring_boot.security.JwtUtil;
import com.example.novo_spring_boot.service.UsuarioService;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "https://lookcustom.vercel.app")
public class AuthController {
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
    this.usuarioService = usuarioService;
    this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        Usuario usuario  = usuarioService.registrarUsuario(request.get("username"), request.get("password"));
        return ResponseEntity.ok(usuario);
        }
        
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
    

