package com.example.securecustomerapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.securecustomerapi.dto.ChangePasswordDTO;
import com.example.securecustomerapi.dto.LoginRequestDTO;
import com.example.securecustomerapi.dto.LoginResponseDTO;
import com.example.securecustomerapi.dto.RefreshTokenDTO;
import com.example.securecustomerapi.dto.RegisterRequestDTO;
import com.example.securecustomerapi.dto.ResetPasswordDTO;
import com.example.securecustomerapi.dto.UpdateProfileDTO;
import com.example.securecustomerapi.dto.UpdateRoleDTO;
import com.example.securecustomerapi.dto.UserResponseDTO;
import com.example.securecustomerapi.repository.UserRepository;
import com.example.securecustomerapi.service.UserService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = userService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        UserResponseDTO response = userService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        UserResponseDTO user = userService.getCurrentUser(username);
        return ResponseEntity.ok(user);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        // In JWT, logout is handled client-side by removing token
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully. Please remove token from client.");
        return ResponseEntity.ok(response);
    }
     @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.changePassword(username, dto);
        return ResponseEntity.ok(Map.of("message", "Password updated successfully"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestParam String email) {
        Map<String, String> response = userService.forgotPassword(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@Valid @RequestBody ResetPasswordDTO dto) {
        userService.resetPassword(dto);
        return ResponseEntity.ok(Map.of("message", "Password reset successful"));
    }

    // --- Task 7.1: View Profile ---
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getProfile() {
        // Get current authenticated username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserResponseDTO user = userService.getCurrentUser(username);
        return ResponseEntity.ok(user);
    }

    // --- Task 7.2: Update Profile ---
    @PutMapping("/profile")
    public ResponseEntity<UserResponseDTO> updateProfile(@Valid @RequestBody UpdateProfileDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserResponseDTO updatedUser = userService.updateProfile(username, dto);
        return ResponseEntity.ok(updatedUser);
    }

    // --- Task 7.3: Delete Account (Soft Delete) ---
    @DeleteMapping("/account")
    public ResponseEntity<?> deleteAccount(@RequestParam String password) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.deleteAccount(username, password);
        return ResponseEntity.ok(Map.of("message", "Account deactivated successfully"));
    }
    // --- Task 8.1: List All Users ---
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // --- Task 8.2: Update User Role ---
    @PutMapping("/users/{id}/role")
    public ResponseEntity<UserResponseDTO> updateUserRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRoleDTO dto) {
        return ResponseEntity.ok(userService.updateUserRole(id, dto));
    }

    // --- Task 8.3: Deactivate/Activate User ---
    @PatchMapping("/users/{id}/status")
    public ResponseEntity<UserResponseDTO> toggleUserStatus(@PathVariable Long id) {
        return ResponseEntity.ok(userService.toggleUserStatus(id));
    }
    @PostMapping("/api/auth/refresh")
    public ResponseEntity<LoginResponseDTO> refreshToken(@RequestBody RefreshTokenDTO dto) {
        LoginResponseDTO response = userService.refreshAccessToken(dto.getRefreshToken());
        return ResponseEntity.ok(response);
    }
}