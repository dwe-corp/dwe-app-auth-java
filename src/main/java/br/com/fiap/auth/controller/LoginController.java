package br.com.fiap.auth.controller;

import br.com.fiap.auth.model.Auth;
import br.com.fiap.auth.repository.AuthRepository;
import br.com.fiap.auth.dto.AuthRequest;
import br.com.fiap.auth.dto.AuthResponse;
import br.com.fiap.auth.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

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
        logger.info("Tentativa de login para o e-mail: {}", request.getEmail());

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
            );
        } catch (BadCredentialsException e) {
            logger.warn("Credenciais inválidas para o e-mail: {}", request.getEmail());
            throw e;
        } catch (Exception e) {
            logger.error("Erro durante autenticação do e-mail {}: {}", request.getEmail(), e.getMessage());
            throw e;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        Auth usuario = authRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> {
                logger.error("Usuário não encontrado após autenticação: {}", request.getEmail());
                return new RuntimeException("Usuário não encontrado após autenticação");
            });

        logger.info("Login realizado com sucesso para o e-mail: {}", request.getEmail());

        return new AuthResponse(token, usuario.getNome(), usuario.getEmail(), usuario.getPerfil().toString());
    }
}
