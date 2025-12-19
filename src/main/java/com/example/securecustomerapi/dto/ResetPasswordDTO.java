package com.example.securecustomerapi.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public class ResetPasswordDTO {
    @NotBlank(message = "Reset token is required")
    private String token;

    @NotBlank(message = "New password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String newPassword;

    // Getters & Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    
    @NotBlank
    private String currentPassword;


    @NotBlank
    private String confirmPassword;
    
    public String getCurrentPassword() {
        return currentPassword;
    }
    public void setCurrentPassword(String password) {
        this.currentPassword = password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String password) {
        this.confirmPassword = password;
    }
}