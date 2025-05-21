package br.com.fiap.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha; // Armazenar com BCrypt

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "O perfil é obrigatório.")
    private Perfil perfil; // Enum: INVESTIDOR, ASSESSOR

    @Column(name = "data_criacao", updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate dataCriacao;

    @PrePersist
    public void preencherDataCriacao() {
        this.dataCriacao = LocalDate.now();
    }
}
