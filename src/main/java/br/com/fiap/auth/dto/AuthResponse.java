package br.com.fiap.auth.dto;

public class AuthResponse {
    private String token;
    private String perfil;

    public AuthResponse(String token, String perfil) {
        this.token = token;
        this.perfil = perfil;
    }

    public String getToken() {
        return token;
    }

    public String getPerfil() {
        return perfil;
    }
}