package com.example.securecustomerapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.securecustomerapi.dto.ChangePasswordDTO;
import com.example.securecustomerapi.dto.LoginRequestDTO;
import com.example.securecustomerapi.dto.LoginResponseDTO;
import com.example.securecustomerapi.dto.RegisterRequestDTO;
import com.example.securecustomerapi.dto.ResetPasswordDTO;
import com.example.securecustomerapi.dto.UpdateProfileDTO;
import com.example.securecustomerapi.dto.UpdateRoleDTO;
import com.example.securecustomerapi.dto.UserResponseDTO;
import com.example.securecustomerapi.entity.RefreshToken;
import com.example.securecustomerapi.entity.Role;
import com.example.securecustomerapi.entity.User;
import com.example.securecustomerapi.exception.DuplicateResourceException;
import com.example.securecustomerapi.exception.ResourceNotFoundException;
import com.example.securecustomerapi.repository.RefreshTokenRepository;
import com.example.securecustomerapi.repository.UserRepository;
import com.example.securecustomerapi.security.JwtTokenProvider;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get user
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Generate JWT access token
        String accessToken = tokenProvider.generateToken(authentication);

        // Generate refresh token
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshToken);
        LoginResponseDTO response = new LoginResponseDTO(
            accessToken,
            user.getUsername(),
            user.getEmail(),
            user.getRole().name()
        );
        response.setRefreshToken(refreshToken.getToken());
        // Return both tokens
        return response;
    }

    public LoginResponseDTO refreshAccessToken(String refreshTokenStr) {
    RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenStr)
            .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

    if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
        throw new IllegalArgumentException("Refresh token expired");
    }

    User user = refreshToken.getUser();

    // Create Authentication object for tokenProvider
    Authentication authentication = new UsernamePasswordAuthenticationToken(
            user.getUsername(),
            null,   // no password required
            null    // authorities can be null if not used in token generation
    );

    String newAccessToken = tokenProvider.generateToken(authentication);

    LoginResponseDTO response = new LoginResponseDTO(
            newAccessToken,
            user.getUsername(),
            user.getEmail(),
            user.getRole().name()
    );
    response.setRefreshToken(refreshToken.getToken());

    return response;
}

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString()); // Unique token
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7)); // 7 days expiry

        return refreshTokenRepository.save(refreshToken);
    }
    @Override
    public UserResponseDTO register(RegisterRequestDTO registerRequest) {
        // Check if username exists
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }
        
        // Check if email exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        
        // Create new user
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFullName(registerRequest.getFullName());
        user.setRole(Role.USER);  // Default role
        user.setIsActive(true);
        
        User savedUser = userRepository.save(user);
        
        return convertToDTO(savedUser);
    }
    @Override
    public UserResponseDTO getCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return convertToDTO(user);
    }
    
    private UserResponseDTO convertToDTO(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFullName(),
            user.getRole().name(),
            user.getIsActive(),
            user.getCreatedAt()
        );
    }
    @Override
    public void changePassword(String username, ChangePasswordDTO dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email not found"));
    }

    public User findByResetToken(String token) {
        return userRepository.findByResetToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reset token"));
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
    @Override
    public UserResponseDTO updateProfile(String username, UpdateProfileDTO dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new IllegalArgumentException("Email already in use");
            }
            user.setEmail(dto.getEmail());
        }

        if (dto.getFullName() != null) {
            user.setFullName(dto.getFullName());
        }

        userRepository.save(user);

        return convertToDTO(user);
    }
    @Override
    public Map<String, String> forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email not found"));

        // Generate a random token
        String token = java.util.UUID.randomUUID().toString();

        // Set token and expiry (1 hour from now)
        user.setResetToken(token);
        user.setResetTokenExpiry(java.time.LocalDateTime.now().plusHours(1));

        userRepository.save(user);

        // Return token and message (in real app, you'd email this token)
        Map<String, String> response = new java.util.HashMap<>();
        response.put("resetToken", token);
        response.put("message", "Use this token to reset password");

        return response;
    }
    @Override
    public void resetPassword(ResetPasswordDTO dto) {
        User user = userRepository.findByResetToken(dto.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid reset token"));

        // Check if token is expired
        if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().isBefore(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("Reset token expired");
        }

        // Validate new password matches confirm
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }

        // Encode and set new password
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        // Clear token and expiry
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepository.save(user);
    }
    @Override
    public void deleteAccount(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password");
        }

        // Soft delete
        user.setIsActive(false);
        userRepository.save(user);
    }
    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public UserResponseDTO toggleUserStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setIsActive(!user.getIsActive());  // Toggle status
        userRepository.save(user);
        return convertToDTO(user);
    }
    @Override
    public UserResponseDTO updateUserRole(Long id, UpdateRoleDTO dto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setRole(dto.getRole());
        userRepository.save(user);
        return convertToDTO(user);
    }
}