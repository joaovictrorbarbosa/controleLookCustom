package com.example.novo_spring_boot.service;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.novo_spring_boot.model.Usuario;
import com.example.novo_spring_boot.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                            .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));

                            return User.builder()
                                        .username(usuario.getUsername())
                                        .password(usuario.getPassword())
                                        .roles("USER")
                                        .build();
    }


}
