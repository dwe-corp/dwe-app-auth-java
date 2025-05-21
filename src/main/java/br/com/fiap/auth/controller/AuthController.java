package br.com.fiap.auth.controller;

import br.com.fiap.auth.model.Auth;
import br.com.fiap.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping
    public ResponseEntity<List<Auth>> listAll() {
        return ResponseEntity.ok(authService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auth> findById(@PathVariable Long id) {
        return authService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Auth> create(@RequestBody @Valid Auth auth) {
        // Criptografar senha antes de salvar, se ainda não estiver sendo feito no service
        return ResponseEntity.ok(authService.save(auth));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auth> update(@PathVariable Long id, @RequestBody @Valid Auth atualizado) {
        Optional<Auth> existente = authService.findById(id);

        if (existente.isPresent()) {
            Auth auth = existente.get();
            auth.setNome(atualizado.getNome());
            auth.setEmail(atualizado.getEmail());
            auth.setPerfil(atualizado.getPerfil());
            // Evita sobrescrever senha diretamente
            return ResponseEntity.ok(authService.save(auth));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (authService.findById(id).isPresent()) {
            authService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
