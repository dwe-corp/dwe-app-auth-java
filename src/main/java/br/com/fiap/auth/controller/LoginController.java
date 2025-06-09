package br.com.fiap.auth.controller;

import br.com.fiap.auth.model.Auth;
import br.com.fiap.auth.repository.AuthRepository;
import br.com.fiap.auth.dto.AuthRequest;
import br.com.fiap.auth.dto.AuthResponse;
import br.com.fiap.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthRepository authRepository;

    @PostMapping
    public AuthResponse login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );
    
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails);
    
        Auth usuario = authRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado após autenticação"));
    
        return new AuthResponse(token, usuario.getPerfil().toString());
    }
    
}