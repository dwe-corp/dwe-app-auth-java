package br.com.fiap.auth.controller;

import br.com.fiap.auth.model.Auth;
import br.com.fiap.auth.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @GetMapping
    public ResponseEntity<List<Auth>> listAll() {
        logger.info("Listando todos os usuários.");
        return ResponseEntity.ok(authService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auth> findById(@PathVariable Long id) {
        logger.info("Buscando usuário com ID: {}", id);
        return authService.findById(id)
                .map(auth -> {
                    logger.info("Usuário encontrado: {}", auth.getEmail());
                    return ResponseEntity.ok(auth);
                })
                .orElseGet(() -> {
                    logger.warn("Usuário com ID {} não encontrado.", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<Auth> create(@RequestBody @Valid Auth auth) {
        logger.info("Criando novo usuário com e-mail: {}", auth.getEmail());
        Auth criado = authService.save(auth);
        logger.info("Usuário criado com sucesso. ID: {}", criado.getId());
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auth> update(@PathVariable Long id, @RequestBody @Valid Auth atualizado) {
        logger.info("Atualizando usuário com ID: {}", id);
        Optional<Auth> existente = authService.findById(id);

        if (existente.isPresent()) {
            Auth auth = existente.get();
            auth.setNome(atualizado.getNome());
            auth.setEmail(atualizado.getEmail());
            auth.setPerfil(atualizado.getPerfil());
            Auth atualizadoFinal = authService.save(auth);
            logger.info("Usuário atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(atualizadoFinal);
        } else {
            logger.warn("Tentativa de atualizar usuário inexistente com ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Requisição para deletar usuário com ID: {}", id);
        if (authService.findById(id).isPresent()) {
            authService.delete(id);
            logger.info("Usuário com ID {} deletado com sucesso.", id);
            return ResponseEntity.noContent().build();
        }
        logger.warn("Tentativa de deletar usuário inexistente com ID: {}", id);
        return ResponseEntity.notFound().build();
    }
}
