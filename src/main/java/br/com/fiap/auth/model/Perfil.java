package br.com.fiap.auth.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Perfil {
    INVESTIDOR,
    ASSESSOR;
    
    @JsonCreator
    public static Perfil fromString(String valor) {
        return Perfil.valueOf(valor.toUpperCase());
    }
}
