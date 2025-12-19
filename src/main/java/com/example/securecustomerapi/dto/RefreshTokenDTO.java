package com.example.securecustomerapi.dto;
import jakarta.validation.constraints.NotBlank;

public class RefreshTokenDTO {

    @NotBlank
    private String refreshToken;

    // Getters and Setters
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}