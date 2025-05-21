package br.com.fiap.auth.service;

import br.com.fiap.auth.model.Auth;
import br.com.fiap.auth.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Auth user = authRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com e-mail: " + email));

        return new User(user.getEmail(), user.getSenha(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getPerfil().name())));
    }
}