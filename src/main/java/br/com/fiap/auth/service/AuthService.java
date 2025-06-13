package br.com.fiap.auth.service;

import br.com.fiap.auth.model.Auth;
import br.com.fiap.auth.repository.AuthRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Auth> listAll() {
        logger.info("Listando todos os usuários cadastrados.");
        return authRepository.findAll();
    }

    public Optional<Auth> findById(Long id) {
        logger.info("Buscando usuário com ID: {}", id);
        return authRepository.findById(id);
    }

    public Auth save(Auth auth) {
        if (auth.getId() == null) {
            logger.info("Criando novo usuário com e-mail: {}", auth.getEmail());
        } else {
            logger.info("Atualizando usuário com ID: {}", auth.getId());
        }

        if (auth.getSenha() != null) {
            logger.debug("Criptografando senha do usuário.");
            auth.setSenha(passwordEncoder.encode(auth.getSenha()));
        }

        Auth salvo = authRepository.save(auth);
        logger.info("Usuário salvo com sucesso. ID: {}", salvo.getId());
        return salvo;
    }

    public void delete(Long id) {
        logger.info("Deletando usuário com ID: {}", id);
        authRepository.deleteById(id);
        logger.info("Usuário deletado com sucesso. ID: {}", id);
    }
}
