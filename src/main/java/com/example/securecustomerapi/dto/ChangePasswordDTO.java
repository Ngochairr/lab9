package com.example.securecustomerapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordDTO {

    @NotBlank
    private String currentPassword;

    @NotBlank
    @Size(min = 6)
    private String newPassword;

    @NotBlank
    private String confirmPassword;
    
    public String getCurrentPassword() {
        return currentPassword;
    }
    public void setCurrentPassword(String password) {
        this.currentPassword = password;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String password) {
        this.newPassword = password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String password) {
        this.confirmPassword = password;
    }
    
}
