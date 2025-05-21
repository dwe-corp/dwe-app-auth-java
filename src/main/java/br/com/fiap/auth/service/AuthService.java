package br.com.fiap.auth.service;

import br.com.fiap.auth.model.Auth;
import br.com.fiap.auth.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<Auth> listAll() {
        return authRepository.findAll();
    }

    public Optional<Auth> findById(Long id) {
        return authRepository.findById(id);
    }

    public Auth save(Auth auth) {
        // Se for novo ou senha foi alterada, criptografa
        if (auth.getSenha() != null) {
            auth.setSenha(passwordEncoder.encode(auth.getSenha()));
        }
        return authRepository.save(auth);
    }

    public void delete(Long id) {
        authRepository.deleteById(id);
    }
}
