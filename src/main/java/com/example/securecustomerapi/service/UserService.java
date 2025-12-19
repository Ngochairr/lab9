package com.example.securecustomerapi.service;

import java.util.List;
import java.util.Map;
 
import com.example.securecustomerapi.dto.ChangePasswordDTO;
import com.example.securecustomerapi.dto.LoginRequestDTO;
import com.example.securecustomerapi.dto.LoginResponseDTO;
import com.example.securecustomerapi.dto.RegisterRequestDTO;
import com.example.securecustomerapi.dto.ResetPasswordDTO;
import com.example.securecustomerapi.dto.UpdateProfileDTO;
import com.example.securecustomerapi.dto.UpdateRoleDTO;
import com.example.securecustomerapi.dto.UserResponseDTO;
import com.example.securecustomerapi.entity.RefreshToken;
import com.example.securecustomerapi.entity.User;

public interface UserService {
    
    LoginResponseDTO login(LoginRequestDTO loginRequest);
    
    UserResponseDTO register(RegisterRequestDTO registerRequest);
    
    UserResponseDTO getCurrentUser(String username);

    void changePassword(String username, ChangePasswordDTO dto);

    Map<String, String> forgotPassword(String email);

    void resetPassword(ResetPasswordDTO dto);

    UserResponseDTO updateProfile(String username, UpdateProfileDTO dto);

    void deleteAccount(String username, String password);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUserRole(Long id, UpdateRoleDTO dto);

    UserResponseDTO toggleUserStatus(Long id);

    LoginResponseDTO refreshAccessToken(String refreshToken);

    User findByEmail(String email);

    User findByResetToken(String token);

    String encodePassword(String password);

    RefreshToken createRefreshToken(User user);

}